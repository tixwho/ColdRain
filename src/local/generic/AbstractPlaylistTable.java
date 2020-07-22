package local.generic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EnumMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import exception.ErrorCodes;
import exception.MetaIOException;
import exception.NativeReflectionException;
import exception.PlaylistIOException;

public abstract class AbstractPlaylistTable {



    protected SupportedMeta[] suppMeta;
    protected Class<?> correspondingSongClass;
    // use enumMap to identify if everything in new table is contained in old
    // if it is, directly use table info. if not, cosntruct metasong and retrieve.

    /**
     * ArrayList contains PlaylistSong instances
     */

    protected ArrayList<AbstractPlaylistSong> songArrList = new ArrayList<AbstractPlaylistSong>();

    /**
     * Universal logger for playlistTable.
     */
    public static Logger logger = LoggerFactory.getLogger(AbstractPlaylistTable.class);

    /* constructor */
    public AbstractPlaylistTable() {
        initializeMeta();
        initializeSongInstance();
    }

    public AbstractPlaylistTable(ArrayList<AbstractPlaylistSong> songArrList) throws PlaylistIOException {
        this();
        if (!songArrList.isEmpty()) {
            if(!this.correspondingSongClass.isInstance(songArrList.get(0))){
                throw new PlaylistIOException("Wrong Song Instance!",ErrorCodes.BASE_IO_ERROR);
            }
        }
        this.songArrList=songArrList;
    }

    public AbstractPlaylistTable(AbstractPlaylistTable unknownTable)
        throws NativeReflectionException, MetaIOException {
        this();
        if (!this.correspondingSongClass.equals(unknownTable.getCorrespondingSongClass())) {
            setDesiredSongArrList(this.correspondingSongClass, this.suppMeta,
                unknownTable.getSupportedMeta(), unknownTable.getSongArrList());
        }
    }

    /**
     * A Universal Table Converter for different tables
     * @param clazz class instance of playlistSong TO SET
     * @param localMeta supported meta info in local table
     * @param foreignMeta supported meta info in foreign table
     * @param foreignArrList  foreign arrList to set
     * @throws NativeReflectionException
     * @throws MetaIOException
     */
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

    protected abstract void initializeMeta();

    protected abstract void initializeSongInstance();

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

    public Class<?> getCorrespondingSongClass() {
        return correspondingSongClass;
    }

    public void setCorrespondingSongClass(Class<?> correspondingSongClass) {
        this.correspondingSongClass = correspondingSongClass;
    }

}
