package com.ashcorps.githubapp.ui.favorite;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ashcorps.githubapp.db.UserFavoriteHelper;
import com.ashcorps.githubapp.model.UserItems;

import java.util.ArrayList;

import io.realm.Realm;

public class FavoriteViewModel extends ViewModel {

    private static final String TAG = FavoriteViewModel.class.getSimpleName();

    private Realm realm;
    private MutableLiveData<ArrayList<UserItems>> favoriteList = new MutableLiveData<>();

    public MutableLiveData<ArrayList<UserItems>> getFavoriteList() {
        return favoriteList;
    }

    void loadFavoriteUser() {
        realm = Realm.getDefaultInstance();
        UserFavoriteHelper favoriteHelper = new UserFavoriteHelper(realm);
        favoriteList.postValue(favoriteHelper.getAllUserFav());
        Log.d(TAG, "loadFavMovieList: "+favoriteHelper.getAllUserFav().size());
    }
}
