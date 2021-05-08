package com.coldrain.database.models;

import com.coldrain.database.generic.DatabasePOJO;
import com.coldrain.database.utils.InitSessionFactory;
import com.coldrain.playlist.generic.MetaSong;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.query.Query;


@Entity
@Table(name = "Meta")
@SequenceGenerator(name = "meta_seq", sequenceName = "meta_id_seq", initialValue = 1,
    allocationSize = 1)
public class MetaModel extends DatabasePOJO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7680624167194156409L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meta_seq")
    private Integer metaid;

    @OneToMany(mappedBy = "metaM", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<FileModel> fileModels = new HashSet<FileModel>();


    @ManyToOne
    @JoinColumn(name = "albumid")
    private AlbumModel albumM;

    @ManyToOne
    @JoinColumn(name = "songid")
    private SongModel songM;

    @ManyToOne
    @JoinColumn(name = "actualMetaid")
    private MetaModel actualMetaM;


    String trackNo;
    String discNo;


    public MetaModel() {

    }

    public MetaModel(MetaSong meta) {
        this.trackNo = meta.getTrackNo();
        this.discNo = meta.getDiscNo();
    }


    public Integer getMetaid() {
        return metaid;
    }

    public void setMetaid(Integer metaid) {
        this.metaid = metaid;
    }

    public Set<FileModel> getFileModels() {
        return fileModels;
    }

    public void setFileModels(Set<FileModel> fileModels) {
        this.fileModels = fileModels;
    }

    public String getTrackNo() {
        return trackNo;
    }

    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo;
    }

    public String getDiscNo() {
        return discNo;
    }

    public void setDiscNo(String discNo) {
        this.discNo = discNo;
    }


    public AlbumModel getAlbumM() {
        return albumM;
    }

    public void setAlbumM(AlbumModel albumM) {
        this.albumM = albumM;
    }

    public SongModel getSongM() {
        return songM;
    }

    public void setSongM(SongModel songM) {
        this.songM = songM;
    }


    public MetaModel getActualMetaM() {
        return actualMetaM;
    }

    public void setActualMetaM(MetaModel actualMetaM) {
        this.actualMetaM = actualMetaM;
    }

    public static MetaModel guaranteeMetaModel(MetaSong meta) {
        MetaModel returnMetaM;
        // just need to guarantee SongModel, AlbumModel and title,
        // since SongModel includes ArtistModel, AlbumModel includes AlbumArtist
        AlbumModel toCheckAlbumM = AlbumModel.guaranteeAlbumModel(meta);
        SongModel toCheckSongM = SongModel.guaranteeSongModel(meta);
        logger.debug("Checking album:" + toCheckAlbumM.getAlbum() + " song:"
            + toCheckSongM.getTrackTitle() + " by: " + toCheckSongM.getArtistM().getArtist());
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // Query: albumM, songM @ metaModel
        Query<MetaModel> q = session
            .createQuery("from MetaModel m where m.albumM=?1 and m.songM=?2", MetaModel.class);
        q.setParameter(1, toCheckAlbumM);
        q.setParameter(2, toCheckSongM);
        logger.warn("TEMP:" + toCheckAlbumM + toCheckSongM);
        MetaModel toCheckMetaM;
        try {
            toCheckMetaM = q.uniqueResult();
        } catch (NonUniqueResultException nure) {
            Iterator<MetaModel> it = q.list().iterator();
            while (it.hasNext()) {
                MetaModel theModel = it.next();
                logger.error("Duplicate Model:" + theModel.toString());
            }
            throw nure;
        }
        session.close();
        if (toCheckMetaM == null) {
            logger.debug("Meta NOT FOUND");
            returnMetaM = createMetaModel(meta);
            returnMetaM.attachAlbumModel(toCheckAlbumM);
            returnMetaM.attachSongModel(toCheckSongM);
        } else {
            logger.debug("Meta FOUND");
            returnMetaM = toCheckMetaM;
        }
        return returnMetaM;

    }

    public static int checkMetaCount(MetaSong meta) {
        int metaCount = -1;
        AlbumModel toCheckAlbumM = AlbumModel.guaranteeAlbumModel(meta);
        SongModel toCheckSongM = SongModel.guaranteeSongModel(meta);
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // Query: albumM, songM @ metaModel
        Query<MetaModel> q = session
            .createQuery("from MetaModel m where m.albumM=?1 and m.songM=?2", MetaModel.class);
        q.setParameter(1, toCheckAlbumM);
        q.setParameter(2, toCheckSongM);
        metaCount = q.list().size();
        session.close();
        return metaCount;
    }


    public static MetaModel createMetaModel(MetaSong meta) {
        MetaModel metaM = new MetaModel(meta);
        metaM.setActualMetaM(metaM);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(metaM);
        tx.commit();
        session.close();
        logger.info("New MetaModel Created!");
        return metaM;
    }


    public void attachAlbumModel(AlbumModel albumModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(albumModel);
        this.setAlbumM(albumModel);
        logger.trace("Album Model binded to MetaModel!");
        albumModel.getMetaModels().add(this);
        logger.trace("Meta Model binded to AlbumModel!");
        session.saveOrUpdate(albumModel);
        logger.trace("AlbumModel updated!");
        session.saveOrUpdate(this);
        logger.trace("MetaModel updated!");
        tx.commit();
        logger.debug("All change commited! AlbumModel attached to MetaModel.");
        session.close();
    }

    public void attachSongModel(SongModel songModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(songModel);
        this.setSongM(songModel);
        logger.trace("Song Model binded to MetaModel!");
        songModel.getMetaModels().add(this);
        logger.trace("Meta Model binded to SongModel!");
        session.saveOrUpdate(songModel);
        logger.trace("SongModel updated!");
        session.saveOrUpdate(this);
        logger.trace("MetaModel updated!");
        tx.commit();
        logger.debug("All change commited! SongModel attached to MetaModel.");
        session.close();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((albumM == null) ? 0 : albumM.hashCode());
        result = prime * result + ((discNo == null) ? 0 : discNo.hashCode());
        result = prime * result + ((metaid == null) ? 0 : metaid.hashCode());
        result = prime * result + ((songM == null) ? 0 : songM.hashCode());
        result = prime * result + ((trackNo == null) ? 0 : trackNo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetaModel other = (MetaModel) obj;
        if (albumM == null) {
            if (other.albumM != null)
                return false;
        } else if (!albumM.equals(other.albumM))
            return false;
        if (discNo == null) {
            if (other.discNo != null)
                return false;
        } else if (!discNo.equals(other.discNo))
            return false;
        if (metaid == null) {
            if (other.metaid != null)
                return false;
        } else if (!metaid.equals(other.metaid))
            return false;
        if (songM == null) {
            if (other.songM != null)
                return false;
        } else if (!songM.equals(other.songM))
            return false;
        if (trackNo == null) {
            return other.trackNo == null;
        } else return trackNo.equals(other.trackNo);
    }

    @Override
    public String toString() {
        return "MetaModel [metaid=" + metaid + ", albumM=" + albumM + ", songM=" + songM
            + ", trackNo=" + trackNo + ", discNo=" + discNo + "]";
    }



}
