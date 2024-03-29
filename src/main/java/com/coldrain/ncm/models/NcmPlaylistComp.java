package com.coldrain.ncm.models;

import java.util.ArrayList;
import java.util.List;

public class NcmPlaylistComp {
    private String playlistName;
    private long playlist_id;

    public long getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(long playlist_id) {
        this.playlist_id = playlist_id;
    }

    private List<NcmAudioInfoComp> playlistRecordList = new ArrayList<NcmAudioInfoComp>();
    private List<NcmNegatedAudioInfo> negatedPlaylistRecordList =
        new ArrayList<NcmNegatedAudioInfo>();

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<NcmAudioInfoComp> getPlaylistRecordList() {
        return playlistRecordList;
    }

    public void setPlaylistRecordList(List<NcmAudioInfoComp> playlistRecordList) {
        this.playlistRecordList = playlistRecordList;
    }

    public List<NcmNegatedAudioInfo> getNegatedPlaylistRecordList() {
        return negatedPlaylistRecordList;
    }

    public void setNegatedPlaylistRecordList(List<NcmNegatedAudioInfo> negatedPlaylistRecordList) {
        this.negatedPlaylistRecordList = negatedPlaylistRecordList;
    }


}
