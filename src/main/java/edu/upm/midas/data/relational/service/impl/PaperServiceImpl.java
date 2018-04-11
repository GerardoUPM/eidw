package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Paper;
import edu.upm.midas.data.relational.repository.PaperRepository;
import edu.upm.midas.data.relational.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PaperServiceImpl
 * @see
 */
@Service("paperService")
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperRepository daoPaper;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Paper findById(String paperId) {
        Paper paper = daoPaper.findById(paperId);
        return paper;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Paper findByIdNative(String paperId) {
        Paper paper = daoPaper.findByIdNative(paperId);
        return paper;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Paper> findAll() {
        List<Paper> daoPaperAllQuery = daoPaper.findAllQuery();
        return daoPaperAllQuery;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Paper paper) {
        daoPaper.persist(paper);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String paperId, String doi, String alternativeId, String title, String authors, String keywords, boolean isFreeText) {
        return daoPaper.insertNative(paperId, doi,alternativeId, title, authors, keywords, isFreeText);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNativeUrl(String paperId, String urlId) {
        return daoPaper.insertNativeUrl( paperId, urlId );
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(Paper paper) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(Paper paper) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(String paperId) {
        return false;
    }
}
