package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.Url;

import java.util.List;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className UrlService
 * @see
 */
public interface UrlService {

    Url findById(String urlId);

    Url findByName(String urlName);

    Url findLastUrlQuery();

    List<Url> findAll();

    void save(Url url);

    int insertNative(String urlId, String url);

    boolean updateFindFull(Url url);

    boolean updateFindPartial(Url url);

    boolean deleteById(String urlId);
    
}
