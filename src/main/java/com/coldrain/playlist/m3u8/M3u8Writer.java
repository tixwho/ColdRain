package com.coldrain.playlist.m3u8;

import com.coldrain.exception.ErrorCodes;
import com.coldrain.exception.PlaylistIOException;
import com.coldrain.playlist.generic.AbstractPlaylistSong;
import com.coldrain.playlist.generic.AbstractPlaylistWriter;

import java.io.IOException;

public class M3u8Writer extends AbstractPlaylistWriter {

    @Override
    protected void writeHeading() throws PlaylistIOException {
        try {
            oStreamWriter.append("#EXTM3U" + "\r\n");
        } catch (IOException ioe) {
            throw new PlaylistIOException("M3UWriter IO Exception", ioe, ErrorCodes.BASE_IO_ERROR);
        }
        logger.trace("heading written: EXTM3U");

    }

    @Override
    protected void writeEnding() {
        // nothing to write for m3u8

    }

    @Override
    protected void writeASong(AbstractPlaylistSong aPlaylistSong) throws PlaylistIOException {
        M3u8Song castedSong = (M3u8Song)aPlaylistSong;
        try {
            oStreamWriter.append("#EXTINF:");//identifier
            oStreamWriter.append(castedSong.getLENGTH()+",");//tracklength
            oStreamWriter.append(castedSong.getArtist()+" - ");//artist
            oStreamWriter.append(castedSong.getTrackTitle());//title
            oStreamWriter.append("\r\n");//next line;
            oStreamWriter.append(castedSong.getSrc());//src
            oStreamWriter.append("\r\n");//next line;
            
        } catch (IOException ioe) {
            throw new PlaylistIOException("M3UWriter IO Exception", ioe, ErrorCodes.BASE_IO_ERROR);
        }
        logger.trace("written:" + aPlaylistSong.getSrc());

    }

}
