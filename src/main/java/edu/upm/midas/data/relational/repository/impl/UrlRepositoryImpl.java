package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Url;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.UrlRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className UrlRepositoryImpl
 * @see
 */
@Repository("UrlRepositoryDao")
public class UrlRepositoryImpl extends AbstractDao<String, Url>
                                implements UrlRepository{

    public Url findById(String urlId) {
        Url url = getByKey(urlId);
        return url;
    }

    @SuppressWarnings("unchecked")
    public Url findByIdQuery(String urlId) {
        Url url = null;
        List<Url> urlList = (List<Url>) getEntityManager()
                .createNamedQuery("Url.findById")
                .setParameter("urlId", urlId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(urlList))
            url = urlList.get(0);
        return url;
    }

    @SuppressWarnings("unchecked")
    public Url findByNameQuery(String urlName) {
        Url url = null;
        List<Url> urlList = (List<Url>) getEntityManager()
                .createNamedQuery("Url.findByUrlNativeResultClass")
                .setParameter("url", urlName)
                .getResultList();
        if (CollectionUtils.isNotEmpty(urlList))
            url = urlList.get(0);
        return url;
    }

    @Override
    public Url findLastUrlQuery() {
        Url url = null;
        url = (Url) getEntityManager()
                .createNamedQuery("Url.findLastUrl")
                .setMaxResults(1)
                .getResultList();
        return url;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Url findByIdNative(String urlId) {
        Url url = null;
        List<Url> urlList = (List<Url>) getEntityManager()
                .createNamedQuery("Url.findByIdNative")
                .setParameter("urlId", urlId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(urlList))
            url = urlList.get(0);
        return url;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Url findByIdNativeResultClass(String urlId) {
        Url url = null;
        List<Url> urlList = (List<Url>) getEntityManager()
                .createNamedQuery("Url.findByIdNativeResultClass")
                .setParameter("urlId", urlId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(urlList))
            url = urlList.get(0);
        return url;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Url> findAllQuery() {
        return (List<Url>) getEntityManager()
                .createNamedQuery("Url.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    public void persist(Url url) {
        super.persist(url);
    }

    @Override
    public int insertNative(String urlId, String url) {
        return getEntityManager()
                .createNamedQuery("Url.insertNative")
                .setParameter("urlId", urlId)
                .setParameter("url", url)
                .executeUpdate();
    }

    public boolean deleteById(String urlId) {
        Url url = findById( urlId );
        if(url ==null)
            return false;
        super.delete(url);
        return true;
    }

    public void delete(Url url) {
        super.delete(url);
    }

    public Url update(Url url) {
        return super.update(url);
    }

    @Override
    public int updateByIdQuery(Url url) {
        return getEntityManager()
                .createNamedQuery("Url.updateById")
                .setParameter("urlId", url.getUrlId())
                .setParameter("url", url.getUrl())
                .executeUpdate();
    }
}
