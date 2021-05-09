package com.coldrain.database.dao.impl;

import com.coldrain.database.annotations.CrTask;
import com.coldrain.database.dao.FileDao;
import com.coldrain.database.models.FileModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import javax.annotation.Nullable;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//hibernate template embedded
@Repository("FileDao")
@Transactional(transactionManager = "crTxManager")
public class FileDaoImpl extends CustomHibernateDaoSupport implements FileDao {

    private static final Logger logger = LoggerFactory.getLogger(FileDaoImpl.class);

    @Override
    public void save(FileModel fileM) {
        getHibernateTemplate().save(fileM);
    }

    @Override
    public void update(FileModel fileM) {
        getHibernateTemplate().update(fileM);
    }

    @Override
    public void delete(FileModel fileM) {
        getHibernateTemplate().delete(fileM);
    }

    @Override
    @Nullable
    @CrTask("FindFileBySrc")
    public FileModel findBySrc(String fileSrc) {
        DetachedCriteria criteria = DetachedCriteria.forClass(FileModel.class);
        //from FileModel f where f.src=?1
        criteria.add(Restrictions.eq("src",fileSrc));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0){
            logger.info("Missing files with src "+fileSrc);
            return null;
        }else if(list.size()>1){
            logger.warn("Finding multiple files by src");
        }
        return (FileModel)list.get(0);


    }

    @Override
    @Nullable
    @CrTask("FindFileByMd5")
    public FileModel findByMd5(String fileMd5) {
        DetachedCriteria criteria = DetachedCriteria.forClass(FileModel.class);
        //from FileModel f where f.src=?1
        criteria.add(Restrictions.eq("md5",fileMd5));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0){
            logger.info("Missing files with md5 "+fileMd5);
            return null;
        }else if(list.size()>1){
            logger.warn("Finding multiple files by md5");
        }
        return (FileModel)list.get(0);
    }

    @Override
    public boolean checkMetaExistenceInFile(MetaModel metaM) {
        DetachedCriteria criteria = DetachedCriteria.forClass(FileModel.class);
        criteria.add(Restrictions.eq("metaM",metaM)).setProjection(Projections.rowCount());
        Long count = (Long)getHibernateTemplate().findByCriteria(criteria).get(0);
        return count > 0;
    }

    @Override
    public List<FileModel> findAllFileModels() {
        DetachedCriteria criteria = DetachedCriteria.forClass(FileModel.class);
        List<FileModel> allFileModels = (List<FileModel>) getHibernateTemplate().findByCriteria(criteria);
        return allFileModels;
    }

    @Override
    public List<String> findAllFileModelsSRC() {
        DetachedCriteria criteria = DetachedCriteria.forClass(FileModel.class);
        criteria.setProjection(Projections.property("src"));
        List<String> allFileModelSRCs = (List<String>) getHibernateTemplate().findByCriteria(criteria);
        return allFileModelSRCs;
    }
}
