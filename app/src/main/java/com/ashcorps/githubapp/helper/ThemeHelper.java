package com.ashcorps.githubapp.helper;

import androidx.appcompat.app.AppCompatDelegate;

public final class ThemeHelper {

    public static final String lightMode = "light";
    public static final String darkMode = "dark";

    public static void applyTheme(String theme) {
        if (theme.equals(lightMode)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

}
