package playlist.generic;

/**
 * Most basic function of SongInstance: contains src info. M3U song might be just an extension of
 * this without any addition, but keep it abstract make things clear
 * 
 * @author tixwho
 *
 */
public abstract class AbstractPlaylistSong {

    /**
     * song src path. preferrably absolute for future integration.
     */
    protected String src;

    
    /**
     * return song path.
     * @return song path
     */
    public String getSrc() {
        return src;
    }
    
    public void setSrc(String src) {
        this.src = src;
    }
}
