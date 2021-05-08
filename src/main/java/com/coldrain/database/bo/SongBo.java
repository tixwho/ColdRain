package com.coldrain.database.bo;

import com.coldrain.database.dao.SongDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;

public interface SongBo extends SongDao {


    //logical operation
    SongModel guaranteeSongModel(String title, ArtistModel artistM);
    SongModel createSongModel(MetaSong metaSong);
    SongModel attachArtistMtoSongM(SongModel songM, ArtistModel artistM);
    SongModel registerMetaMtoSongM(SongModel songM, MetaModel metaM);

}
