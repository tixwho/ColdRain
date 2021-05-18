package com.coldrain.database.bo;

import com.coldrain.database.dao.ArtistDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;

public interface ArtistBo extends ArtistDao {

    //logical operations

    /**
     * Persist a basic ArtistModel with artist name.
     *
     * @param artist artist name
     * @return Persisted ArtistModel
     */
    ArtistModel createArtistModel(String artist);

    /**
     * Persist a basic ArtistModel from 'Artist' field in metadata
     *
     * @param metaSong MetaSong instance to read
     * @return Persisted ArtistModel
     */
    ArtistModel createArtistModel_track(MetaSong metaSong);

    /**
     * Persist a basic ArtistModel from 'Album Artist' field in metadata
     *
     * @param metaSong MetaSong instance to read
     * @return Persisted ArtistModel
     */
    ArtistModel createArtistModel_album(MetaSong metaSong);

    /**
     * Retrieve an existing ArtistModel/Generate new ArtistModel from 'Artist' in given metadata
     *
     * @param metaSong MetaSong instance to read
     * @return Persisted ArtistModel that is either generated lately or retrieved.
     */
    ArtistModel guaranteeArtistModel_track(MetaSong metaSong);

    /**
     * Retrieve an existing ArtistModel/Generate new ArtistModel from 'Album Artist' in given
     * metadata
     *
     * @param metaSong MetaSong instance to read
     * @return Persisted ArtistModel that is either generated lately or retrieved.
     */
    ArtistModel guaranteeArtistModel_album(MetaSong metaSong);

    /**
     * A One-way connection to add a SongModel into ArtistModel collection.
     *
     * @param artistM ArtistModel to include a new SongModel relationship
     * @param songM   SongModel to be attached into ArtistModel collection.
     * @return ArtistModel with relationship persisted.
     */
    ArtistModel registerSongMtoArtistM(ArtistModel artistM, SongModel songM);

    /**
     * A One-way connection to add an AlbumModel into ArtistModel collection.
     *
     * @param artistM ArtistModel to include a new SongModel relationship
     * @param albumM  AlbumModel to be attached into ArtistModel collection.
     * @return ArtistModel with relationship persisted.
     */
    ArtistModel registerAlbumMtoArtistM(ArtistModel artistM, AlbumModel albumM);

    /**
     * Persist a two-way connection with an artist and another multi-value artist. Will
     * automatically toggle multi_status for multi-ArtistModel, but will not check root_status
     *
     * @param singleArtistM The single ArtistModel to be connected with.
     * @param multiArtistM  The multi-value ArtistModel to be connected with.
     */
    void connectSingleAndMultiArtistM(ArtistModel singleArtistM, ArtistModel multiArtistM);

    /**
     * Abandon a two-way connection with an artist and another multi-value artist. Will NOT
     * automatically toggle multi_status for multi-ArtistModel
     *
     * @param singleArtistM The single ArtistModel to be disconnected with.
     * @param multiArtistM  The multi-value ArtistModel to be disconnected with.
     */
    void disconnectSingleAndMultiArtistM(ArtistModel singleArtistM, ArtistModel multiArtistM);

    /**
     * Register an Artist to be another Artist's Jacket. Will automatically toggle root_status for
     * Jacket Artist
     *
     * @param rootArtistM   The root ArtistModel to be connected with.
     * @param jacketArtistM The jacket ArtistModel to be connected with.
     */
    void registerArtistJacket(ArtistModel rootArtistM, ArtistModel jacketArtistM);

    /**
     * Deregister an Artist to be another Artist's Jacket.  Handles root Artist relationship. Will
     * automatically toggle root_status back for previous Jacket Artist
     *
     * @param jacketArtistM The jacket ArtistModel to be disconnected with.
     */
    void abandonArtistJacket(ArtistModel jacketArtistM);

}
