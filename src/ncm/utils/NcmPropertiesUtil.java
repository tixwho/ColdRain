package ncm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import javax.swing.filechooser.FileSystemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NcmPropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(NcmPropertiesUtil.class);

    // could be empty. check before use.
    public static String ncmCacheDirPath;
    public static String ncmMusicDirPath;

    public static void readNcmProperties(File ncmPropFile) {
        try {
            InputStream in = new FileInputStream(ncmPropFile);
            Properties prop = new Properties();
            prop.load(in);
            FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
            File defaultDbFile = new File(fsv.getHomeDirectory().getParent(),"/AppData/Local/Netease/CloudMusic/Library");
            ncmCacheDirPath =
                prop.getProperty("cacheDir", defaultDbFile.getAbsolutePath());
            ncmMusicDirPath = prop.getProperty("musicDir", "");
            logger.info("ncmCacheDir:" + ncmCacheDirPath);
            logger.info("ncmMusicDir:" + ncmMusicDirPath);
        } catch (IOException ioe) {
            logger.warn("Failed reading ncm properties! Ncm related functions will be disabled.");
            ncmCacheDirPath = "";
            ncmMusicDirPath = "";
        }

    }

    //
    public static void writeNcmProperties(File ncmPropFile, Map<String, String> propMap) {
        Properties prop = new Properties();
        for (Map.Entry<String, String> entry : propMap.entrySet()) {
            prop.setProperty(entry.getKey(), entry.getValue());
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(ncmPropFile);
            prop.store(fos, "ncmProps stored");
            logger.info("Stored ncmProperties into" + ncmPropFile.getAbsolutePath());
        } catch (IOException ioe) {
            logger.warn("Failed storing properties into " + ncmPropFile.getPath());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
