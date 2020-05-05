package old.localModels;

//atom级 包含每首歌的属性
public class M3U8Song {
    private String unknownNum;
    private String trackTitle;
    private String src;
    
    public M3U8Song() {
        this.src = "";
        this.trackTitle = "";
        this.unknownNum = "";
    }
    //getters & setters
    public String getUnknownNum() {
        return unknownNum;
    }
    public void setUnknownNum(String unknownNum) {
        this.unknownNum = unknownNum;
    }
    public String getTrackTitle() {
        return trackTitle;
    }
    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }
    public String getSrc() {
        return src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    
    

}
