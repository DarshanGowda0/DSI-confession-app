package com.dark.confess;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by darshan on 29/09/16.
 */

public class Reply {


    String name;
    String uid;
    String replyValue;
    String timeStamp;

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
