package ncm.models;

import ncm.jsonSupp.Album;
import ncm.jsonSupp.JsonRootBean;

public class NcmAudioInfoComp {

    // JsonRootBean, should include a complete Json decompiled info from web_track:track

    private JsonRootBean track_json_complete;

    // the following will present in every track, regardless of download state
    private long track_id; // called tid in web_track
    private String title; // called name in web_track
    // for alias info plz check jsonBean info.
    private Album album; // directly retrieved from jsonBean



    private boolean downloaded_flag;

    // if local(existed in web_offline_track), then the following will not be null;

    @Override
    public String toString() {
        return "NcmAudioInfoComp [track_id=" + track_id + ", title=" + title + ", album="
            + album.getName() + ", downloaded_flag=" + downloaded_flag + ", relative_path="
            + relative_path + ", real_suffix=" + real_suffix + ", ncm_flag=" + ncm_flag + ", is_DJ="
            + is_DJ + "]";
    }

    private String relative_path;
    private String real_suffix;
    private boolean ncm_flag;

    // this is telled from web_offline_track:extra_type not null, so DJ tracks must be downloaded.
    // if dj program, then "program" is activated in OfflineRootBean
    private boolean is_DJ;

    public NcmAudioInfoComp() {

    }

    /**
     * Set up track_json_complete, track_id, title, and album
     * @param jsonBean input jsonBean from web_track
     */
    public NcmAudioInfoComp(JsonRootBean jsonBean) {
        // first setup Bean
        setTrack_json_complete(jsonBean);
        setTrack_id(jsonBean.getId());
        setTitle(jsonBean.getName());
        setAlbum(jsonBean.getAlbum());
    }



    public long getTrack_id() {
        return track_id;
    }

    public void setTrack_id(long track_id) {
        this.track_id = track_id;
    }

    public String getRelative_path() {
        return relative_path;
    }

    public void setRelative_path(String relative_path) {
        this.relative_path = relative_path;
    }

    public String getReal_suffix() {
        return real_suffix;
    }

    public void setReal_suffix(String real_suffix) {
        this.real_suffix = real_suffix;
    }

    public boolean isNcm_flag() {
        return ncm_flag;
    }

    public void setNcm_flag(boolean ncm_flag) {
        this.ncm_flag = ncm_flag;
    }

    public JsonRootBean getTrack_json_complete() {
        return track_json_complete;
    }

    public void setTrack_json_complete(JsonRootBean track_json_complete) {
        this.track_json_complete = track_json_complete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public boolean isDownloaded_flag() {
        return downloaded_flag;
    }

    public void setDownloaded_flag(boolean downloaded_flag) {
        this.downloaded_flag = downloaded_flag;
    }

    public boolean isIs_DJ() {
        return is_DJ;
    }

    public void setIs_DJ(boolean is_DJ) {
        this.is_DJ = is_DJ;
    }

}
