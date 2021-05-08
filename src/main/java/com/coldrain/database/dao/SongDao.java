package com.coldrain.database.dao;

import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;

public interface SongDao {

    void save(SongModel songM);
    void update(SongModel songM);
    void delete(SongModel songM);

    //query
    SongModel findByTitleAndArtistM(String title, ArtistModel artistM);

}
