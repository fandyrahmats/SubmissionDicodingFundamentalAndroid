package com.ashcorps.githubapp.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashcorps.githubapp.R;
import com.ashcorps.githubapp.ui.MyPreferenceFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.settings));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new MyPreferenceFragment()).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
