package web.generic;

import local.generic.BaseLoggingClass;
import local.generic.LoggerCtrl;
import toolkit.Utf8Normalizer;

public class BaseWebLoggingClass extends BaseLoggingClass {
    
    public LoggerCtrl utf8SwitcherCtrl = new LoggerCtrl(Utf8Normalizer.class);
    
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
        utf8SwitcherCtrl.setLevel(lvl);
    }

}
