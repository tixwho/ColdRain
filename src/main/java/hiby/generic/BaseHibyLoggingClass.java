package hiby.generic;

import playlist.generic.BaseLoggingClass;
import playlist.generic.LoggerCtrl;
import toolkit.Utf8Normalizer;

public class BaseHibyLoggingClass extends BaseLoggingClass {
    
    public LoggerCtrl utf8SwitcherCtrl = new LoggerCtrl(Utf8Normalizer.class);
    
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
        utf8SwitcherCtrl.setLevel(lvl);
    }

}
