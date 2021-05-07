package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.MetaBo;
import com.coldrain.database.dao.MetaDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("MetaBo")
@Transactional(transactionManager = "crTxManager")
public class MetaBoImpl implements MetaBo {

    @Autowired
    public void setMetaDao(MetaDao metaDao) {
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
    public MetaModel guaranteeMetaModel(MetaSong metaSong) {
        //TODO fill in after complete albumM and songM
        return null;
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
    public int checkMetaCount(MetaSong metaSong) {
        //TODO fill in after complete albumM and songM
        return 0;
    }

    @Override
    public MetaModel createMetaModel(MetaSong metaSong) {
        //TODO fill in after complete albumM and songM
        return null;
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
