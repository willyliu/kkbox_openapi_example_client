package com.kkbox.openapiclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private OpenApiWrapper openApi;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // request access token
        openApi = new OpenApiWrapper(getApplicationContext()).init();
        presenter = new MainPresenter(this, openApi);

        //set search view listener
        final SearchView searchView = findViewById(R.id.search_bar);
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                presenter.search(keyword);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        final ListView listView = findViewById(R.id.listView);
        TrackListAdapter adapter = new TrackListAdapter(this);
        if(listView != null) {
            listView.setAdapter(adapter);
        }
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView var1, int var2) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
                    presenter.loadMore();
                }
            }
        });
    }

    @Override
    public void showTracks(List<TrackInfo> tracks) {
        updateTracks(tracks, true);
    }

    @Override
    public void showMoreTracks(List<TrackInfo> tracks) {
        updateTracks(tracks, false);
    }

    private void updateTracks(List<TrackInfo> tracks, boolean reset) {
        final ListView listView = MainActivity.this.findViewById(R.id.listView);
        TrackListAdapter trackListAdapter = (TrackListAdapter)listView.getAdapter();

        if (reset) {
            trackListAdapter.items.clear();
        }
        trackListAdapter.items.addAll(tracks);

        trackListAdapter.notifyDataSetChanged();
    }

}
