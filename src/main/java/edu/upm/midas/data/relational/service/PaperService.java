package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.Paper;

import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className PaperService
 * @see
 */
public interface PaperService {

    Paper findById(String paperId);

    Paper findByIdNative(String paperId);

    List<Paper> findAll();

    void save(Paper paper);

    int insertNative(String paperId, String doi, String alternativeId, String title, String authors, String keywords, boolean isFreeText);

    int insertNativeUrl(String paperId, String urlId);

    boolean updateFindFull(Paper paper);

    boolean updateFindPartial(Paper paper);

    boolean deleteById(String paperId);

}
