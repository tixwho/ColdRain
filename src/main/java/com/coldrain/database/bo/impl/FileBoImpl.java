package com.coldrain.database.bo.impl;

import com.coldrain.database.bo.FileBo;
import com.coldrain.database.dao.FileDao;
import com.coldrain.database.models.FileInfoComp;
import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.utils.DbHelper;
import com.coldrain.playlist.generic.MetaSong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("FileBo")
@Transactional(transactionManager = "crTxManager")
public class FileBoImpl implements FileBo {


    FileDao fileDao;

    @Autowired
    public void setFileDao(@Qualifier("FileDao") FileDao fileDao) {
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

    @Override
    public FileModel createFileModel(MetaSong metaSong) {
        FileModel fileM = new FileModel(metaSong);
        FileInfoComp fileInfoComp = new FileInfoComp(metaSong);
        fileM.setFileInfoC(fileInfoComp);
        fileDao.save(fileM);
        return fileM;
    }

    @Override
    //one way: only attach metaM to fileM here.
    public FileModel attachMetaMToFileM(FileModel fileM, MetaModel metaM) {
        fileM.setMetaM(metaM);
        fileDao.update(fileM);
        return fileM;
    }

    @Override
    public FileModel updateFileTimestamp(FileModel fileM) {
        fileM.setLastModified(DbHelper.calcLastModTimestamp(fileM.getSrc()));
        fileDao.update(fileM);
        return fileM;
    }
}
