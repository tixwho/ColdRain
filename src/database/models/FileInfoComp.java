package database.models;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.generic.DatabasePOJO;
import database.utils.InitSessionFactory;
import local.generic.MetaSong;

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
    
    public FileInfoComp() {
        
    }
    
    public FileInfoComp(MetaSong meta) {
        this.format= meta.getFORMAT();
        this.sampleRate= meta.getSAMPLERATE();
        this.bitRate=meta.getBITRATE();
        this.length=meta.getLENGTH();
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
    
    
    
    
    

}
