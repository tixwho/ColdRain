package com.coldrain.database.bo;

import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;

public interface ArtistBo {

    void save (ArtistModel artistM);
    void update (ArtistModel artistM);
    void delete (ArtistModel artistM);

    //query
    ArtistModel findByArtist (String artist);

    //logical operations
    ArtistModel createArtistModel(String artist);
    ArtistModel createArtistModel(MetaSong metaSong);
    ArtistModel guaranteeArtistModel_track(MetaSong metaSong);
    ArtistModel guaranteeArtistModel_album(MetaSong metaSong);
    ArtistModel attachArtistMtoSongM(ArtistModel artistM, SongModel songM);
    ArtistModel attachArtistMtoAlbumM(ArtistModel artistM, AlbumModel albumM);

}
