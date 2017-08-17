package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Url;
import edu.upm.midas.data.relational.repository.UrlRepository;
import edu.upm.midas.data.relational.service.UrlService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className UrlServiceImpl
 * @see
 */
@Service("urlService")
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository daoUrl;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Url findById(String urlId) {
        Url url = daoUrl.findById(urlId);
        //if(source!=null)
        //Hibernate.initialize(source.getDiseasesBySidsource());
        return url;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Url findByName(String urlName) {
        Url url = daoUrl.findByNameQuery(urlName);
/*
        if(source!=null)
            Hibernate.initialize(source.getVersionList());
*/
        return url;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Url findLastUrlQuery() {
        return daoUrl.findLastUrlQuery();
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Url> findAll() {
        List<Url> urlEntityList = daoUrl.findAllQuery();
        return urlEntityList;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Url url) {
        daoUrl.persist(url);
    }

    @Override
    public int insertNative(String urlId, String url) {
        return daoUrl.insertNative( urlId, url );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(Url url) {
        Url u = daoUrl.findById(url.getUrlId());
        if(u!=null){
            if(StringUtils.isNotBlank(url.getUrlId()))
                u.setUrlId(url.getUrlId());
            if(StringUtils.isNotBlank(url.getUrl()))
                u.setUrl(url.getUrl());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(Url url) {
        Url u = daoUrl.findById(url.getUrlId());
        if(u!=null){
            if(StringUtils.isNotBlank(url.getUrlId()))
                u.setUrlId(url.getUrlId());
            if(StringUtils.isNotBlank(url.getUrl()))
                u.setUrl(url.getUrl());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(String urlId) {
        Url url = daoUrl.findById(urlId);
        if(url !=null)
            daoUrl.delete(url);
        else
            return false;
        return true;
    }
}
