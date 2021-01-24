package database.testDatabaseModels2;

import playlist.generic.MetaSong;
import toolkit.HibernateUtils;

public class FileProperty1 {
    private int fpid;
    private String bitdepth;
    private int bitrate;
    private int samplerate;
    private File1 fileModel;
    public FileProperty1() {
        
    }
    public FileProperty1(MetaSong meta) {
        this.bitdepth=HibernateUtils.splitFormat(meta.getFORMAT())[1];
        this.bitrate=Integer.parseInt(meta.getBITRATE());
        this.samplerate=Integer.parseInt(meta.getSAMPLERATE());
    }
    public int getFpid() {
        return fpid;
    }
    public void setFpid(int fpid) {
        this.fpid = fpid;
    }
    public String getBitdepth() {
        return bitdepth;
    }
    public void setBitdepth(String bitdepth) {
        this.bitdepth = bitdepth;
    }
    public int getBitrate() {
        return bitrate;
    }
    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }
    public int getSamplerate() {
        return samplerate;
    }
    public void setSamplerate(int samplerate) {
        this.samplerate = samplerate;
    }
    public File1 getFileModel() {
        return fileModel;
    }
    public void setFileModel(File1 fileModel) {
        this.fileModel = fileModel;
    }
    
    
}
