package com.ashcorps.githubapp;

public final class ApiEndPoint {

    public static final String USERNAME_PARAM = "username";

    public static final String ENDPOINT_SEARCH_USER = BuildConfig.BASE_URL +
            "/search/users?q={username}";

    public static final String ENDPOINT_DETAIL_USER = BuildConfig.BASE_URL +
            "/users/{username}";

    public static final String ENDPOINT_FOLLOWERS_USER = BuildConfig.BASE_URL +
            "/users/{username}/followers";

    public static final String ENDPOINT_FOLLOWING_USER = BuildConfig.BASE_URL +
            "/users/{username}/following";
}
