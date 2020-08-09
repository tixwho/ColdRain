package database.models;

import java.io.Serializable;
import javax.persistence.Embeddable;
import org.apache.commons.io.FilenameUtils;
import database.generic.DatabasePOJO;
import local.generic.AbstractPlaylistTable;

@Embeddable
public class PlaylistInfoComp extends DatabasePOJO implements Serializable {
    
    public PlaylistInfoComp() {
        
    }
    
    public PlaylistInfoComp(AbstractPlaylistTable unknownTable) {
        this.format=FilenameUtils.getExtension(unknownTable.getPlaylistSrc());
    }

    /**
     * 
     */
    private static final long serialVersionUID = -3099254095985357239L;
    
    private String format;
    //could be extended for future use

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    
    
    
    

}
