package com.ashcorps.consumerapp.favorite;

import com.ashcorps.consumerapp.model.UserItems;

import java.util.ArrayList;

public interface LoadUserCallback {
    void preExecute();

    void postExecute(ArrayList<UserItems> users);
}
