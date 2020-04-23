package com.ashcorps.githubapp.ui.followers;


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
public class FollowersFragment extends Fragment {

    @BindView(R.id.rv_followers)
    RecyclerView rvFollowers;

    private static final String KEY_USERNAME = "KEY_USERNAME";
    private String username;

    private UserAdapter userAdapter;
    private ArrayList<UserItems> followersList = new ArrayList<>();

    public FollowersFragment(String username) {
        this.username = username;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        ButterKnife.bind(this, view);
        prepare();
        return view;
    }

    private void prepare() {
        userAdapter = new UserAdapter(followersList);
        FollowersViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);
        if (getArguments()!=null) {
            username = getArguments().getString(KEY_USERNAME);
        }
        rvFollowers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFollowers.setAdapter(userAdapter);
        viewModel.loadFollowers(username);
        viewModel.getListFollowers().observe(getViewLifecycleOwner(), followersResponses -> userAdapter.setUserList(followersResponses));
    }

}
