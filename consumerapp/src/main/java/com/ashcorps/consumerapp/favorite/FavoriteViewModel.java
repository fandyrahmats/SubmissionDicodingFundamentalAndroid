package com.ashcorps.consumerapp.favorite;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ashcorps.consumerapp.model.UserItems;

import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {

    private static final String TAG = FavoriteViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<UserItems>> favoriteList = new MutableLiveData<>();

    public MutableLiveData<ArrayList<UserItems>> getFavoriteList() {
        return favoriteList;
    }

    void loadFavoriteUser() {

    }
}
