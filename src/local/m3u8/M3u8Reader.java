package local.m3u8;

import java.io.IOException;
import exception.ErrorCodes;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistTable;

public class M3u8Reader extends AbstractPlaylistReader {
    

    @Override
    protected void initializeMeta() {
        super.setSupportedMeta(M3u8Utils.getSupportedMeta());

    }

    @Override
    protected void readHeading() throws PlaylistIOException {
        // TODO Auto-generated method stub
        //if #EXTINF do normal read
        //if #, skip
        //else, notify.
        try {
            tempData=br.readLine();
            if(checkAnnotation(tempData)) {
                logger.debug("Heading annotation detected");
            }
        } catch (IOException ioe) {
            throw new PlaylistIOException("Error in reading bufferedReader",ioe,ErrorCodes.BASE_IO_ERROR);
        }

    }

    @Override
    protected void readASong() throws NativeReflectionException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void readEnding() {
        // TODO Auto-generated method stub

    }

    @Override
    public AbstractPlaylistTable getTable() throws PlaylistIOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    private boolean checkAnnotation(String line) {
        String first = line.substring(0, 1);
        return first.equals("#");
    }

}
