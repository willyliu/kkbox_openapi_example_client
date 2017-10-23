package com.kkbox.openapiclient;

import java.util.List;

public interface MainContract {

    interface View {
        void showTracks(List<TrackInfo> tracks);
        void showMoreTracks(List<TrackInfo> tracks);
    }

    interface Presenter {
        void search(String keyword);
        void loadMore();
    }

}
