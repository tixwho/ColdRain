package com.coldrain.database.dao.impl;

import com.coldrain.database.dao.MetaDao;
import com.coldrain.database.models.AlbumModel;
import com.coldrain.database.models.MetaModel;
import com.coldrain.database.models.SongModel;
import com.coldrain.database.utils.CustomHibernateDaoSupport;
import java.util.List;
import javax.annotation.Nullable;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("MetaDao")
public class MetaDaoImpl extends CustomHibernateDaoSupport implements MetaDao {

    private static final Logger logger = LoggerFactory.getLogger(MetaDaoImpl.class);

    @Override
    public void save(MetaModel metaM) {
        getHibernateTemplate().save(metaM);
    }

    @Override
    public void update(MetaModel metaM) {
        getHibernateTemplate().update(metaM);
    }

    @Override
    public void delete(MetaModel metaM) {
        getHibernateTemplate().delete(metaM);
    }

    @Nullable
    @Override
    public MetaModel findByAlbumMandSongM(AlbumModel albumM, SongModel songM) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MetaModel.class);
        criteria.add(Restrictions.and(Restrictions.eq("albumM",albumM),Restrictions.eq("songM",songM)));
        List list = getHibernateTemplate().findByCriteria(criteria);
        if(list.size()==0){
            logger.info("Missing meta with albumM & song M " +albumM + songM);
            return null;
        }else if(list.size()>1){
            logger.warn("Finding multiple metas by albumM and songM");
        }
        return (MetaModel) list.get(0);
    }

    @Override
    public int checkMetaCount(AlbumModel albumM, SongModel songM) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MetaModel.class);
        criteria.add(Restrictions.and(Restrictions.eq("albumM",albumM),Restrictions.eq("songM",songM)));
        List list = getHibernateTemplate().findByCriteria(criteria);
        return list.size();
    }
}
