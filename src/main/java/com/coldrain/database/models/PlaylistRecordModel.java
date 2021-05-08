package com.coldrain.database.models;

import com.coldrain.database.generic.DatabasePOJO;
import com.coldrain.database.service.AudioDBService_legacy;
import com.coldrain.database.utils.InitSessionFactory;
import com.coldrain.exception.DatabaseException;
import com.coldrain.exception.MetaIOException;
import com.coldrain.playlist.generic.AbstractPlaylistSong;
import com.coldrain.playlist.generic.MetaSong;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PlaylistRecord")
@SequenceGenerator(name = "playlistRecord_seq", sequenceName = "playlistRecord_id_seq",
    initialValue = 1, allocationSize = 1)
public class PlaylistRecordModel extends DatabasePOJO implements Serializable {


    public PlaylistRecordModel() {

    }

    public PlaylistRecordModel(int order, AbstractPlaylistSong unknownSong) {
        this.playlistOrder = order;
        try {
            this.fileM = FileModel.findFileModel(unknownSong.getSrc(),true);
        } catch (DatabaseException de) {
            // song not presented in com.coldrain.database; scan first
            try {
                this.fileM = AudioDBService_legacy.loadNewFile(new MetaSong(unknownSong.getSrc()));
            } catch (MetaIOException me) {
                // should not happen if audioFile is valid
                return;
            }
        }
    }

    /**
     * 
     */
    private static final long serialVersionUID = -4318333861372042906L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlistRecord_seq")
    private Integer pRecordid;

    @ManyToOne
    @JoinColumn(name = "playlistid")
    private PlaylistModel playlistM;

    @ManyToOne
    @JoinColumn(name = "fileid")
    // this is a single-direction relation.
    private FileModel fileM;

    private Integer playlistOrder;


    public Integer getpRecordid() {
        return pRecordid;
    }

    public void setpRecordid(Integer pRecordid) {
        this.pRecordid = pRecordid;
    }

    public PlaylistModel getPlaylistM() {
        return playlistM;
    }

    public void setPlaylistM(PlaylistModel playlistM) {
        this.playlistM = playlistM;
    }

    public FileModel getFileM() {
        return fileM;
    }

    public void setFileM(FileModel fileM) {
        this.fileM = fileM;
    }

    public Integer getPlaylistOrder() {
        return playlistOrder;
    }

    public void setPlaylistOrder(Integer playlistOrder) {
        this.playlistOrder = playlistOrder;
    }

    public void attachPlaylistModel(PlaylistModel playlistModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(playlistModel);
        this.setPlaylistM(playlistModel);
        logger.trace("Playlist Model binded to PlaylistRecordModel!");
        playlistModel.getPlaylistRecordModels().add(this);
        logger.trace("PlaylistRecord Model binded to PlaylistModel!");
        session.saveOrUpdate(playlistModel);
        logger.trace("Playlist Model updated!");
        session.saveOrUpdate(this);
        logger.trace("Playlist Record Model updated!");
        tx.commit();
        logger.debug("All change commited! PlaylistModel attached to PlaylistRecordModel.");
        session.close();
    }

    public static PlaylistRecordModel createPlaylistRecordModel(int order,
        AbstractPlaylistSong unknownSong) {
        PlaylistRecordModel playlistRecordM = new PlaylistRecordModel(order, unknownSong);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(playlistRecordM);
        tx.commit();
        session.close();
        logger.info("New PlaylistRecordModel Created!");
        return playlistRecordM;
    }



}
