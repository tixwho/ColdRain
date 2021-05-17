package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.ArtistBo;
import com.coldrain.database.dao.ArtistDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ArtistBo")
public class ArtistBoImpl implements ArtistBo {

    Logger logger = LoggerFactory.getLogger(ArtistBoImpl.class);
    ArtistDao artistDao;

    @Override
    public ArtistModel createArtistModel(String artist) {
        ArtistModel artistM = new ArtistModel(artist);
        //default: not jacked unless specified
        artistM.setRoot_status(true);
        //default: not multi-artist unless specified
        artistM.setMulti_status(false);
        artistDao.save(artistM);
        return artistM;
    }
    @Override
    public ArtistModel createArtistModel_track(MetaSong metaSong) {
        return createArtistModel(metaSong.getArtist());
    }

    @Override
    public ArtistModel createArtistModel_album(MetaSong metaSong) {
        return createArtistModel(metaSong.getAlbumArtist());
    }

    @Override
    public ArtistModel guaranteeArtistModel_track(MetaSong metaSong) {
        ArtistModel artistM = artistDao.findByArtist(metaSong.getArtist());
        if (artistM == null){
            artistM = createArtistModel_track(metaSong);
        }
        return artistM;
    }

    @Override
    public ArtistModel guaranteeArtistModel_album(MetaSong metaSong) {
        ArtistModel artistM = artistDao.findByArtist(metaSong.getAlbumArtist());
        if (artistM == null){
            artistM = createArtistModel_album(metaSong);
        }
        return artistM;
    }

    @Override
    public ArtistModel registerSongMtoArtistM(ArtistModel artistM, SongModel songM) {
        artistM.getSongModels().add(songM);
        artistDao.update(artistM);
        return artistM;
    }

    @Override
    public ArtistModel registerAlbumMtoArtistM(ArtistModel artistM, AlbumModel albumM) {
        artistM.getAlbumModels().add(albumM);
        artistDao.update(artistM);
        return artistM;
    }

    @Override
    public void connectSingleAndMultiArtistM(ArtistModel singleArtistM,
        ArtistModel multiArtistM) {
        multiArtistM.setMulti_status(true);
        multiArtistM.getRelatedSingleArtists().add(singleArtistM);
        singleArtistM.getRelatedMultiArtists().add(multiArtistM);
        artistDao.update(multiArtistM);
        artistDao.update(singleArtistM);
    }

    @Override
    public void disconnectSingleAndMultiArtistM(ArtistModel singleArtistM,
        ArtistModel multiArtistM) {
        multiArtistM.getRelatedSingleArtists().remove(singleArtistM);
        singleArtistM.getRelatedMultiArtists().remove(multiArtistM);
        artistDao.update(multiArtistM);
        artistDao.update(singleArtistM);
    }

    @Override
    public void registerArtistJacket(ArtistModel rootArtistM, ArtistModel jacketArtistM) {
        jacketArtistM.setRoot_status(false);
        jacketArtistM.setRootArtistM(rootArtistM);
        rootArtistM.getJacketArtists().add(jacketArtistM);
        artistDao.update(jacketArtistM);
        artistDao.update(rootArtistM);
    }

    @Override
    public void abandonArtistJacket(ArtistModel jacketArtistM) {
        ArtistModel rootArtistM = jacketArtistM.getRootArtistM();
        rootArtistM.getJacketArtists().remove(jacketArtistM);
        jacketArtistM.setRootArtistM(null);
        artistDao.update(jacketArtistM);
        artistDao.update(rootArtistM);
    }


    @Override
    public void save(ArtistModel artistM) {
        artistDao.save(artistM);
    }

    @Override
    public void update(ArtistModel artistM) {
        artistDao.update(artistM);
    }

    @Override
    public void delete(ArtistModel artistM) {
        artistDao.delete(artistM);
    }

    @Override
    public ArtistModel findByArtist(String artist) {
        return artistDao.findByArtist(artist);
    }

    @Override
    public List<ArtistModel> findUnusedArtistMs() {
        return artistDao.findUnusedArtistMs();
    }

    @Autowired
    public void setArtistDao(@Qualifier("ArtistDao") ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
}
