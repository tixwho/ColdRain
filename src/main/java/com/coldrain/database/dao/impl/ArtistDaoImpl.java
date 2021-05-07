package com.coldrain.database.dao.impl;

import com.coldrain.database.dao.ArtistDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("ArtistDao")
public class ArtistDaoImpl extends CustomHibernateDaoSupport implements ArtistDao {

    @Override
    public void save(ArtistModel artistM) {
        getHibernateTemplate().save(artistM);
    }

    @Override
    public void update(ArtistModel artistM) {
        getHibernateTemplate().update(artistM);
    }

    @Override
    public void delete(ArtistModel artistM) {
        getHibernateTemplate().delete(artistM);
    }

    @Override
    public ArtistModel findByArtist(String artist) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ArtistModel.class);
        criteria.add(Restrictions.eq("artist",artist));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0){
            logger.info("Missing Artist with artistName " +artist);
            return null;
        }else if(list.size()>1){
            logger.warn("Finding multiple Artists by artistName");
        }
        return (ArtistModel) list.get(0);
    }
}
