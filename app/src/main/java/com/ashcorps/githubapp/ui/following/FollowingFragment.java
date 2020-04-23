package com.ashcorps.githubapp.ui.following;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashcorps.githubapp.R;
import com.ashcorps.githubapp.adapter.UserAdapter;
import com.ashcorps.githubapp.model.UserItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment {

    private static final String KEY_USERNAME = "KEY_USERNAME";

    @BindView(R.id.rv_following)
    RecyclerView rvFollowing;

    private UserAdapter userAdapter;

    private ArrayList<UserItems> followingList = new ArrayList<>();
    private String username;

    public FollowingFragment(String username) {
        this.username = username;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        ButterKnife.bind(this, view);
        prepare();
        return view;
    }

    private void prepare() {
        userAdapter = new UserAdapter(followingList);
        FollowingViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);
        if (getArguments()!=null) {
            username = getArguments().getString(KEY_USERNAME);
        }
        rvFollowing.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFollowing.setAdapter(userAdapter);
        viewModel.loadFollowing(username);
        viewModel.getListFollowing().observe(getViewLifecycleOwner(), followingResponse -> userAdapter.setUserList(followingResponse));
    }

}
