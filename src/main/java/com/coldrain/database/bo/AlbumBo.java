package com.coldrain.database.bo;

import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.playlist.generic.MetaSong;

public interface AlbumBo {

    void save (AlbumModel albumM);
    void update (AlbumModel albumM);
    void delete (AlbumModel albumM);

    //query
    AlbumModel findByAlbumAndArtistM(String album, ArtistModel artistM);

    //logical operations
    AlbumModel createAlbumModel(MetaSong metaSong);
    AlbumModel guaranteeAlbumModel(MetaSong metaSong);
    AlbumModel attachAlbumMtoMetaM(AlbumModel albumM, MetaModel metaM);
    AlbumModel registerAlbumMtoArtistM(AlbumModel albumM, ArtistModel artistM);

}
