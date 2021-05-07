package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.SongBo;
import com.coldrain.database.dao.SongDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("SongBo")
@Transactional(transactionManager = "crTxManager")
public class SongBoImpl implements SongBo {
    
    SongDao songDao;

    @Autowired
    public void setSongDao(@Qualifier("SongDao") SongDao songDao) {
        this.songDao = songDao;
    }

    @Override
    public SongModel guaranteeSongModel(MetaSong metaSong) {
        return null;
    }

    @Override
    public SongModel createSongModel(MetaSong metaSong) {
        return null;
    }

    @Override
    public SongModel attachArtistMtoSongM(SongModel songM, ArtistModel artistM) {
        songM.setArtistM(artistM);
        songDao.update(songM);
        return songM;
    }

    @Override
    public SongModel registerMetaMtoSongM(SongModel songM, MetaModel metaM) {
        songM.getMetaModels().add(metaM);
        songDao.update(songM);
        return songM;
    }

    @Override
    public void save(SongModel songM) {
        songDao.save(songM);
    }

    @Override
    public void update(SongModel songM) {
        songDao.update(songM);
    }

    @Override
    public void delete(SongModel songM) {
        songDao.delete(songM);
    }

    @Override
    public SongModel findByTitleAndArtistM(String title, ArtistModel artistM) {
        return songDao.findByTitleAndArtistM(title,artistM);
    }
}
