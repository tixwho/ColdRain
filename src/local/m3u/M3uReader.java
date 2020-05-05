package local.m3u;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import local.generic.AbstractPlaylistReader;
import local.generic.SupportedMeta;
import toolkit.LogMaker;

public class M3uReader extends AbstractPlaylistReader {
    SupportedMeta[] suppMeta = M3uUtils.getSupportedMeta();
    String src;

    @Override
    // customize exception to
    //IOE-> FileOperationEx Nosuch/Security->MethodConstructEx IllegalAccess/IllArg/Inv->MethodInvEx
    //todo: move reflective method to generic.
    public void read(File f) throws IOException, NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
        //IMPORTANT! SET supportedMeta Array to SuperClass.
        this.setSupportedMeta(suppMeta);
        //test
        LogMaker.logs("suppMeta:" + super.suppMeta[0]);
        InputStreamReader in = new InputStreamReader(new FileInputStream(f), "utf-8");
        BufferedReader br = new BufferedReader(in);
        String tempData; // each lines contains a src.
        while ((tempData = br.readLine()) != null) {
            M3uPlaylistSong aSong = new M3uPlaylistSong();
            src=tempData;
            //move everything beneath to generic
            setAllProperties(this,aSong);
            songArrList.add(aSong);
        }
        br.close();

    }

}
