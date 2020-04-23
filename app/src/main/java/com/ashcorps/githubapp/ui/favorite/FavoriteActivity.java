package com.ashcorps.githubapp.ui.favorite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ashcorps.githubapp.R;
import com.ashcorps.githubapp.adapter.UserAdapter;
import com.ashcorps.githubapp.model.UserItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.rv_favorite)
    RecyclerView rvFavorite;

    UserAdapter userAdapter;

    private ArrayList<UserItems> favoriteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.favorite));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        userAdapter = new UserAdapter(favoriteList);

        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setAdapter(userAdapter);

        FavoriteViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory())
                .get(FavoriteViewModel.class);
        viewModel.loadFavoriteUser();
        viewModel.getFavoriteList().observe(this, userList -> userAdapter.setUserList(userList));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
