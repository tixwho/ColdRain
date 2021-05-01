package ncm.models;

import ncm.jsonSupp.Album;
import ncm.jsonSupp.JsonRootBean;
import ncm.jsonSupp.OfflineRootBean;

public class NcmAudioInfoComp {

    // JsonRootBean, should include a complete Json decompiled info from web_track:track

    private JsonRootBean track_json_complete;
    
    // OfflineRootBean, should include a complete Json decompiled info from web_offline_track:detail
    
    private OfflineRootBean offline_detail_json_complete;
    
    private Dj dj;

    // the following will present in every track, regardless of download state
    private long track_id; // called tid in web_track
    private String title; // called name in web_track
    // for alias info plz check jsonBean info.
    private Album album; // directly retrieved from jsonBean; may not be accurate for DJ_program



    private boolean downloaded_flag;

    // if playlist(existed in web_offline_track), then the following will not be null;

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
    // for dj program, cover art is not accurate, so we need to record the actual cover url
    private boolean is_DJ;
    private String dj_coverUrl;

    public NcmAudioInfoComp() {

    }

    /**
     * Set up track_json_complete, track_id, title, and album
     * @param jsonBean input jsonBean from web_track
     */
    public NcmAudioInfoComp(JsonRootBean jsonBean) {
        initFromTrackJson(jsonBean);
    }
    
    /**
     * Set up offline_detail_json_complete, track_id, title, album(may not be accurate)
     * @param jsonBean
     */
    public NcmAudioInfoComp(OfflineRootBean jsonBean) {
        initFromOfflineDetailJson(jsonBean);
    }
    
    public void initFromTrackJson(JsonRootBean jsonBean) {
     // first setup Bean
        setTrack_json_complete(jsonBean);
        setTrack_id(jsonBean.getId());
        setTitle(jsonBean.getName());
        setAlbum(jsonBean.getAlbum());
    }
    
    public void initFromOfflineDetailJson(OfflineRootBean jsonBean) {
        setOffline_detail_json_complete(jsonBean);
        setDownloaded_flag(true); // since it comes fron web_offline_track, must be downloaded
        setTrack_id(jsonBean.getId());
        setTitle(jsonBean.getName());
        setAlbum(jsonBean.getAlbum());//could be inaccurate for program!
        //tell if this is a DJ program
        if(jsonBean.getProgram()==null) {
            setIs_DJ(false);
            
        }else {
            setIs_DJ(true);
            setDj_coverUrl(jsonBean.getProgram().getCoverUrl());
        }
        
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

    public String getDj_coverUrl() {
        return dj_coverUrl;
    }

    public void setDj_coverUrl(String dj_coverUrl) {
        this.dj_coverUrl = dj_coverUrl;
    }

    public OfflineRootBean getOffline_detail_json_complete() {
        return offline_detail_json_complete;
    }

    public void setOffline_detail_json_complete(OfflineRootBean offline_detail_json_complete) {
        this.offline_detail_json_complete = offline_detail_json_complete;
    }

}
