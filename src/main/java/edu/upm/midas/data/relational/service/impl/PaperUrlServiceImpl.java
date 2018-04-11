package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.PaperUrl;
import edu.upm.midas.data.relational.entities.edsssdb.PaperUrlPK;
import edu.upm.midas.data.relational.repository.PaperUrlRepository;
import edu.upm.midas.data.relational.service.PaperUrlService;
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
 * @className PaperUrlServiceImpl
 * @see
 */
@Service("paperUrlService")
public class PaperUrlServiceImpl implements PaperUrlService {

    @Autowired
    private PaperUrlRepository daoPaperUrl;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public PaperUrl findById(PaperUrlPK paperUrlPK) {
        PaperUrl paperUrl = daoPaperUrl.findById(paperUrlPK);
        return paperUrl;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public PaperUrl findByIdNative(PaperUrlPK paperUrlPK) {
        return daoPaperUrl.findByIdNative(paperUrlPK);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<PaperUrl> findAll() {
        List<PaperUrl> daoPaperUrlAllQuery = daoPaperUrl.findAllQuery();
        return daoPaperUrlAllQuery;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void save(PaperUrl paperUrl) {
daoPaperUrl.persist(paperUrl);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String paperId, String urlId) {
        return daoPaperUrl.insertNative(paperId, urlId);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(PaperUrl paperUrl, PaperUrlPK paperUrlPK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(PaperUrl paperUrl, PaperUrlPK paperUrlPK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(PaperUrlPK paperUrlPK) {
        return false;
    }
}
