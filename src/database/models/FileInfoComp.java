package database.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import database.generic.DatabasePOJO;
import database.utils.DbHelper;
import playlist.generic.MetaSong;

@Embeddable
public class FileInfoComp extends DatabasePOJO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 2241804180052986786L;
    

    
    
    private String format;
    private String sampleRate;
    private String bitRate;
    private String length;
    private Integer quality;
    
    public Integer getQuality() {
        return quality;
    }

    
    public FileInfoComp(MetaSong meta) {
        this.format= meta.getFORMAT();
        this.sampleRate= meta.getSAMPLERATE();
        this.bitRate=meta.getBITRATE();
        this.length=meta.getLENGTH();
        this.quality=DbHelper.calcAudioQuality(meta);
    }


    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(String sampleRate) {
        this.sampleRate = sampleRate;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
    

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public FileInfoComp() {
        
    }
    
    


    @Override
    public String toString() {
        return "FileInfoComp [format=" + format + ", sampleRate=" + sampleRate + ", bitRate="
            + bitRate + ", length=" + length + ", quality=" + quality + "]";
    }


    @Override
    public int hashCode() {
        return Objects.hash(bitRate, format, length, quality, sampleRate);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileInfoComp other = (FileInfoComp) obj;
        return Objects.equals(bitRate, other.bitRate) && Objects.equals(format, other.format)
            && Objects.equals(length, other.length) && Objects.equals(quality, other.quality)
            && Objects.equals(sampleRate, other.sampleRate);
    }
    
    
    
    
    
    
    

}
