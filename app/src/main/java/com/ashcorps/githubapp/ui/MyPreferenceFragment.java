package com.ashcorps.githubapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.ashcorps.githubapp.R;
import com.ashcorps.githubapp.helper.ThemeHelper;
import com.ashcorps.githubapp.receiver.AlarmReceiver;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MyPreferenceFragment.class.getSimpleName();

    private String REMINDER;
    private String MODE;

    private Context mContext;
    private CheckBoxPreference isReminderPreference, isDarkMode;
    private AlarmReceiver alarmReceiver;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }

    private void init() {
        alarmReceiver = new AlarmReceiver();
        REMINDER = getResources().getString(R.string.reminder);
        MODE = getResources().getString(R.string.mode);

        isReminderPreference = findPreference(REMINDER);
        isDarkMode = findPreference(MODE);
    }

    private void setSummaries() {
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        isReminderPreference.setChecked(sharedPreferences.getBoolean(REMINDER, false));
        isDarkMode.setChecked(sharedPreferences.getBoolean(MODE, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(REMINDER)) {
            isReminderPreference.setChecked(sharedPreferences.getBoolean(REMINDER, false));
            if (isReminderPreference.isChecked()) {
                alarmReceiver.setReminder(mContext);
            } else {
                alarmReceiver.cancelReminder(mContext);
            }
        }
        if (key.equals(MODE)) {
            isDarkMode.setChecked(sharedPreferences.getBoolean(MODE, false));
            if (isDarkMode.isChecked()) {
                ThemeHelper.applyTheme(ThemeHelper.darkMode);
            } else {
                ThemeHelper.applyTheme(ThemeHelper.lightMode);
            }
        }
    }
}
