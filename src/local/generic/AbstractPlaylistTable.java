package local.generic;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import exception.ErrorCodes;
import exception.MetaIOException;
import exception.NativeReflectionException;

public abstract class AbstractPlaylistTable {



    protected SupportedMeta[] suppMeta;
    // use enumMap to identify if everything in new table is contained in old
    // if it is, directly use table info. if not, cosntruct metasong and retrieve.

    /**
     * ArrayList contains PlaylistSong instances
     */

    protected ArrayList<AbstractPlaylistSong> songArrList = new ArrayList<AbstractPlaylistSong>();

    /* constructor */
    public AbstractPlaylistTable() {

    }

    public AbstractPlaylistTable(ArrayList<AbstractPlaylistSong> songArrList,
        SupportedMeta[] suppMeta) {
        this.songArrList = songArrList;
        this.suppMeta = suppMeta;
    }

    protected void setDesiredSongArrList(Class<?> clazz, SupportedMeta[] localMeta,
        SupportedMeta[] foreignMeta, ArrayList<AbstractPlaylistSong> foreignArrList)
        throws NativeReflectionException, MetaIOException {
        try {
            EnumMap<SupportedMeta, Boolean> compMap = SupportedMeta.getInfoMap(foreignMeta);
            boolean metaSupportedFlag = true;
            for (SupportedMeta eachMeta : localMeta) {
                if (!compMap.get(eachMeta)) {
                    metaSupportedFlag = false;
                    break;
                }
            }
            ArrayList<AbstractPlaylistSong> replacingArr = new ArrayList<AbstractPlaylistSong>();
            // iterate through each song
            for (AbstractPlaylistSong aForeignSong : foreignArrList) {
                AbstractPlaylistSong newSong = (AbstractPlaylistSong) clazz.newInstance();
                // if supported, use reflect to get desired instance
                if (metaSupportedFlag) {

                    for (SupportedMeta aMeta : localMeta) {
                        Method getMethod = SupportedMeta.getGetter(aForeignSong, aMeta);
                        Object meta = getMethod.invoke(aForeignSong);
                        Method setMethod = SupportedMeta.getSetter(newSong, aMeta);
                        setMethod.invoke(newSong, meta);

                    }

                }
                // if not supported, create a MetaSong for each song
                else {
                    // cover up after finish new MetaSong class.
                    String songSrc = aForeignSong.getSrc();
                    // create a new MetaSong object->Not time efficient but covers all info.
                    MetaSong aMetaSong = new MetaSong(songSrc);
                    for (SupportedMeta aMeta : localMeta) {
                        Method getMethod = SupportedMeta.getGetter(aMetaSong, aMeta);
                        Object meta = getMethod.invoke(aMetaSong);
                        Method setMethod = SupportedMeta.getSetter(newSong, aMeta);
                        setMethod.invoke(newSong, meta);

                    }
                }
                // add new song to array
                replacingArr.add(newSong);
            }
            // set the new Array to default.
            setSongArrList(replacingArr);
        } catch (InstantiationException ise) {
            throw new NativeReflectionException("AbsPlaylistTable Reflection Exception", ise,
                ErrorCodes.INSTANSTIATE_ERROR);
        } catch (IllegalAccessException iace) {
            throw new NativeReflectionException("AbsPlaylistTable Reflection Exception", iace,
                ErrorCodes.ILLEGAL_ACCESS_ERROR);
        } catch (NoSuchMethodException nsme) {
            throw new NativeReflectionException("AbsPlaylistTable Reflection Exception", nsme,
                ErrorCodes.REFLECT_UNKNOWN_METHOD_ERROR);
        } catch (SecurityException se) {
            throw new NativeReflectionException("AbsPlaylistTable Reflection Exception", se,
                ErrorCodes.SECURITY_ERROR);
        } catch (IllegalArgumentException iare) {
            throw new NativeReflectionException("AbsPlaylistTable Reflection Exception", iare,
                ErrorCodes.ILLEGAL_ARGUMENT_ERROR);
        } catch (InvocationTargetException ite) {
            throw new NativeReflectionException("AbsPlaylistTable Reflection Exception", ite,
                ErrorCodes.INVOCATION_TARGET_ERROR);
        }
    }

    public abstract void setInfoFromTable(AbstractPlaylistTable foreignTable)
        throws NativeReflectionException, MetaIOException;

    public SupportedMeta[] getSupportedMeta() {
        return this.suppMeta;
    }

    public void setSupportedMeta(SupportedMeta[] metaArray) {
        suppMeta = metaArray;
    }

    public ArrayList<AbstractPlaylistSong> getSongArrList() {
        return songArrList;
    }

    public void setSongArrList(ArrayList<AbstractPlaylistSong> songArrList) {
        this.songArrList = songArrList;
    }

}
