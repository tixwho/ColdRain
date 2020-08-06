package database.generic;

import local.generic.BaseLoggingClass;
import local.generic.LoggerCtrl;

public abstract class BaseDatabaseLoggingClass extends BaseLoggingClass{
    LoggerCtrl pojoCtrl = new LoggerCtrl(DatabasePOJO.class);
    LoggerCtrl navigatorCtrl = new LoggerCtrl(DbNavigator.class);
    
    @Override
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
        pojoCtrl.setLevel(lvl);
        navigatorCtrl.setLevel(lvl);
    }

}
