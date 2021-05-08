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

@Service("ArtistBo")
public class ArtistBoImpl implements ArtistBo {

    ArtistDao artistDao;

    @Override
    public ArtistModel createArtistModel(String artist) {
        ArtistModel artistM = new ArtistModel(artist);
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
            artistM = createArtistModel_track(metaSong);
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
