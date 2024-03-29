package com.coldrain.database.models;

import com.coldrain.database.generic.DatabasePOJO;
import com.coldrain.database.utils.DbHelper;
import com.coldrain.database.utils.InitSessionFactory;
import com.coldrain.exception.DatabaseException;
import com.coldrain.exception.ErrorCodes;
import com.coldrain.playlist.generic.AbstractPlaylistTable;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "Playlist", indexes = {@Index(name = "index_playlistFileSrc",columnList = "src ASC")})
@SequenceGenerator(name = "playlist_seq", sequenceName = "playlist_id_seq", initialValue = 1,
    allocationSize = 1)
public class PlaylistModel extends DatabasePOJO implements Serializable {


    public PlaylistModel() {

    }

    public PlaylistModel(AbstractPlaylistTable unknownTable) {
        this.src = unknownTable.getPlaylistSrc();
        this.lastModified = DbHelper.calcLastModTimestamp(this.src);
    }

    /**
     * 
     */
    private static final long serialVersionUID = -9079949573223480359L;


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_seq")
    private Integer playlistid;


    @OneToMany(mappedBy = "playlistM")
    private Set<PlaylistRecordModel> playlistRecordModels = new HashSet<PlaylistRecordModel>();

    private PlaylistInfoComp playlistInfoC;

    private String src;
    private Long lastModified;


    public Integer getPlaylistid() {
        return playlistid;
    }

    public void setPlaylistid(Integer playlistid) {
        this.playlistid = playlistid;
    }

    public Set<PlaylistRecordModel> getPlaylistRecordModels() {
        return playlistRecordModels;
    }

    public void setPlaylistRecordModels(Set<PlaylistRecordModel> playlistRecordModels) {
        this.playlistRecordModels = playlistRecordModels;
    }

    public PlaylistInfoComp getPlaylistInfoC() {
        return playlistInfoC;
    }

    public void setPlaylistInfoC(PlaylistInfoComp playlistInfoC) {
        this.playlistInfoC = playlistInfoC;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

    public static PlaylistModel createPlaylistModel(AbstractPlaylistTable unknownTable) {
        PlaylistModel playlistM = new PlaylistModel(unknownTable);
        PlaylistInfoComp playlistC = new PlaylistInfoComp(unknownTable);
        playlistM.setPlaylistInfoC(playlistC);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(playlistM);
        tx.commit();
        session.close();
        System.out.println("Created PlaylistModel");
        return playlistM;
    }


    public static PlaylistModel guaranteePlaylistModel(AbstractPlaylistTable unknownTable) {
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // Query: src @ playlistModel
        Query<PlaylistModel> q = session
            .createQuery("from PlaylistModel p where p.src=?1", PlaylistModel.class);
        q.setParameter(1, unknownTable.getPlaylistSrc());
        PlaylistModel toCheckPlaylistM;
        try {
            toCheckPlaylistM = q.uniqueResult();
        } catch (NonUniqueResultException nure) {
            Iterator<PlaylistModel> it = q.list().iterator();
            while (it.hasNext()) {
                PlaylistModel theModel = it.next();
                logger.error("Duplicate Model:" + theModel.toString());
            }
            throw nure;
        }
        session.close();
        if (toCheckPlaylistM == null) {
            logger.debug("Playlist NOT FOUND");
            toCheckPlaylistM = createPlaylistModel(unknownTable);
        } else {
            logger.debug("Playlist FOUND");
        }
        return toCheckPlaylistM;
    }

    public static PlaylistModel findPlaylistModel(String src) throws DatabaseException {
        PlaylistModel returnPlaylistM;
        // just need to guarantee SongModel, AlbumModel and title,
        // since SongModel includes ArtistModel, AlbumModel includes AlbumArtist
        logger.debug("Finding PlaylistModel for " + src);
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // Query: src @ playlistModel
        Query<PlaylistModel> q = session
            .createQuery("from PlaylistModel p where p.src=?1", PlaylistModel.class);
        q.setParameter(1, src);
        returnPlaylistM = q.uniqueResult();
        session.close();
        if (returnPlaylistM == null) {
            logger.debug("Playlist NOT FOUND");
            throw new DatabaseException("Does not found corresponding PlaylistModel",
                ErrorCodes.DATABASE_NOT_FOUND);
        } else {
            logger.debug("Playlist FOUND");
        }
        return returnPlaylistM;
    }


    public void updateTimeStamp() {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.refresh(this);
        this.lastModified = DbHelper.calcLastModTimestamp(this.getSrc());

        session.saveOrUpdate(this);
        logger.trace("PlaylistModel timestamp updated!");
        tx.commit();
        logger.debug("All change commited! Timestamp updated.");
        session.close();

    }



}
