package database.generic;

import database.service.AudioDBService;
import database.service.PlaylistDBService;
import playlist.generic.BaseLoggingClass;
import playlist.generic.LoggerCtrl;

public abstract class BaseDatabaseLoggingClass extends BaseLoggingClass{
    LoggerCtrl pojoCtrl = new LoggerCtrl(DatabasePOJO.class);
    LoggerCtrl audioDBServiceCtrl = new LoggerCtrl(AudioDBService.class);
    LoggerCtrl playlistDBServiceCtrl = new LoggerCtrl(PlaylistDBService.class);
    
    @Override
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
        pojoCtrl.setLevel(lvl);
        audioDBServiceCtrl.setLevel(lvl);
        playlistDBServiceCtrl.setLevel(lvl);
    }

}
