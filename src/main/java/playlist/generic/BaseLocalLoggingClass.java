package playlist.generic;

public abstract class BaseLocalLoggingClass extends BaseLoggingClass{
    
    public LoggerCtrl readerCtrl = new LoggerCtrl(AbstractPlaylistReader.class);
    public LoggerCtrl tableCtrl = new LoggerCtrl(AbstractPlaylistTable.class);
    public LoggerCtrl writerCtrl = new LoggerCtrl(AbstractPlaylistWriter.class);
    public LoggerCtrl playlistIOCtrl = new LoggerCtrl(PlaylistFileIO.class);
    
    @Override
    public void setAllLevel(String lvl) {
        selfCtrl.setLevel(lvl);
        readerCtrl.setLevel(lvl);
        tableCtrl.setLevel(lvl);
        writerCtrl.setLevel(lvl);
        playlistIOCtrl.setLevel(lvl);
    }
}
