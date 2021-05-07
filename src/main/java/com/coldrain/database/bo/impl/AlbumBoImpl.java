package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.AlbumBo;
import com.coldrain.database.dao.AlbumDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.playlist.generic.MetaSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AlbumBo")
@Transactional(transactionManager = "crTxManager")
public class AlbumBoImpl implements AlbumBo {
    
    AlbumDao albumDao;

    @Autowired
    public void setAlbumDao(@Qualifier("AlbumDao") AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Override
    public AlbumModel createAlbumModel(MetaSong metaSong) {
        return null;
    }

    @Override
    public AlbumModel guaranteeAlbumModel(MetaSong metaSong) {
        return null;
    }

    @Override
    public AlbumModel registerMetaMtoAlbumM(AlbumModel albumM, MetaModel metaM) {
        albumM.getMetaModels().add(metaM);
        albumDao.update(albumM);
        return albumM;
    }

    @Override
    public AlbumModel attachArtistMtoAlbumM(AlbumModel albumM, ArtistModel artistM) {
        albumM.setAlbumArtistM(artistM);
        albumDao.update(albumM);
        return albumM;
    }

    @Override
    public void save(AlbumModel albumM) {
        albumDao.save(albumM);
    }

    @Override
    public void update(AlbumModel albumM) {
        albumDao.update(albumM);
    }

    @Override
    public void delete(AlbumModel albumM) {
        albumDao.delete(albumM);
    }

    @Override
    public AlbumModel findByAlbumAndArtistM(String album, ArtistModel artistM) {
        return albumDao.findByAlbumAndArtistM(album,artistM);
    }
}
