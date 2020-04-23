package com.ashcorps.githubapp.db;

import com.ashcorps.githubapp.model.UserItems;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserFavoriteHelper {
    private Realm mRealm;

    public UserFavoriteHelper(Realm realm) {
        this.mRealm = realm;
    }

    public void save(final UserItems user) {
        mRealm.executeTransaction(realm -> {
            user.setFavorite(true);
            realm.copyToRealm(user);
        });
    }

    public ArrayList<UserItems> getAllUserFav() {
        RealmResults<UserItems> resultItems = mRealm.where(UserItems.class).findAll();
        return new ArrayList<>(mRealm.copyFromRealm(resultItems));
    }

    public boolean favorite(Integer id) {
        UserItems model = mRealm.where(UserItems.class).equalTo("id", id).findFirst();
        if (model != null) {
            return model.isFavorite();
        } else {
            return false;
        }
    }

    public void delete(Integer id) {
        final RealmResults<UserItems> model = mRealm.where(UserItems.class).equalTo("id", id).findAll();
        mRealm.executeTransaction(realm -> model.deleteAllFromRealm());
    }
}
