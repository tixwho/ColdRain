package com.coldrain.database.dao;

import com.coldrain.database.models.ArtistModel;

public interface ArtistDao {

    void save (ArtistModel artistM);
    void update (ArtistModel artistM);
    void delete (ArtistModel artistM);

    //query
    ArtistModel findByArtist (String artist);

}
