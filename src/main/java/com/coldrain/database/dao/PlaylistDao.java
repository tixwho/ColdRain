package com.coldrain.database.dao;

import com.coldrain.database.models.PlaylistModel;

public interface PlaylistDao {

    void save(PlaylistModel playlistM);
    void update(PlaylistModel playlistM);
    void delete(PlaylistModel playlistM);


    //query
    PlaylistModel findBySrc(String playlistSrc);

}
