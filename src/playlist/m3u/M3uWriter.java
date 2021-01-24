package playlist.m3u;

import java.io.IOException;
import exception.ErrorCodes;
import exception.PlaylistIOException;
import playlist.generic.AbstractPlaylistSong;
import playlist.generic.AbstractPlaylistWriter;

public class M3uWriter extends AbstractPlaylistWriter {


    @Override
    protected void writeASong(AbstractPlaylistSong aPlaylistSong) throws PlaylistIOException {
        try {
            oStreamWriter.append(aPlaylistSong.getSrc() + "\r\n");
        } catch (IOException ioe) {
            throw new PlaylistIOException("M3UWriter IO Exception", ioe, ErrorCodes.BASE_IO_ERROR);
        }
        logger.trace("written:" + aPlaylistSong.getSrc());

    }

    @Override
    protected void writeHeading() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void writeEnding() {
        // TODO Auto-generated method stub
        
    }
    

}
