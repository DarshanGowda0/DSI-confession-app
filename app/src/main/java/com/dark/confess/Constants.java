package com.dark.confess;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by darshan on 29/09/16.
 */

public class Constants {

    public static final String USERS_TABLE = "users";
    public static final String POSTS = "posts";
    public static final String USER_POSTS = "user-posts";
    public static final String REPLIES = "post-replies";


    public static String getImei(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();

    }

    public static String getCurrentTime() {

        //TODO get the current time stamp in proper displayable format
        return "12 am";
    }

}


//// TODO: add hashTags parsing functions and pass it to writePost function

// TODO: add a function to calculate the time difference before showing in the rec view

// TODO: add a permission seeking to read the phone state