package old.localModels;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import old.readers.SongReader;
import old.readers.ZplXmlReader;

public class ZplTable {
    /*
     * ZplTable是最上层的constructor，接收一个文件address，返回一个包含了ZplSong的ArrayList
     * 需要先调用ZplXmlReader返回一个<seq>的Element（包含了所有的<media>) 然后由Constructor遍历每一个<media>呈递给ZplMediaReader
     * ZplMediaReader返回一个ZplSong，在ZplTable层面内装入ArrayList
     */

    ArrayList<ZplSong> playList = new ArrayList<ZplSong>();

    public ZplTable(String addr) throws DocumentException, NoSuchMethodException, SecurityException,
        IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ZplXmlReader xmlReader = new ZplXmlReader(addr);
        Element sequence = xmlReader.getSequence();

        List<Element> medias = sequence.elements();
        // 遍历每个<media/>
        for (Element mediaInfo : medias) {
            SongReader singleMediaReader = new SongReader(mediaInfo); //input Element so utilize xml style reading
            ZplSong songInfo = singleMediaReader.getZplSong();
            playList.add(songInfo);
            // 遍历每个media的attribute
        }

    }



    public ArrayList<ZplSong> getSongArraylist() {
        return playList;
    }



}
