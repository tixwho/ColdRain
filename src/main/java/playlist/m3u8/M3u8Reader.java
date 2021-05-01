package playlist.m3u8;

import java.io.IOException;
import exception.ErrorCodes;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import playlist.generic.AbstractPlaylistReader;
import playlist.generic.AbstractPlaylistTable;

public class M3u8Reader extends AbstractPlaylistReader {
    
    String src=null;
    String artist=null;
    String trackTitle=null;
    String LENGTH=null;


    @Override
    protected void initializeMeta() {
        super.setSupportedMeta(M3u8Utils.getSupportedMeta());

    }

    @Override
    protected void readHeading() throws PlaylistIOException {
        // if #EXTINF do normal read
        // if #, skip
        // else, notify.
        try {
            tempData = br.readLine();
            if (checkAnnotation(tempData)) {
                logger.debug("Heading annotation detected");
            }
            tempData = br.readLine();//skip heading 
        } catch (IOException ioe) {
            throw new PlaylistIOException("Error in reading bufferedReader", ioe,
                ErrorCodes.BASE_IO_ERROR);
        }

    }

    @Override
    protected void readASong() throws NativeReflectionException, PlaylistIOException {
        // first line already loaded
        try {
            //skip empty lines
            while(tempData=="") {
                tempData = br.readLine();
            }
            //check if it is annotation
            if(startWith(tempData,"#EXTINF:")) {
                String divider = "<di>";
                //replace all divider with "<di>
                String modStr = tempData;
                modStr = modStr.replace(":",divider);//replace first ":"
                modStr = modStr.replace(",",divider);//replace first ","
                modStr = modStr.replace(" - ",divider);//replace" - " notice the blank;
                String[] arr = modStr.split(divider);
                LENGTH = arr[1];
                artist = arr[2];
                trackTitle = arr[3];
                // read tracklength(in seconds), artist, title;
                tempData = br.readLine();
                //set src and pack a song instance;
                M3u8Song aSong = new M3u8Song();
                src = tempData;
                super.setAllProperties(this,aSong);
                super.songArrList.add(aSong);
                
            }else {
                //set src and pack a song instance;
                M3u8Song aSong = new M3u8Song();
                src = tempData;
                super.setAllProperties(this,aSong);
                super.songArrList.add(aSong);
            }
        }catch (IOException ioe) {
            throw new PlaylistIOException("Error in reading bufferedReader", ioe,
                ErrorCodes.BASE_IO_ERROR);
        }

    }

    @Override
    protected void readEnding() {
        // do nothing. m3u8 does not have ending.

    }

    @Override
    public AbstractPlaylistTable getTable() throws PlaylistIOException {
        M3u8Table rtrTable = new M3u8Table(songArrList);
        rtrTable.setPlaylistSrc(this.playlistSrc);
        return rtrTable;
    }

    private boolean checkAnnotation(String line) {
        try {
        String first = line.substring(0, 1);
        return first.equals("#");
        }catch(StringIndexOutOfBoundsException siobe) {
            return false;
        }
    }
    
    private boolean startWith(String toCheckPhrase,String toCheckHead) {
        //check index
        if(toCheckHead.length()>toCheckPhrase.length()) {
            return false;
        }
        String checking = toCheckPhrase.substring(0,toCheckHead.length());
        return checking.equals(toCheckHead);
    }

    @Override
    protected void resetEntity() {
        songArrList.clear();// flush before use.
        src=null;
        artist=null;
        trackTitle=null;
        LENGTH=null;
        
    }

}
