package com.ashcorps.githubapp.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import com.ashcorps.githubapp.db.DatabaseContract;
import com.ashcorps.githubapp.db.UserFavoriteHelper;
import com.ashcorps.githubapp.model.UserItems;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

import static com.ashcorps.githubapp.db.DatabaseContract.AUTHORITY;
import static com.ashcorps.githubapp.db.DatabaseContract.UserColumns.AVATAR;
import static com.ashcorps.githubapp.db.DatabaseContract.UserColumns.ID;
import static com.ashcorps.githubapp.db.DatabaseContract.UserColumns.TABLE_NAME;
import static com.ashcorps.githubapp.db.DatabaseContract.UserColumns.USERNAME;

public class UserFavoriteProvider extends ContentProvider {

    private static final String TAG = UserFavoriteProvider.class.getSimpleName();

    private static final int USER = 1;
    private static final int USER_ID = 2;
    private UserFavoriteHelper favoriteHelper;
    Realm realm;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public UserFavoriteProvider() {
    }

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                USER_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        try {
            if (sUriMatcher.match(uri) == USER_ID) {
                Integer id = Integer.parseInt(String.valueOf(ContentUris.parseId(uri)));
                favoriteHelper.delete(id);
            }
        } finally {
            realm.close();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        Realm.init(getContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration(new MyRealmMigration())
                .build();
        Realm.setDefaultConfiguration(config);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        realm = Realm.getDefaultInstance();
        favoriteHelper = new UserFavoriteHelper(realm);
        int match = sUriMatcher.match(uri);

        MatrixCursor matrixCursor = new MatrixCursor(new String[]{
                ID, USERNAME, AVATAR
        });

        try {
            if (match == USER) {
                for (UserItems userItems : favoriteHelper.getAllUserFav()) {
                    Object[] rowData = new Object[]{userItems.getId(), userItems.getLogin(), userItems.getAvatarUrl()};
                    matrixCursor.addRow(rowData);
                }
            } else {
                throw new UnsupportedOperationException("Not yet implemented");
            }
            matrixCursor.setNotificationUri(getContext().getContentResolver(), uri);
        } finally {
            realm.close();
        }

        return matrixCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    class MyRealmMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            RealmSchema schema = realm.getSchema();

            if (oldVersion != 0) {
                schema.create(TABLE_NAME)
                        .addField(DatabaseContract.UserColumns.ID, Integer.class)
                        .addField(USERNAME, String.class)
                        .addField(AVATAR, String.class);
            }

        }
    }
}
