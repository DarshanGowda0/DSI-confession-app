package com.dark.confess;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

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
        Post post = new Post(userId, username, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + Constants.POSTS + "/" + key, postValues);
        childUpdates.put("/" + Constants.USER_POSTS + "/" + userId + "/" + key, postValues);

        databaseReference.updateChildren(childUpdates);
    }

    public void writeComment(String postId, String uid, String name, String commentValue) {


    }

    public boolean deletePost(String postId, String uid) {


        return true;
    }

    public boolean deleteReply(String replyId, String uid) {

        return true;
    }

    public boolean likeOrUnlikePost(String postId, String uid) {

        return true;
    }

    //fetch posts,fetch comments

    public void fetchPosts(){

    }

    public void fetchComments(String postID){

    }

}
