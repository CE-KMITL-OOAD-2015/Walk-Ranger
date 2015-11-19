package com.example.nattachai.walkingranger;

/**
 * Created by Windows8.1 on 6/11/2558.
 */
public class MemberStatic {
    static String fbID;

    public static String getFbID() {
        return fbID;
    }

    public static void setFbID(String fbID) {
        MemberStatic.fbID = fbID;
    }
}
