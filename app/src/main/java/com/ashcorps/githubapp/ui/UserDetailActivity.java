package com.ashcorps.githubapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ashcorps.githubapp.R;
import com.ashcorps.githubapp.adapter.ViewPagerAdapter;
import com.ashcorps.githubapp.db.UserFavoriteHelper;
import com.ashcorps.githubapp.model.UserItems;
import com.ashcorps.githubapp.ui.followers.FollowersFragment;
import com.ashcorps.githubapp.ui.following.FollowingFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_ITEM = "KEY_ITEM";

    @BindView(R.id.container)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.img_profile)
    ImageView imgProfile;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_id)
    TextView tvId;

    @BindView(R.id.floating_fav)
    FloatingActionButton fabFav;

    private UserFavoriteHelper userFavoriteHelper;

    private UserItems profileItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);

        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        userFavoriteHelper = new UserFavoriteHelper(realm);

        if (getIntent() != null) {
            profileItem = getIntent().getParcelableExtra(KEY_ITEM);
        }

        Glide.with(this)
                .load(profileItem.getAvatarUrl())
                .placeholder(getResources().getDrawable(R.drawable.ic_person_placeholder))
                .centerCrop()
                .into(imgProfile);

        tvName.setText(profileItem.getLogin());

        String id = "ID : " + profileItem.getId();

        tvId.setText(id);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }

        prepareViewPager(viewPager);
        tab.setupWithViewPager(viewPager);

        fabFav.setOnClickListener(this);

        if (checkFavoriteUser()) {
            fabFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
        } else {
            fabFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_off));
        }

    }

    private void prepareViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new FollowersFragment(profileItem.getLogin()), getResources().getString(R.string.title_tab_followers));
        viewPagerAdapter.addFragment(new FollowingFragment(profileItem.getLogin()), getResources().getString(R.string.title_tab_following));
        viewPager.setAdapter(viewPagerAdapter);
    }

    private boolean checkFavoriteUser() {
        return userFavoriteHelper.favorite(profileItem.getId());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.floating_fav) {
            if (checkFavoriteUser()) {
                userFavoriteHelper.delete(profileItem.getId());
                fabFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_off));
                Toast.makeText(this, getResources().getString(R.string.deleted_favorite), Toast.LENGTH_SHORT).show();
            } else {
                fabFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
                userFavoriteHelper.save(profileItem);
                Toast.makeText(this, getResources().getString(R.string.added_favorite), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
