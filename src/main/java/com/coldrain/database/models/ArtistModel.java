package com.coldrain.database.models;

import com.coldrain.database.generic.DatabasePOJO;
import com.coldrain.database.utils.InitSessionFactory;
import com.coldrain.playlist.generic.MetaSong;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.query.Query;

@Entity
@Table(name = "Artist", indexes = {@Index(name = "index_artist",columnList = "artist ASC")})
@SequenceGenerator(name = "artist_seq", sequenceName = "artist_id_seq", initialValue = 1,
    allocationSize = 1)
public class ArtistModel extends DatabasePOJO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1634806897634166804L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artist_seq")
    private Integer artistid;

    @OneToMany(mappedBy = "albumArtistM", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<AlbumModel> albumModels = new HashSet<AlbumModel>();

    @OneToMany(mappedBy = "artistM", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private Set<SongModel> songModels = new HashSet<SongModel>();

    private String artist;

    public ArtistModel() {

    }

    public ArtistModel(MetaSong meta) {
        this.artist = meta.getArtist();
    }

    public ArtistModel(String artistName) {this.artist = artistName;}


    public Integer getArtistid() {
        return artistid;
    }

    public void setArtistid(Integer artistid) {
        this.artistid = artistid;
    }



    public Set<AlbumModel> getAlbumModels() {
        return albumModels;
    }

    public void setAlbumModels(Set<AlbumModel> albumModels) {
        this.albumModels = albumModels;
    }

    public Set<SongModel> getSongModels() {
        return songModels;
    }

    public void setSongModels(Set<SongModel> songModels) {
        this.songModels = songModels;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public static ArtistModel createArtistModel(MetaSong meta) {
        ArtistModel artist = new ArtistModel(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(artist);
        tx.commit();
        session.close();
        logger.debug("Created an artist!");
        return artist;
    }

    public static ArtistModel createArtistModel(String artistName) {

        ArtistModel artist = new ArtistModel();
        artist.setArtist(artistName);
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(artist);
        tx.commit();
        session.close();
        logger.debug("Created an artist!");
        return artist;
    }

    public static ArtistModel guaranteeArtistModel(MetaSong meta) {

        ArtistModel returnArtistM;
        String artist = meta.getArtist();
        logger.debug("Checking artist:" + artist);
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // query: artist @ artistModel
        Query<ArtistModel> q = session
            .createQuery("from ArtistModel a where a.artist=?1", ArtistModel.class);
        q.setParameter(1, artist);
        ArtistModel toCheckArtistM = q.uniqueResult();
        session.close();
        if (toCheckArtistM == null) {
            logger.debug("Artist NOT FOUND");
            returnArtistM = createArtistModel(meta);
        } else {
            logger.debug("Artist FOUND");
            returnArtistM = toCheckArtistM;
        }
        return returnArtistM;
    }

    public static ArtistModel guaranteeArtistModel_album(MetaSong meta) {
        ArtistModel returnArtistM;
        String artist = meta.getAlbumArtist();
        logger.debug("Checking album artist:" + artist);
        Session session = InitSessionFactory.getNewSession();
        session.beginTransaction();
        // query artist @ artistModel
        Query<ArtistModel> q = session
            .createQuery("from ArtistModel a where a.artist=?1", ArtistModel.class);
        q.setParameter(1, artist);
        ArtistModel toCheckArtistM = q.uniqueResult();
        session.close();
        if (toCheckArtistM == null) {
            logger.debug("Album Artist NOT FOUND");
            returnArtistM = createArtistModel(artist);
        } else {
            returnArtistM = toCheckArtistM;
            logger.debug("Album Artist FOUND");
        }
        return returnArtistM;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((artist == null) ? 0 : artist.hashCode());
        result = prime * result + ((artistid == null) ? 0 : artistid.hashCode());
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
        ArtistModel other = (ArtistModel) obj;
        if (artist == null) {
            if (other.artist != null)
                return false;
        } else if (!artist.equals(other.artist))
            return false;
        if (artistid == null) {
            return other.artistid == null;
        } else return artistid.equals(other.artistid);
    }

    @Override
    public String toString() {
        return "ArtistModel [artistid=" + artistid + ", artist=" + artist + "]";
    }



}
