package ncm.models;

public class NcmAudioInfoComp{
    
    private long track_id;
    private String relative_path;
    private String real_suffix;
    private boolean ncm_flag;
    
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
}
