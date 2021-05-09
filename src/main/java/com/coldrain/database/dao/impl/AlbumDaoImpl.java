package com.coldrain.database.dao.impl;

import com.coldrain.database.dao.AlbumDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("AlbumDao")
@Transactional(transactionManager = "crTxManager")
public class AlbumDaoImpl extends CustomHibernateDaoSupport implements AlbumDao {

    Logger logger = LoggerFactory.getLogger(AlbumDaoImpl.class);

    @Override
    public void save(AlbumModel albumM) {
        getHibernateTemplate().save(albumM);
    }

    @Override
    public void update(AlbumModel albumM) {
        getHibernateTemplate().update(albumM);
    }

    @Override
    public void delete(AlbumModel albumM) {
        getHibernateTemplate().delete(albumM);
    }

    @Override
    public AlbumModel findByAlbumAndArtistM(String album, ArtistModel artistM) {
        DetachedCriteria criteria = DetachedCriteria.forClass(AlbumModel.class);
        criteria.add(Restrictions.and(Restrictions.eq("album",album),Restrictions.eq("albumArtistM",artistM)));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0){
            logger.info("Missing Album with album & artistM " +album + artistM);
            return null;
        }else if(list.size()>1){
            logger.warn("Finding multiple albums by album and artistM");
        }
        return (AlbumModel) list.get(0);
    }

    public boolean checkAlbumArtistExistenceInAlbum(ArtistModel albumArtistM){
        DetachedCriteria criteria = DetachedCriteria.forClass(AlbumModel.class);
        criteria.add(Restrictions.eq("albumArtistM",albumArtistM)).setProjection(Projections.rowCount());
        Long count = (Long)getHibernateTemplate().findByCriteria(criteria).get(0);
        return count > 0;
    }

    @Override
    public List<AlbumModel> findUnusedAlbumMs() {
        DetachedCriteria criteria = DetachedCriteria.forClass(AlbumModel.class);
        criteria.add(Restrictions.sizeEq("metaModels",0));
        List<AlbumModel> unusedAlbumMs = (List<AlbumModel>) getHibernateTemplate().findByCriteria(criteria);
        logger.debug("Found "+unusedAlbumMs.size()+" unused AlbumM(s)");
        return unusedAlbumMs;



    }
}
