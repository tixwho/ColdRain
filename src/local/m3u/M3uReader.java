package local.m3u;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import exception.ErrorCodes;
import exception.NativeReflectionException;
import exception.PlaylistIOException;
import local.generic.AbstractPlaylistReader;
import local.generic.AbstractPlaylistTable;
import local.generic.SupportedMeta;
import toolkit.LogMaker;

public class M3uReader extends AbstractPlaylistReader {
    SupportedMeta[] suppMeta = M3uUtils.getSupportedMeta();
    String src;

    @Override
    // customize exception .
    public void read(File f) throws PlaylistIOException, NativeReflectionException {
        try {
            // IMPORTANT! SET supportedMeta Array to SuperClass.
            this.setSupportedMeta(suppMeta);
            // test
            LogMaker.logs("suppMeta:" + super.suppMeta[0]);
            InputStreamReader in = new InputStreamReader(new FileInputStream(f), "utf-8");
            BufferedReader br = new BufferedReader(in);
            String tempData; // each lines contains a src.
            while ((tempData = br.readLine()) != null) {
                M3uSong aSong = new M3uSong();
                src = tempData;
                // move everything beneath to generic
                setAllProperties(this, aSong);
                songArrList.add(aSong);
            }
            br.close();
        } catch (IOException ioe) {
            throw new PlaylistIOException("M3UReader IO Exception", ioe, ErrorCodes.BASE_IO_ERROR);
        }

    }

    @Override
    public AbstractPlaylistTable getTable() {

        return new M3uTable(songArrList, suppMeta);
    }

}
