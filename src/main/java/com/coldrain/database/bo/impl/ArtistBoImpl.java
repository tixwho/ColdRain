package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.ArtistBo;
import com.coldrain.database.dao.ArtistDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("ArtistBo")
@Transactional(transactionManager = "crTxManager")
public class ArtistBoImpl implements ArtistBo {

    ArtistDao artistDao;

    @Override
    public ArtistModel createArtistModel(String artist) {
        return null;
    }

    @Override
    public ArtistModel createArtistModel(MetaSong metaSong) {
        return null;
    }

    @Override
    public ArtistModel guaranteeArtistModel_track(MetaSong metaSong) {
        return null;
    }

    @Override
    public ArtistModel guaranteeArtistModel_album(MetaSong metaSong) {
        return null;
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

    @Autowired
    public void setArtistDao(@Qualifier("ArtistDao") ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
}
