package database.testDatabaseModels2;

import local.generic.MetaSong;

public class File1 {
    
    private int fileID;
    private String src;
    private FileProperty1 fileProperty;
    private Meta1 origMeta;
    private Meta1 modifiedMeta;
    
    public File1() {
        
    }
    
    public File1(MetaSong meta) {
        this.src=meta.getSrc();
    }
    public int getFileID() {
        return fileID;
    }
    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
    public String getSrc() {
        return src;
    }
    public void setSrc(String src) {
        this.src = src;
    }
    public FileProperty1 getFileProperty() {
        return fileProperty;
    }
    public void setFileProperty(FileProperty1 fileProperty) {
        this.fileProperty = fileProperty;
    }

    public Meta1 getOrigMeta() {
        return origMeta;
    }

    public void setOrigMeta(Meta1 origMeta) {
        this.origMeta = origMeta;
    }

    public Meta1 getModifiedMeta() {
        return modifiedMeta;
    }

    public void setModifiedMeta(Meta1 modifiedMeta) {
        this.modifiedMeta = modifiedMeta;
    }

}
