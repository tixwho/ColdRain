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
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import database.generic.DatabasePOJO;
import database.utils.InitSessionFactory;
import local.generic.MetaSong;

@Entity
@Table(name = "Album")
@SequenceGenerator(name = "album_seq", sequenceName = "album_id_seq")
public class AlbumModel extends DatabasePOJO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5215786126214247085L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "album_seq")
    private Integer albumid;

    @ManyToOne
    @JoinColumn(name = "albumArtistid")
    private ArtistModel albumArtistM;

    @OneToMany(mappedBy = "albumM")
    private Set<MetaModel> metaModels = new HashSet<MetaModel>();

    private String album;

    private String totalDiscNo;

    private String albumDate;

    public AlbumModel() {

    }

    public AlbumModel(MetaSong meta) {
        this.album = meta.getAlbum();
        this.totalDiscNo = meta.getTotalDiscNo();
        this.albumDate = meta.getAlbumDate();
    }



    public Integer getAlbumid() {
        return albumid;
    }

    public void setAlbumid(Integer albumid) {
        this.albumid = albumid;
    }

    public Set<MetaModel> getMetaModels() {
        return metaModels;
    }

    public void setMetaModels(Set<MetaModel> metaModels) {
        this.metaModels = metaModels;
    }

    public ArtistModel getAlbumArtistM() {
        return albumArtistM;
    }

    public void setAlbumArtistM(ArtistModel albumArtistM) {
        this.albumArtistM = albumArtistM;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTotalDiscNo() {
        return totalDiscNo;
    }

    public void setTotalDiscNo(String totalDiscNo) {
        this.totalDiscNo = totalDiscNo;
    }

    public String getAlbumDate() {
        return albumDate;
    }

    public void setAlbumDate(String albumDate) {
        this.albumDate = albumDate;
    }
    
    public static AlbumModel createAlbumModel(MetaSong meta) {
        AlbumModel album = new AlbumModel(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(album);
        tx.commit();
        session.close();
        logger.debug("Created an artist!");
        return album;
    }
    
    
    public static AlbumModel guaranteeAlbumModel(MetaSong meta) {

        AlbumModel returnAlbumM;
        String albumName = meta.getAlbum();
        ArtistModel toCheckArtistM = ArtistModel.guaranteeArtistModel_album(meta);
        logger.debug("Checking album:"+albumName +" by "+toCheckArtistM.getArtist());
        Session session=InitSessionFactory.getNewSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<AlbumModel> q= (Query<AlbumModel>) session.createQuery("from AlbumModel a where a.album=?0 and a.albumArtistM=?1");
        q.setParameter(0, albumName);
        q.setParameter(1, toCheckArtistM);
        AlbumModel toCheckAlbumM = q.uniqueResult();
        session.close();
        if (toCheckAlbumM == null){
            logger.debug("Album NOT FOUND");
            returnAlbumM=createAlbumModel(meta);
            returnAlbumM.attachArtistModel_album(toCheckArtistM);
        }else {
            logger.debug("Album FOUND");
            returnAlbumM = toCheckAlbumM;
        }
        return returnAlbumM;
    }

    // Archieved for future use: used when multiple collection using same object expected
    public void attachArtistModel_album(ArtistModel artistModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(artistModel);
        logger.info("Artist Model (album) to Attach:" + artistModel.getArtist());
        this.setAlbumArtistM(artistModel);
        logger.trace("Artist Model (album) binded to AlbumModel!");
        artistModel.getAlbumModels().add(this);
        logger.trace("AlbumModel binded to ArtistModel (album)!");
        try {
            session.saveOrUpdate(artistModel);
            logger.trace("ArtistModel (album) updated!");
        } catch (NonUniqueObjectException nuoe) {
            logger
                .debug("Use merge instead of save to handle objects already cached in ArtistModel");
            session.merge(artistModel);
            logger.trace("ArtistModel (album) merged!");
        }
        session.saveOrUpdate(this);
        logger.trace("MetaModel updated!");
        tx.commit();
        logger.debug("All change commited!");
        session.close();

    }



}
