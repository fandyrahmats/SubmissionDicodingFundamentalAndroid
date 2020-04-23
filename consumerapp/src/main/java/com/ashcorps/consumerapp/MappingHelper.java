package com.ashcorps.consumerapp;

import android.database.Cursor;

import com.ashcorps.consumerapp.model.UserItems;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<UserItems> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<UserItems> usersList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.ID));
            String username = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME));
            String avatar = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR));
            usersList.add(new UserItems(id, username, avatar));
        }

        return usersList;
    }
}
