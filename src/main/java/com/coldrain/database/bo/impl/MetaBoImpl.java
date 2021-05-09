package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.MetaBo;
import com.coldrain.database.dao.MetaDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("MetaBo")
public class MetaBoImpl implements MetaBo {

    @Autowired
    public void setMetaDao(@Qualifier("MetaDao") MetaDao metaDao) {
        this.metaDao = metaDao;
    }

    MetaDao metaDao;

    @Override
    public void save(MetaModel metaM) {
        metaDao.save(metaM);
    }

    @Override
    public void update(MetaModel metaM) {
        metaDao.update(metaM);
    }

    @Override
    public void delete(MetaModel metaM) {
        metaDao.delete(metaM);
    }



    @Override
    public MetaModel findByAlbumMandSongM(AlbumModel albumM, SongModel songM) {
        return metaDao.findByAlbumMandSongM(albumM,songM);
    }

    @Override
    public int checkMetaCount(AlbumModel albumM, SongModel songM) {
        return metaDao.checkMetaCount(albumM,songM);
    }

    @Override
    public boolean checkAlbumExistenceInMeta(AlbumModel albumM) {
        return metaDao.checkAlbumExistenceInMeta(albumM);
    }

    @Override
    public boolean checkSongExistenceInMeta(SongModel songM) {
        return metaDao.checkSongExistenceInMeta(songM);
    }

    @Override
    public List<MetaModel> findUnusedMetaM() {
        return metaDao.findUnusedMetaM();
    }

    @Override
    public int checkMetaCount(MetaSong metaSong) {
        //TODO fill in after complete albumM and songM
        return 0;
    }

    @Override
    public MetaModel guaranteeMetaModel(MetaSong metaSong, AlbumModel albumM, SongModel songM) {
        MetaModel metaM = findByAlbumMandSongM(albumM,songM);
        if(metaM == null){
            metaM = createMetaModel(metaSong);
            attachAlbumMtoMetaM(metaM,albumM);
            attachSongMtoMetaM(metaM,songM);
        }
        return metaM;
    }

    @Override
    public MetaModel createMetaModel(MetaSong metaSong) {
        MetaModel metaM = new MetaModel(metaSong);
        metaDao.save(metaM);
        return metaM;
    }

    @Override
    public MetaModel registerFileMtoMetaM(MetaModel metaM, FileModel fileM) {
        metaM.getFileModels().add(fileM);
        metaDao.update(metaM);
        return metaM;
    }

    @Override
    public MetaModel attachAlbumMtoMetaM(MetaModel metaM, AlbumModel albumM) {
        metaM.setAlbumM(albumM);
        metaDao.update(metaM);
        return metaM;
    }

    @Override
    public MetaModel attachSongMtoMetaM(MetaModel metaM, SongModel songM) {
        metaM.setSongM(songM);
        metaDao.update(metaM);
        return metaM;
    }
}
