package com.kkbox.openapiclient;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import com.kkbox.openapiclient.TrackInfo;

/**
 * Created by sharonyang on 2017/10/3.
 */

public class TrackListAdapter extends BaseAdapter{

    private ArrayList<TrackInfo> items;
    private Activity activity;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
