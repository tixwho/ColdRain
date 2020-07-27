package database.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.Hibernate;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import database.generic.DatabasePOJO;
import database.utils.InitSessionFactory;
import local.generic.MetaSong;


@Entity
@Table(name="Meta")
@SequenceGenerator(name = "meta_seq", sequenceName = "meta_id_seq")
public class MetaModel extends DatabasePOJO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7680624167194156409L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="meta_seq")
    private Integer metaid;
    
    @OneToMany(mappedBy="metaM")
    private Set<FileModel> fileModels=new HashSet<FileModel>();
    
    @ManyToOne
    @JoinColumn(name = "artistid")
    private ArtistModel artistM;
    
    @ManyToOne
    @JoinColumn(name = "albumid")
    private AlbumModel albumM;
    
    @ManyToOne
    @JoinColumn(name = "songid")
    private SongModel songM;
    
    
    String trackTitle;
    String trackNo;
    String discNo;
    
    
    public MetaModel() {
        
    }
    public MetaModel(MetaSong meta) {
        this.trackTitle=meta.getTrackTitle();
        this.trackNo=meta.getTrackNo();
        this.discNo=meta.getDiscNo();
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
    public String getTrackTitle() {
        return trackTitle;
    }
    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
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
    
    
    public ArtistModel getArtistM() {
        return artistM;
    }
    public void setArtistM(ArtistModel artistM) {
        this.artistM = artistM;
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
    public static MetaModel createMetaModel(MetaSong meta) {
        MetaModel metaM = new MetaModel(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(metaM);
        tx.commit();
        session.close();
        System.out.println("Created Meta");
        return metaM;
    }
    
    public void attachArtistModel(ArtistModel artistModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(artistModel);
        this.setArtistM(artistModel);
        logger.trace("Artist Model binded to MetaModel!");
        artistModel.getMetaModels().add(this);
        logger.trace("Meta Model binded to ArtistModel!");
        session.saveOrUpdate(artistModel);
        logger.trace("ArtistModel updated!");
        session.saveOrUpdate(this);
        logger.trace("MetaModel updated!");
        tx.commit();
        logger.debug("All change commited!");
        session.close();
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
        logger.debug("All change commited!");
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
        logger.debug("All change commited!");
        session.close();
    }
    
    
    

}
