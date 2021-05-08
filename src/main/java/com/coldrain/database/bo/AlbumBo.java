package com.coldrain.database.bo;

import com.coldrain.database.dao.AlbumDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.playlist.generic.MetaSong;

public interface AlbumBo extends AlbumDao {

    //logical operations
    AlbumModel createAlbumModel(MetaSong metaSong);
    AlbumModel guaranteeAlbumModel(MetaSong metaSong, ArtistModel artistM);
    AlbumModel registerMetaMtoAlbumM(AlbumModel albumM, MetaModel metaM);
    AlbumModel attachArtistMtoAlbumM(AlbumModel albumM, ArtistModel artistM);

}
