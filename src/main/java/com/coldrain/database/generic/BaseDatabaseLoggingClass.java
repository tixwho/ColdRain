package com.coldrain.database.generic;

import com.coldrain.database.service.AudioDBService_legacy;
import com.coldrain.database.service.PlaylistDBService_legacy;
import com.coldrain.playlist.generic.BaseLoggingClass;
import com.coldrain.playlist.generic.LoggerCtrl;

public abstract class BaseDatabaseLoggingClass extends BaseLoggingClass{
    LoggerCtrl pojoCtrl = new LoggerCtrl(DatabasePOJO.class);
    LoggerCtrl audioDBServiceCtrl = new LoggerCtrl(AudioDBService_legacy.class);
    LoggerCtrl playlistDBServiceCtrl = new LoggerCtrl(PlaylistDBService_legacy.class);
    
    @Override
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
        pojoCtrl.setLevel(lvl);
        audioDBServiceCtrl.setLevel(lvl);
        playlistDBServiceCtrl.setLevel(lvl);
    }

}
