package tables;

public class M3USong {
    private String src;
    private MetaSong metaSong;
    private boolean isMetaEd = false;
    
    public M3USong() {
        this.src = "";
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public MetaSong getMetaSong() {
        return metaSong;
    }

    public void setMetaSong(MetaSong metaSong) {
        this.metaSong = metaSong;
        this.isMetaEd = true;
    }
    
    public boolean checkMeta() {
        return this.isMetaEd;
    }
    
    
    
    

}
