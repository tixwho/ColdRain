package com.coldrain.database.dao;

import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import javax.annotation.Nullable;

public interface MetaDao {

    void save(MetaModel metaM);
    void update(MetaModel metaM);
    void delete(MetaModel metaM);

    //query
    @Nullable
    MetaModel findByAlbumMandSongM(AlbumModel albumM, SongModel songM);
    int checkMetaCount(AlbumModel albumM, SongModel songM);

}
