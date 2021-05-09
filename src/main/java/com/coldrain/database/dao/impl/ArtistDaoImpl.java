package com.coldrain.database.dao.impl;

import com.coldrain.database.dao.ArtistDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("ArtistDao")
@Transactional(transactionManager = "crTxManager")
public class ArtistDaoImpl extends CustomHibernateDaoSupport implements ArtistDao {

    Logger logger = LoggerFactory.getLogger(ArtistDaoImpl.class);

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
        criteria.add(Restrictions.eq("artist", artist));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if (list.size() == 0) {
            logger.info("Missing Artist with artistName " + artist);
            return null;
        } else if (list.size() > 1) {
            logger.warn("Finding multiple Artists by artistName");
        }
        return (ArtistModel) list.get(0);
    }

    @Override
    public List<ArtistModel> findUnusedArtistMs() {
        DetachedCriteria criteria = DetachedCriteria.forClass(ArtistModel.class);
        criteria.add(Restrictions
            .and(Restrictions.sizeEq("albumModels", 0),
                Restrictions.sizeEq("songModels", 0)));
        List<ArtistModel> unusedArtistMs = (List<ArtistModel>) getHibernateTemplate()
            .findByCriteria(criteria);
        logger.debug("Found " + unusedArtistMs.size() + " unused ArtistM(s)");
        return unusedArtistMs;
    }


}
