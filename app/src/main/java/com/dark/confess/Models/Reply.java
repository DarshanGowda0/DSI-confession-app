package com.dark.confess.Models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by darshan on 29/09/16.
 */

public class Reply {


    private String name;
    private String uid;
    private String replyValue;

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setReplyValue(String replyValue) {
        this.replyValue = replyValue;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String timeStamp;

    public Reply() {
    }

    public Reply(String name, String uid, String replyValue, String timeStamp) {
        this.name = name;
        this.uid = uid;
        this.replyValue = replyValue;
        this.timeStamp = timeStamp;
    }

    public Map<String, Object> toMap() {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", name);
        hashMap.put("replyValue", replyValue);
        hashMap.put("timeStamp", timeStamp);

        return hashMap;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getReplyValue() {
        return replyValue;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
