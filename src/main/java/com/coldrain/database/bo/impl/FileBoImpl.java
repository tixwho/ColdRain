package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.FileBo;
import com.coldrain.database.dao.FileDao;
import com.coldrain.database.models.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FileBo")
public class FileBoImpl implements FileBo {



    FileDao fileDao;

    @Autowired
    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public void save(FileModel fileM) {
        fileDao.save(fileM);
    }

    @Override
    public void update(FileModel fileM) {
        fileDao.update(fileM);
    }

    @Override
    public void delete(FileModel fileM) {
        fileDao.delete(fileM);
    }

    @Override
    public FileModel findBySrc(String fileSrc) {
        return fileDao.findBySrc(fileSrc);
    }

    @Override
    public FileModel findByMd5(String fileMd5) {
        return fileDao.findByMd5(fileMd5);
    }
}
