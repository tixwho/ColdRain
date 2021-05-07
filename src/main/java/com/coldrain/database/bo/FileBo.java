package com.coldrain.database.bo;

import com.coldrain.database.models.FileModel;

public interface FileBo {

    void save (FileModel fileM);
    void update (FileModel fileM);
    void delete (FileModel fileM);
    FileModel findBySrc(String fileSrc);
    FileModel findByMd5(String fileMd5);


}
