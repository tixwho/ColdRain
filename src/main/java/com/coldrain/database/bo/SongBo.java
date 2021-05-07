package com.coldrain.database.bo;

import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;

public interface SongBo {

    void save(SongModel songM);
    void update(SongModel songM);
    void delete(SongModel songM);

    //query
    SongModel findByTitleAndArtistM(String title, ArtistModel artistM);

    //logical operation
    SongModel guaranteeSongModel(MetaSong metaSong);
    SongModel createSongModel(MetaSong metaSong);
    SongModel registerSongMtoArtistM(SongModel songM, ArtistModel artistM);
    SongModel attachSongMtoMetaM(SongModel songM, MetaModel metaM);

}
