package com.dark.confess;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by darshan on 29/09/16.
 */

public class Constants {

    public static final String PACKAGE_NAME = "com.dark.confess";
    public static final String USERS = "users";
    public static final String POSTS = "posts";
    public static final String REPORTED_POSTS = "reports";
    public static final String HASH_TAGS = "hash-tags";
    public static final String REPLIES = "post-replies";
    public static final String POPULAR = "popular";
    public static final String USER_NAME = PACKAGE_NAME + "/" + "userName";

    public static final int permissionCode = 125;


    public static String getImei(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();

    }

    public static String getCurrentTime() {
        return (DateFormat.format("dd-MM-yyyy hh:mm:ss aa", new java.util.Date()).toString());
    }

    public static String timeDiff(String postTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
        Date past = null;
        try {
            past = sdf.parse(postTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();

        long duration = now.getTime() - past.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long days = TimeUnit.MILLISECONDS.toDays(duration);

        // change the messages as per requirement
        if (seconds < 60)
            return seconds + " Seconds ago";
        else {
            if (minutes < 60)
                return minutes + " Minutes ago";
            else {
                if (hours < 24)
                    return hours + " Hours ago";
                else {
                    if (days < 30)
                        return days + " Days ago";
                    else {
                        if ((days / 30) < 12)
                            return (days / 30) + " Months ago";
                        else
                            return ((days / 30) / 12) + " Years ago";
                    }
                }
            }
        }

    }


    public static ArrayList<String> getHashTags(String body) {
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(body);
        ArrayList<String> hashTagsList = new ArrayList<>();
        while (mat.find()) {
            hashTagsList.add(mat.group(1));
        }
        return hashTagsList;
    }

}

// TODO: 04/10/16 (during implementation of delete) check if the uid is same as the current uid, if yes then delete

