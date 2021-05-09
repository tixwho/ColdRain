package com.coldrain.database.dao;

import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import java.util.List;

public interface SongDao {

    void save(SongModel songM);
    void update(SongModel songM);
    void delete(SongModel songM);

    //query
    SongModel findByTitleAndArtistM(String title, ArtistModel artistM);

    boolean checkArtistExistenceInSong(ArtistModel artistM);

    List<SongModel> findUnusedSongMs();

}
