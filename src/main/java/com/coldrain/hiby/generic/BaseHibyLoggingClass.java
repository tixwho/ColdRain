package com.coldrain.hiby.generic;

import com.coldrain.playlist.generic.BaseLoggingClass;
import com.coldrain.playlist.generic.LoggerCtrl;
import com.coldrain.toolkit.Utf8Normalizer;

public class BaseHibyLoggingClass extends BaseLoggingClass {
    
    public LoggerCtrl utf8SwitcherCtrl = new LoggerCtrl(Utf8Normalizer.class);
    
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
        utf8SwitcherCtrl.setLevel(lvl);
    }

}
