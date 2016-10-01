package com.dark.confess;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by darshan on 29/09/16.
 */

public class Post {

    String uid;
    String time;
    String author;
    String body;
    int starCount = 0;
    Map<String, Boolean> stars = new HashMap<>();

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String body, String time) {
        this.uid = uid;
        this.author = author;
        this.body = body;
        this.time = time;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("body", body);
        result.put("starCount", starCount);
        result.put("stars", stars);
        result.put("time", time);
        return result;
    }

    public String getUid() {
        return uid;
    }

    public String getTime() {
        return time;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public int getStarCount() {
        return starCount;
    }

    public Map<String, Boolean> getStars() {
        return stars;
    }
}
