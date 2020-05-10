package local.generic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toolkit.Timer;

public abstract class BaseTestingClass {
    public static Logger logger = LoggerFactory.getLogger(BaseTestingClass.class);
    public LoggerCtrl selfCtrl = new LoggerCtrl(BaseTestingClass.class);
    public LoggerCtrl readerCtrl = new LoggerCtrl(AbstractPlaylistReader.class);
    public LoggerCtrl tableCtrl = new LoggerCtrl(AbstractPlaylistTable.class);
    public LoggerCtrl writerCtrl = new LoggerCtrl(AbstractPlaylistWriter.class);
    public static Timer tim = new Timer();
    
    public BaseTestingClass() {
        logger.error("TESTING CLASS INITIALIZED: All logging system set to Error");
        logger.error("Supported logger: self,reader,table,writer");
        logger.error("TIMER INITILIZED: call tim");
        logger.error("------SETUP COMPLETE------");
    }
}
