package ncm.models;

public class NcmNegatedAudioInfo {
    private long track_id;
    private long playlist_id;
    private String playlist_name;
    private int inlist_order;

    public NcmNegatedAudioInfo() {

    }

    public NcmNegatedAudioInfo(long track_id, long playlist_id, String playlist_name,
        int inlist_order) {
        setTrack_id(track_id);
        setPlaylist_id(playlist_id);
        setPlaylist_name(playlist_name);
        setInlist_order(inlist_order);
    }

    public long getTrack_id() {
        return track_id;
    }

    public void setTrack_id(long track_id) {
        this.track_id = track_id;
    }

    public long getPlaylist_id() {
        return playlist_id;
    }

    public void setPlaylist_id(long playlist_id) {
        this.playlist_id = playlist_id;
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public int getInlist_order() {
        return inlist_order;
    }

    public void setInlist_order(int inlist_order) {
        this.inlist_order = inlist_order;
    }

    @Override
    public String toString() {
        return "NcmNegatedAudioInfo [track_id=" + track_id + ", playlist_id=" + playlist_id
            + ", playlist_name=" + playlist_name + ", inlist_order=" + inlist_order + "]";
    }

}
