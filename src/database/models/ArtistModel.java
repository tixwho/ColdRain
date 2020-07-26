package database.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import database.generic.DatabasePOJO;
import database.utils.InitSessionFactory;
import local.generic.MetaSong;

@Entity
@Table(name="Artist")
@SequenceGenerator(name = "artist_seq", sequenceName = "artist_id_seq")
public class ArtistModel extends DatabasePOJO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1634806897634166804L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="artist_seq")
    private Integer artistid;
    
    @OneToMany(mappedBy="artistM")
    private Set<MetaModel> metaModels = new HashSet<MetaModel>();
    
    private String artist;
    
    public ArtistModel() {
        
    }
    
    public ArtistModel(MetaSong meta) {
        this.artist = meta.getArtist();
    }
    

    public Integer getArtistid() {
        return artistid;
    }

    public void setArtistid(Integer artistid) {
        this.artistid = artistid;
    }

    public Set<MetaModel> getMetaModels() {
        return metaModels;
    }

    public void setMetaModels(Set<MetaModel> metaModels) {
        this.metaModels = metaModels;
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
        System.out.println("Created File");
        return artist;
    }
    
    public static ArtistModel guaranteeArtistModel(MetaSong meta) {
        ArtistModel returnArtistM;
        String artist = meta.getArtist();
        Session session=InitSessionFactory.getNewSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<ArtistModel> q= (Query<ArtistModel>) session.createQuery("from ArtistModel a where a.artist=?0");
        q.setParameter(0, artist);
        ArtistModel toCheckArtistM = q.uniqueResult();
        session.close();
        if (toCheckArtistM == null){
            returnArtistM=createArtistModel(meta);
        }else {
            returnArtistM = toCheckArtistM;
        }
        return returnArtistM;
    }
    
    

}
