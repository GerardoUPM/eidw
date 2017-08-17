package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.Source;

import java.util.List;

/**
 * Created by gerardo on 28/04/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className SourceService
 * @see
 */
public interface SourceService {

    Source findById(String sourceId);

    Source findByName(String sourceName);

    String findByNameNative(String sourceName);

    Source findLastSourceQuery();

    String findLastSourceIdQuery();

    List<Source> findAll();

    void save(Source source);

    int insertNative(String sourceId, String name);

    int insertNativeUrl(String sourceId, String urlId);

    boolean updateFindFull(Source source);

    boolean updateFindPartial(Source source);

    boolean deleteById(String sourceId);

}
