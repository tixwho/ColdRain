package com.coldrain.database.models;

import com.coldrain.database.generic.DatabasePOJO;
import com.coldrain.playlist.generic.AbstractPlaylistTable;
import org.apache.commons.io.FilenameUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;

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
