package com.kkbox.openapiclient;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.kkbox.openapiclient.TrackInfo;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

/**
 * Created by sharonyang on 2017/10/3.
 */

public class TrackListAdapter extends BaseAdapter{

    ArrayList<TrackInfo> items;
    Activity activity;

    public TrackListAdapter(Activity activity) {
        this.activity = activity;
        this.items = new ArrayList();
    }

    private static final class ViewHolder {
        private TextView txtTrackName;
        private TextView txtArtist;
        private ImageView imgAlbum;

        public final TextView getTxtTrackName() {return this.txtTrackName;}
        public final void setTxtTrackName(TextView trackName) {this.txtTrackName = trackName; }
        public final TextView getTxtArtist() {return  this.txtArtist;}
        public final void setTxtArtist(TextView artist) {this.txtArtist = artist;}
        public final ImageView getImgAlbum() {return this.imgAlbum;}
        public final void setImgAlbum(ImageView album) {this.imgAlbum = album;}

        public ViewHolder(View row) {
            this.txtTrackName = (TextView)(row != null?row.findViewById(R.id.txtTrackName):null);
            this.txtArtist = (TextView)(row != null?row.findViewById(R.id.txtArtist):null);
            this.imgAlbum = (ImageView)(row != null?row.findViewById(R.id.imgAlbum):null);
        }
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TrackListAdapter.ViewHolder viewHolder;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.track_list_row, null);
            viewHolder = new TrackListAdapter.ViewHolder(view);
            if(view != null) {
                view.setTag(viewHolder);
            }
        } else {
            view = convertView;
            viewHolder = (TrackListAdapter.ViewHolder) view.getTag();
        }

        TrackInfo trackInfo = this.items.get(position);
        TextView text = viewHolder.getTxtTrackName();
        if(text != null) {
            text.setText(trackInfo.name);
        }

        text = viewHolder.getTxtArtist();
        if(text != null) {
            text.setText(trackInfo.artist);
        }

        Ion.with(viewHolder.getImgAlbum()).load(trackInfo.albumImage);
        return view;
    }
}
