package com.coldrain.database.bo;

import com.coldrain.database.dao.FileDao;
import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.playlist.generic.MetaSong;
import java.util.List;

public interface FileBo extends FileDao {


    //logic level operation
    FileModel createFileModel(MetaSong metaSong);
    FileModel attachMetaMToFileM(FileModel fileM, MetaModel metaM);
    FileModel updateFileTimestamp(FileModel fileM);
    List<FileModel> findAllInvalidFiles();


}
