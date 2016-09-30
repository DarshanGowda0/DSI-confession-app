package com.dark.confess;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by darshan on 29/09/16.
 */

public class FireBaseHelper {

    private DatabaseReference databaseReference;

    public FireBaseHelper(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void writeNewPost(String userId, String username, String body) {


        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = databaseReference.child(Constants.POSTS).push().getKey();

        Post post = new Post(userId, username, body, Constants.getCurrentTime());
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + Constants.POSTS + "/" + key, postValues);
        childUpdates.put("/" + Constants.USER_POSTS + "/" + userId + "/" + key, postValues);

        databaseReference.updateChildren(childUpdates);
    }

    //comment is reply
    public void writeReply(String postId, String uid, String name, String replyValue) {

        String key = databaseReference.child(Constants.REPLIES).push().getKey();

        Reply reply = new Reply(name, uid, replyValue, Constants.getCurrentTime());
        Map<String, Object> replyValuesMap = reply.toMap();

        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put("/" + Constants.REPLIES + "/" + postId + "/" + key, replyValuesMap);

        databaseReference.updateChildren(childUpdate);

    }

    public void deletePost(String postId, String uid, final DeleteCallBack deletePostCallBack) {

        databaseReference.child(Constants.POSTS).child(postId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                deletePostCallBack.onDeleted(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deletePostCallBack.onDeleted(false);
            }
        });

        //no call back for this
        databaseReference.child(Constants.USER_POSTS).child(uid).child(postId).removeValue();

    }

    //same function without callback
    public void deletePost(String postId, String uid) {

        databaseReference.child(Constants.POSTS).child(postId).removeValue();
        databaseReference.child(Constants.USER_POSTS).child(uid).child(postId).removeValue();

    }


    public void deleteReply(String postId, String replyId, String uid, final DeleteCallBack deleteCallBack) {

        databaseReference.child(Constants.REPLIES).child(postId).child(replyId).removeValue().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deleteCallBack.onDeleted(false);
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                deleteCallBack.onDeleted(true);
            }
        });
    }

    //delete reply/comment without callback
    public void deleteReply(String postId, String replyId, String uid) {
        databaseReference.child(Constants.REPLIES).child(postId).child(replyId).removeValue();
    }

    public boolean likeOrUnlikePost(String postId, final String uid) {

        DatabaseReference postRef = databaseReference.child(Constants.POSTS).child(postId);

        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
//                Post p = mutableData.getValue(Post.class);
                Post post = new Post();
                post.uid = mutableData.child("uid").getValue(String.class);
                post.body = mutableData.child("body").getValue(String.class);
                post.starCount = mutableData.child("starCount").getValue(Integer.class);
                post.timeStamp = mutableData.child("time").getValue(String.class);
                post.author = mutableData.child("author").getValue(String.class);
                post.stars = mutableData.child("stars").getValue(Map.class);
                if (post == null) {
                    return Transaction.success(mutableData);
                }

                if (post.stars.containsKey(uid)) {
                    // Unstar the post and remove self from stars
                    post.starCount = post.starCount - 1;
                    post.stars.remove(uid);
                } else {
                    // Star the post and add self to stars
                    post.starCount = post.starCount + 1;
                    post.stars.put(uid, true);
                }

                // Set value and report transaction success
//                mutableData.setValue(post);

                mutableData.child("starCount").setValue(post.starCount);
                mutableData.child("stars").setValue(post.stars);

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });

        return true;
    }

    //fetch posts,fetch comments

    public ArrayList<Post> fetchPosts(final PostsFetched postsFetched) {

        final ArrayList<Post> postArrayList = new ArrayList<>();

        databaseReference.child(Constants.POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {

                    Log.d(TAG, "onDataChange: " + postSnapShot);
//                    Post post = postSnapShot.getValue(Post.class);
                    Post post = new Post();

                    post.uid = postSnapShot.child("uid").getValue(String.class);
                    post.body = postSnapShot.child("body").getValue(String.class);
                    post.starCount = postSnapShot.child("starCount").getValue(Integer.class);
                    post.timeStamp = postSnapShot.child("time").getValue(String.class);
                    post.author = postSnapShot.child("author").getValue(String.class);

                    postArrayList.add(post);

                }

                //callback to notify that the data is fetched
                postsFetched.onPostsFetched(postArrayList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return postArrayList;
    }


    public ArrayList<Reply> fetchComments(String postID, final RepliesFetched repliesFetched) {

        final ArrayList<Reply> replyArrayList = new ArrayList<>();

        databaseReference.child(Constants.REPLIES).child(postID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot replySnapShot : dataSnapshot.getChildren()) {
//                    Reply reply = replySnapShot.getValue(Reply.class);

                    Reply reply = new Reply();
                    reply.uid = replySnapShot.child("uid").getValue(String.class);
                    reply.replyValue = replySnapShot.child("replyValue").getValue(String.class);
                    reply.timeStamp = replySnapShot.child("time").getValue(String.class);
                    reply.name = replySnapShot.child("name").getValue(String.class);

                    replyArrayList.add(reply);
                }

                repliesFetched.onRepliesFetched(replyArrayList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return replyArrayList;

    }

    public interface PostsFetched {
        void onPostsFetched(ArrayList<Post> list);
    }

    public interface RepliesFetched {
        void onRepliesFetched(ArrayList<Reply> list);
    }

    public interface DeleteCallBack {
        void onDeleted(boolean var);
    }


}
