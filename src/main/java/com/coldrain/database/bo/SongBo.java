package com.coldrain.database.bo;

import com.coldrain.database.dao.SongDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;

public interface SongBo extends SongDao {

    //logical operation


    /**
     * Retrieve given SongModel/generate & persist if non-exist with title & given Artist
     *
     * @param title   Title of the song
     * @param artistM ArtistModel of the Song already persisted
     * @return persisted SongModel that is either retrieved or generated.
     */
    SongModel guaranteeSongModel(String title, ArtistModel artistM);

    /**
     * Persist a basic SongModel from given metadata that includes only Title field
     *
     * @param metaSong metaSong instance to read
     * @return persisted SongModel with title
     */
    SongModel createSongModel(MetaSong metaSong);

    /**
     * A one way connection to attach given ArtistModel to SongModel
     *
     * @param songM   SongModel assigned with given ArtistModel
     * @param artistM ArtistModel to be attached
     * @return SongModel persisted with ArtistModel
     */
    SongModel attachArtistMtoSongM(SongModel songM, ArtistModel artistM);

    /**
     * A one way connection to add a MetaModel into SongModel collection.
     *
     * @param songM SongModel to include a MetaModel relationship
     * @param metaM MetaModel to be added into SongModel collection.
     * @return SongModel with relationship persisted
     */
    SongModel registerMetaMtoSongM(SongModel songM, MetaModel metaM);

    /**
     * Persist a two-way relationship with a SongModel and another SongModel that's a cover version.
     * automatically toggle has_cover status for 'original' SongModel
     * @param origSongM original SongModel with cover version(s).
     * @param coverSongM cover SongModel
     */
    void registerCoverSong(SongModel origSongM, SongModel coverSongM);

    /**
     * Abandon the two-way relationship with a cover SongModel and its original SongModel.
     * automatically toggle has_cover status for the ORIGINAL SongModel
     * @param coverSongM cover SongModel to be disconnected with
     */
    void abandonCoverSong(SongModel coverSongM);

}
