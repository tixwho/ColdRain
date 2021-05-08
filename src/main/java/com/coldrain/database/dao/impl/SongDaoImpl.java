package com.coldrain.database.dao.impl;

import com.coldrain.database.dao.SongDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("SongDao")
@Transactional(transactionManager = "crTxManager")
public class SongDaoImpl extends CustomHibernateDaoSupport implements SongDao {

    @Override
    public void save(SongModel songM) {
        getHibernateTemplate().save(songM);
    }

    @Override
    public void update(SongModel songM) {
        getHibernateTemplate().update(songM);
    }

    @Override
    public void delete(SongModel songM) {
        getHibernateTemplate().delete(songM);
    }

    @Override
    public SongModel findByTitleAndArtistM(String title, ArtistModel artistM) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SongModel.class);
        criteria.add(Restrictions.and(Restrictions.eq("trackTitle",title),Restrictions.eq("artistM",artistM)));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0){
            logger.info("Missing Song with title & artistM " +title + artistM);
            return null;
        }else if(list.size()>1){
            logger.warn("Finding multiple Songs by title and artistM");
        }
        return (SongModel) list.get(0);
    }
}
