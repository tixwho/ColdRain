package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.SongBo;
import com.coldrain.database.dao.SongDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("SongBo")
public class SongBoImpl implements SongBo {
    
    SongDao songDao;


    @Autowired
    public void setSongDao(@Qualifier("SongDao") SongDao songDao) {
        this.songDao = songDao;
    }


    @Override
    public SongModel guaranteeSongModel(String title, ArtistModel artistM) {
        SongModel songM = songDao.findByTitleAndArtistM(title,artistM);
        if(songM==null){
            songM = createSongModel(title);
            //所有attach操作均在Bo完成 避免hashcode出现问题
            attachArtistMtoSongM(songM, artistM);
        }
        return songM;
    }

    @Override
    public SongModel createSongModel(MetaSong metaSong) {
        SongModel songM= new SongModel(metaSong);
        songDao.save(songM);
        return songM;
    }

    public SongModel createSongModel(String trackTitle){
        SongModel songM = new SongModel(trackTitle);
        songDao.save(songM);
        return songM;
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

    @Override
    public boolean checkArtistExistenceInSong(ArtistModel artistM) {
        return songDao.checkArtistExistenceInSong(artistM);
    }

    @Override
    public List<SongModel> findUnusedSongMs() {
        return songDao.findUnusedSongMs();
    }
}
