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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import database.generic.DatabasePOJO;
import database.utils.InitSessionFactory;
import local.generic.MetaSong;

@Entity
@Table(name = "Song")
@SequenceGenerator(name = "song_seq", sequenceName = "song_id_seq")
public class SongModel extends DatabasePOJO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6385688097272219913L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_seq")
    private Integer songid;
    
    @OneToMany(mappedBy = "songM")
    private Set<MetaModel> metaModels = new HashSet<MetaModel>();
    
    @ManyToOne
    @JoinColumn(name =  "artistid")
    private ArtistModel artistM;
    
    private String trackTitle;
    
    public SongModel() {
        
    }
    
    public SongModel(MetaSong meta) {
        this.trackTitle=meta.getTrackTitle();
    }

    public Integer getSongid() {
        return songid;
    }

    public void setSongid(Integer songid) {
        this.songid = songid;
    }

    public Set<MetaModel> getMetaModels() {
        return metaModels;
    }

    public void setMetaModels(Set<MetaModel> metaModels) {
        this.metaModels = metaModels;
    }

    public ArtistModel getArtistM() {
        return artistM;
    }

    public void setArtistM(ArtistModel artistM) {
        this.artistM = artistM;
    }
    
    
    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }
    
    public void attachArtistModel(ArtistModel artistModel) {
        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Refreshing...");
        session.refresh(this);
        session.refresh(artistModel);
        this.setArtistM(artistModel);
        logger.trace("Artist Model binded to SongModel!");
        artistModel.getSongModels().add(this);
        logger.trace("SongModel binded to ArtistModel!");
        session.saveOrUpdate(artistModel);
        logger.trace("ArtistModel updated!");
        session.saveOrUpdate(this);
        logger.trace("SongModel updated!");
        tx.commit();
        logger.debug("All change commited! Attached ArtistModel to SongModel.");
        session.close();
    }
    
    public static SongModel createSongModel(MetaSong meta) {
        SongModel album = new SongModel(meta);

        Session session = InitSessionFactory.getNewSession();
        Transaction tx = session.beginTransaction();
        session.save(album);
        tx.commit();
        session.close();
        logger.debug("Created a SongModel!");
        return album;
    }

    public static SongModel guaranteeSongModel(MetaSong meta) {

        SongModel returnSongM;
        String trackTitle = meta.getTrackTitle();
        ArtistModel toCheckArtistM = ArtistModel.guaranteeArtistModel(meta);
        logger.debug("Checking title:"+trackTitle +" by "+toCheckArtistM.getArtist());
        Session session=InitSessionFactory.getNewSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        Query<SongModel> q= (Query<SongModel>) session.createQuery("from SongModel s where s.trackTitle=?0 and s.artistM=?1");
        q.setParameter(0, trackTitle);
        q.setParameter(1, toCheckArtistM);
        SongModel toCheckSongM = q.uniqueResult();
        session.close();
        if (toCheckSongM == null){
            logger.debug("Song NOT FOUND");
            returnSongM=createSongModel(meta);
            returnSongM.attachArtistModel(toCheckArtistM);
        }else {
            logger.debug("Song FOUND");
            returnSongM = toCheckSongM;
        }
        return returnSongM;
    }

}
