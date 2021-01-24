package playlist.generic;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import toolkit.LogMaker;

public class LoggerCtrl {
    ch.qos.logback.classic.Logger rootlogger;

    public <T> LoggerCtrl(Class<T> setupClass) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        this.rootlogger = loggerContext.getLogger(setupClass);
        // default is error level
        rootlogger.setLevel(Level.ERROR);
    }

    public void setLevel(String lvl) {
        switch (lvl) {
            case "trace":
                rootlogger.setLevel(Level.TRACE);
                break;
            case "debug":
                rootlogger.setLevel(Level.DEBUG);
                break;
            case "info":
                rootlogger.setLevel(Level.INFO);
                break;
            case "warn":
                rootlogger.setLevel(Level.WARN);
                break;
            case "error":
                rootlogger.setLevel(Level.ERROR);
                break;
            default:
                LogMaker.logs(
                    "Unsupported level: please choose from trace, debug, info, warn or error");
                return;
        }
        noticeLevel(lvl);
    }
    
    private void noticeLevel(String in) {
        LogMaker.logs("Successfully set Loglevel in " + rootlogger.getName() +" to " + in);
    }

}
