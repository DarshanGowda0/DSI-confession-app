package com.dark.confess;

import android.util.Log;

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

    public void deletePost(String postId, String uid) {

        databaseReference.child(Constants.POSTS).child(postId).removeValue();

    }

    public void deleteReply(String postId, String replyId, String uid) {

        databaseReference.child(Constants.REPLIES).child(postId).child(replyId).removeValue();
    }

    public boolean likeOrUnlikePost(String postId, final String uid) {

        DatabaseReference postRef = databaseReference.child(Constants.POSTS).child(postId);

        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(uid)) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(uid);
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(uid, true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
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

    public ArrayList<Post> fetchPosts() {

        final ArrayList<Post> postArrayList = new ArrayList<>();

        databaseReference.child(Constants.POSTS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                    Post post = postSnapShot.getValue(Post.class);
                    postArrayList.add(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return postArrayList;
    }


    public ArrayList<Reply> fetchComments(String postID) {

        final ArrayList<Reply> replyArrayList = new ArrayList<>();

        databaseReference.child(Constants.REPLIES).child(postID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot replySnapShot : dataSnapshot.getChildren()) {
                    Reply reply = replySnapShot.getValue(Reply.class);
                    replyArrayList.add(reply);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return replyArrayList;

    }

}
