package com.coldrain.database.bo;

import com.coldrain.database.dao.MetaDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.playlist.generic.MetaSong;

public interface MetaBo extends MetaDao {


    //logic level operation
    int checkMetaCount(MetaSong metaSong); //contains an extra conversion
    MetaModel guaranteeMetaModel(MetaSong metaSong);
    MetaModel createMetaModel(MetaSong metaSong);
    MetaModel registerFileMtoMetaM(MetaModel metaM, FileModel fileM);
    MetaModel attachAlbumMtoMetaM(MetaModel metaM, AlbumModel albumM);
    MetaModel attachSongMtoMetaM(MetaModel metaM, SongModel songM);

}
