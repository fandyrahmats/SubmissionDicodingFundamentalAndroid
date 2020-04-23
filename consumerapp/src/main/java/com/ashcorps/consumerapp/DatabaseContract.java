package com.ashcorps.consumerapp;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.ashcorps.githubapp";
    private static final String SCHEME = "content";

    private DatabaseContract(){}

    public static final class UserColumns implements BaseColumns {
        public static final String TABLE_NAME = "githubuser";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String AVATAR = "avatar_url";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
