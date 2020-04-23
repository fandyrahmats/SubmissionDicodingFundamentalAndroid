package com.ashcorps.consumerapp.favorite;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashcorps.consumerapp.DatabaseContract;
import com.ashcorps.consumerapp.MappingHelper;
import com.ashcorps.consumerapp.R;
import com.ashcorps.consumerapp.adapter.UserAdapter;
import com.ashcorps.consumerapp.model.UserItems;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements LoadUserCallback {

    private static final String TAG = FavoriteActivity.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @BindView(R.id.rv_favorite)
    RecyclerView rvFavorite;

    @BindView(R.id.placeholder)
    ShimmerFrameLayout placeholder;

    @BindView(R.id.view_placeholder)
    View viewPlaceholder;

    UserAdapter userAdapter;

    private ArrayList<UserItems> favoriteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.favorite));
        }

        userAdapter = new UserAdapter(favoriteList);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this));
        rvFavorite.setAdapter(userAdapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContract.UserColumns.CONTENT_URI, true, myObserver);
        if (savedInstanceState == null) {
            new LoadUserAsync(this, this).execute();
        } else {
            ArrayList<UserItems> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                userAdapter.setUserList(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, userAdapter.getmUserList());
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                placeholder.startShimmer();
            }
        });
    }

    @Override
    public void postExecute(ArrayList<UserItems> users) {
        viewPlaceholder.setVisibility(View.GONE);
        placeholder.stopShimmer();
        Log.d(TAG, "FavoriteUserEmpty " + users.size());
        if (users.size() >= 1) {
            userAdapter.setUserList(users);
        } else {
            Log.d(TAG, "FavoriteUserEmpty ");
        }
    }

    public class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadUserAsync(context, (LoadUserCallback) context).execute();
        }
    }

    private static class LoadUserAsync extends AsyncTask<Void, Void, ArrayList<UserItems>> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadUserCallback> weakCallback;

        public LoadUserAsync(Context context, LoadUserCallback callback) {
            this.weakContext = new WeakReference<>(context);
            this.weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<UserItems> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor cursor = context.getContentResolver().query(DatabaseContract.UserColumns.CONTENT_URI,
                    null, null, null, null);
            return MappingHelper.mapCursorToArrayList(cursor);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<UserItems> userItems) {
            super.onPostExecute(userItems);
            weakCallback.get().postExecute(userItems);
        }
    }
}