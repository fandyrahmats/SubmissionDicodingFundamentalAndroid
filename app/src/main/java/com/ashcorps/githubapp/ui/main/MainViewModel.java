package com.ashcorps.githubapp.ui.main;

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

public class MainViewModel extends ViewModel {
    private static final String TAG = UserItems.class.getSimpleName();

    private MutableLiveData<ArrayList<UserItems>> listUser = new MutableLiveData<>();

    void loadUserList(String username) {
        AndroidNetworking.get(ApiEndPoint.ENDPOINT_SEARCH_USER)
                .addPathParameter(ApiEndPoint.USERNAME_PARAM, username)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(SearchUsersResponse.class, new ParsedRequestListener<SearchUsersResponse>() {
                    @Override
                    public void onResponse(SearchUsersResponse response) {
                        listUser.postValue(response.getItems());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e(TAG, "onErrorSearchUser: ", anError);
                    }
                });
    }

    MutableLiveData<ArrayList<UserItems>> getListUser() {
        return listUser;
    }
}
