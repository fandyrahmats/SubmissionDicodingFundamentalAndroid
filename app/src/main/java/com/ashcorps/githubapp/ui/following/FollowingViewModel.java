package com.ashcorps.githubapp.ui.following;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.ashcorps.githubapp.ApiEndPoint;
import com.ashcorps.githubapp.model.SearchUsersResponse;
import com.ashcorps.githubapp.model.UserItems;

import java.util.ArrayList;

public class FollowingViewModel extends ViewModel {
    private MutableLiveData<ArrayList<UserItems>> listFollowing = new MutableLiveData<>();

    public MutableLiveData<ArrayList<UserItems>> getListFollowing() {
        return listFollowing;
    }

    void loadFollowing(String username) {
        AndroidNetworking.get(ApiEndPoint.ENDPOINT_FOLLOWING_USER)
                .addPathParameter(ApiEndPoint.USERNAME_PARAM, username)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(UserItems.class,
                        new ParsedRequestListener<ArrayList<UserItems>>() {
                            @Override
                            public void onResponse(ArrayList<UserItems> response) {
                                listFollowing.postValue(response);
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
    }
}
