package com.kkbox.openapiclient;

/**
 * Created by sharonyang on 2017/10/3.
 */

public class TrackInfo {

    String name = "";
    String artist = "";
    String albumImage = "";

    public TrackInfo() {}

    public TrackInfo(String name, String artist, String albumImage) {
        this.name = name;
        this.artist = artist;
        this.albumImage = albumImage;
    }
}
