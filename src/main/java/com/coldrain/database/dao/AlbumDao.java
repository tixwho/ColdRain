package com.coldrain.database.dao;

import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;

public interface AlbumDao {

    void save (AlbumModel albumM);
    void update (AlbumModel albumM);
    void delete (AlbumModel albumM);

    //query
    AlbumModel findByAlbumAndArtistM(String album, ArtistModel artistM);

}
