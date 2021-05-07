package com.coldrain.database.bo;

import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.playlist.generic.MetaSong;
import javax.annotation.Nullable;

public interface FileBo {

    void save (FileModel fileM);
    void update (FileModel fileM);
    void delete (FileModel fileM);

    //query
    @Nullable
    FileModel findBySrc(String fileSrc);
    @Nullable
    FileModel findByMd5(String fileMd5);

    //logic level operation
    FileModel createFileModel(MetaSong metaSong);
    FileModel attachMetaMToFileM(FileModel fileM, MetaModel metaM);
    FileModel updateFileTimestamp(FileModel fileM);


}
