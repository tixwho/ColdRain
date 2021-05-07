package com.coldrain.database.dao.impl;

import com.coldrain.database.dao.AlbumDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("AlbumDao")
public class AlbumDaoImpl extends CustomHibernateDaoSupport implements AlbumDao {

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
        criteria.add(Restrictions.and(Restrictions.eq("album",album),Restrictions.eq("artistM",artistM)));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0){
            logger.info("Missing Album with album & artistM " +album + artistM);
            return null;
        }else if(list.size()>1){
            logger.warn("Finding multiple albums by album and artistM");
        }
        return (AlbumModel) list.get(0);
    }
}
