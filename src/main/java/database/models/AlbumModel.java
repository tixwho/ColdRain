package database.models;

import database.generic.DatabasePOJO;
import database.utils.InitSessionFactory;
import playlist.generic.MetaSong;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Album", indexes = {@Index(name = "index_album",columnList = "album ASC")})
@SequenceGenerator(name = "album_seq", sequenceName = "album_id_seq", initialValue = 1,
    allocationSize = 1)
// todo: add index
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
        logger.debug("Created an album!");
        return album;
    }


    public static AlbumModel guaranteeAlbumModel(MetaSong meta) {

        AlbumModel returnAlbumM;
        String albumName = meta.getAlbum();
        ArtistModel toCheckArtistM = ArtistModel.guaranteeArtistModel_album(meta);
        logger.debug("Checking album:" + albumName + " by " + toCheckArtistM.getArtist());
        Session session = InitSessionFactory.getNewSession();
        Transaction transaction = session.beginTransaction();
        // Query: album,albumArtistM @ album
        Query<AlbumModel> q = session.createQuery(
            "from AlbumModel a where a.album=?1 and a.albumArtistM=?2", AlbumModel.class);
        q.setParameter(1, albumName);
        q.setParameter(2, toCheckArtistM);
        AlbumModel toCheckAlbumM = q.uniqueResult();
        session.close();
        if (toCheckAlbumM == null) {
            logger.debug("Album NOT FOUND");
            returnAlbumM = createAlbumModel(meta);
            returnAlbumM.attachArtistModel_album(toCheckArtistM);
        } else {
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
        logger.trace("AlbumModel updated!");
        tx.commit();
        logger.debug("All change commited! Attached ArtistModel to AlbumModel.");
        session.close();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((album == null) ? 0 : album.hashCode());
        result = prime * result + ((albumArtistM == null) ? 0 : albumArtistM.hashCode());
        result = prime * result + ((albumDate == null) ? 0 : albumDate.hashCode());
        result = prime * result + ((albumid == null) ? 0 : albumid.hashCode());
        result = prime * result + ((totalDiscNo == null) ? 0 : totalDiscNo.hashCode());
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
        AlbumModel other = (AlbumModel) obj;
        if (album == null) {
            if (other.album != null)
                return false;
        } else if (!album.equals(other.album))
            return false;
        if (albumArtistM == null) {
            if (other.albumArtistM != null)
                return false;
        } else if (!albumArtistM.equals(other.albumArtistM))
            return false;
        if (albumDate == null) {
            if (other.albumDate != null)
                return false;
        } else if (!albumDate.equals(other.albumDate))
            return false;
        if (albumid == null) {
            if (other.albumid != null)
                return false;
        } else if (!albumid.equals(other.albumid))
            return false;
        if (totalDiscNo == null) {
            return other.totalDiscNo == null;
        } else return totalDiscNo.equals(other.totalDiscNo);
    }

    @Override
    public String toString() {
        return "AlbumModel [albumid=" + albumid + ", albumArtistM=" + albumArtistM + ", album="
            + album + ", totalDiscNo=" + totalDiscNo + ", albumDate=" + albumDate + "]";
    }



}
