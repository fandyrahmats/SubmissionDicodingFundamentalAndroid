package com.ashcorps.githubapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashcorps.githubapp.R;
import com.ashcorps.githubapp.adapter.UserAdapter;
import com.ashcorps.githubapp.model.UserItems;
import com.ashcorps.githubapp.ui.favorite.FavoriteActivity;
import com.ashcorps.githubapp.ui.settings.SettingActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_user)
    RecyclerView rvUser;

    @BindView(R.id.searchView)
    SearchView svUser;

    @BindView(R.id.view_shimmer)
    View viewShimmer;

    @BindView(R.id.view_empty)
    View viewEmpty;

    ShimmerFrameLayout placeholder;

    private MainViewModel mainViewModel;
    private UserAdapter mUserAdapter;

    private ArrayList<UserItems> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        prepare();
    }

    private void prepare() {
        Handler handler = new Handler();
        placeholder = findViewById(R.id.placeholder);
        mainViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        mUserAdapter = new UserAdapter(userList);

        rvUser.setLayoutManager(new LinearLayoutManager(this));
        rvUser.setHasFixedSize(true);
        rvUser.setAdapter(mUserAdapter);

        svUser.setIconifiedByDefault(true);
        svUser.setFocusable(true);
        svUser.setIconified(false);
        svUser.requestFocusFromTouch();
        svUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                handler.postDelayed(() -> searching(keyword), 1000);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                searching(keyword);
                return false;
            }
        });

        mainViewModel.getListUser().observe(this, itemsItems -> {
            if (itemsItems.size() <= 0) {
                viewEmpty.setVisibility(View.VISIBLE);
            }
            mUserAdapter.setUserList(itemsItems);
            showLoading(false);
        });
    }

    private void showLoading(boolean isShowing) {
        if (isShowing) {
            viewEmpty.setVisibility(View.GONE);
            viewShimmer.setVisibility(View.VISIBLE);
            placeholder.startShimmer();
        } else {
            viewShimmer.setVisibility(View.GONE);
            placeholder.startShimmer();
        }
    }

    private void searching(String username) {
        if (username.equals("")) {
            mUserAdapter.clearList();
            showLoading(false);
            return;
        }
        showLoading(true);
        mainViewModel.loadUserList(username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intentSetting = new Intent(this, SettingActivity.class);
                startActivity(intentSetting);
                break;
            case R.id.menu_favorite:
                Intent intentFavorite = new Intent(this, FavoriteActivity.class);
                startActivity(intentFavorite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
