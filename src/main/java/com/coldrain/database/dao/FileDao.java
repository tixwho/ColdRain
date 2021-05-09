package com.coldrain.database.dao;

import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import java.util.List;
import javax.annotation.Nullable;

public interface FileDao {

    void save (FileModel fileM);
    void update (FileModel fileM);
    void delete (FileModel fileM);

    //query
    @Nullable
    FileModel findBySrc(String fileSrc);
    @Nullable
    FileModel findByMd5(String fileMd5);
    boolean checkMetaExistenceInFile(MetaModel metaM);

    List<FileModel> findAllFileModels();
    List<String> findAllFileModelsSRC();


}
