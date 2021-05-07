package com.coldrain.ncm.utils;

import java.io.File;
import java.util.StringJoiner;
import javax.swing.filechooser.FileSystemView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:ncm.properties")
public class NcmConfig {

    @Value("${ncm.cacheDir:_default}")
    private String ncmCacheDirPath;

    @Value("${ncm.musicDir}")
    private String ncmMusicDirPath;

    @Value("${ncm.dumpExecutablePath}")
    private String ncmDumpExecutablePath;

    //modified to meet default cache location!
    public String getNcmCacheDirPath() {
        if (ncmCacheDirPath.equals("_default")) {
            //calculate file, set it up for once
            FileSystemView fsv = FileSystemView.getFileSystemView();
            File calculatedCacheDBFile = new File(fsv.getHomeDirectory().getParent(),
                "/AppData/Local/Netease/CloudMusic/Library");
            this.ncmCacheDirPath = calculatedCacheDBFile.getAbsolutePath();
        }
        return ncmCacheDirPath;
    }

    public String getNcmMusicDirPath() {
        return ncmMusicDirPath;
    }

    public String getNcmDumpExecutablePath() {
        return ncmDumpExecutablePath;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NcmConfig.class.getSimpleName() + "[", "]")
            .add("ncmCacheDirPath='" + ncmCacheDirPath + "'")
            .add("ncmMusicDirPath='" + ncmMusicDirPath + "'")
            .add("ncmDumpExecutablePath='" + ncmDumpExecutablePath + "'")
            .toString();
    }
}
