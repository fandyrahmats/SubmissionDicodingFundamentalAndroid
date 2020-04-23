package com.ashcorps.githubapp.ui.followers;

import android.util.Log;

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

public class FollowersViewModel extends ViewModel {

    private static final String TAG = FollowersViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<UserItems>> listFollowers = new MutableLiveData<>();

    public MutableLiveData<ArrayList<UserItems>> getListFollowers() {
        return listFollowers;
    }

    void loadFollowers(String username) {
        AndroidNetworking.get(ApiEndPoint.ENDPOINT_FOLLOWERS_USER)
                .addPathParameter(ApiEndPoint.USERNAME_PARAM, username)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObjectList(UserItems.class, new ParsedRequestListener<ArrayList<UserItems>>() {
                            @Override
                            public void onResponse(ArrayList<UserItems> response) {
                                Log.d(TAG, "onResponseFollowers: "+response.size());
                                listFollowers.postValue(response);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.e(TAG, "onErrorGetFollowers: ", anError);
                            }
                        }
                );
    }

}
