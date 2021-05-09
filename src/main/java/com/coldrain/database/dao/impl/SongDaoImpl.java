package com.coldrain.database.dao.impl;

import com.coldrain.database.dao.SongDao;
import com.coldrain.database.models.ArtistModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("SongDao")
@Transactional(transactionManager = "crTxManager")
public class SongDaoImpl extends CustomHibernateDaoSupport implements SongDao {

    Logger logger = LoggerFactory.getLogger(SongDaoImpl.class);

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
        criteria.add(Restrictions
            .and(Restrictions.eq("trackTitle", title), Restrictions.eq("artistM", artistM)));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if (list.size() == 0) {
            logger.info("Missing Song with title & artistM " + title + artistM);
            return null;
        } else if (list.size() > 1) {
            logger.warn("Finding multiple Songs by title and artistM");
        }
        return (SongModel) list.get(0);
    }

    @Override
    public boolean checkArtistExistenceInSong(ArtistModel artistM) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SongModel.class);
        criteria.add(Restrictions.eq("artistM",artistM)).setProjection(Projections.rowCount());
        Long count = (Long)getHibernateTemplate().findByCriteria(criteria).get(0);
        return count > 0;
    }

    @Override
    public List<SongModel> findUnusedSongMs() {
        DetachedCriteria criteria = DetachedCriteria.forClass(SongModel.class);
        criteria.add(Restrictions.sizeEq("metaModels",0));
        List<SongModel> unusedSongMs = (List<SongModel>) getHibernateTemplate().findByCriteria(criteria);
        logger.debug("Found "+unusedSongMs.size()+" unused SongM(s)");
        return unusedSongMs;
    }
}
