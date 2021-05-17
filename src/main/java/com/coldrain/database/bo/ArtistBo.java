package com.coldrain.database.bo;

import com.coldrain.database.dao.ArtistDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;

public interface ArtistBo extends ArtistDao {

    //logical operations
    ArtistModel createArtistModel(String artist);
    ArtistModel createArtistModel_track(MetaSong metaSong);
    ArtistModel createArtistModel_album(MetaSong metaSong);
    ArtistModel guaranteeArtistModel_track(MetaSong metaSong);
    ArtistModel guaranteeArtistModel_album(MetaSong metaSong);
    ArtistModel registerSongMtoArtistM(ArtistModel artistM, SongModel songM);
    ArtistModel registerAlbumMtoArtistM(ArtistModel artistM, AlbumModel albumM);
    void connectSingleAndMultiArtistM(ArtistModel singleArtistM, ArtistModel multiArtistM);
    void disconnectSingleAndMultiArtistM(ArtistModel singleArtistM, ArtistModel multiArtistM);
    void registerArtistJacket(ArtistModel rootArtistM, ArtistModel jacketArtistM);
    void abandonArtistJacket(ArtistModel jacketArtistM);

}
