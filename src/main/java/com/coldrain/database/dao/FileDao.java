package com.coldrain.database.dao;

import com.coldrain.database.models.FileModel;

public interface FileDao {

    void save(FileModel fileM);
    void update(FileModel fileM);
    void delete(FileModel fileM);
    FileModel findBySrc(String fileSrc);
    FileModel findByMd5(String fileMd5);


}
