package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Paper;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.PaperRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PaperRepositoryImpl
 * @see
 */
@Repository("PaperRepositoryDao")
public class PaperRepositoryImpl extends AbstractDao<String, Paper> implements PaperRepository {
    @Override
    public Paper findById(String paperId) {
        Paper paper = getByKey(paperId);
        return paper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Paper findByIdQuery(String paperId) {
        Paper paper = null;
        List<Paper> paperList = (List<Paper>) getEntityManager()
                .createNamedQuery("Paper.findById")
                .setParameter("paperId", paperId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(paperList))
            paper = paperList.get(0);
        return paper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Paper findByIdNative(String paperId) {
        Paper paper = null;
        List<Paper> paperList = (List<Paper>) getEntityManager()
                .createNamedQuery("Paper.findByIdNative")
                .setParameter("paperId", paperId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(paperList))
            paper = paperList.get(0);
        return paper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Paper findByIdNativeResultClass(String paperId) {
        Paper paper = null;
        List<Paper> paperList = (List<Paper>) getEntityManager()
                .createNamedQuery("Paper.findByIdNativeResultClass")
                .setParameter("paperId", paperId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(paperList))
            paper = paperList.get(0);
        return paper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Paper> findAllQuery() {
        List<Paper> terms = getEntityManager()
                .createNamedQuery("Paper.findAll")
                .setMaxResults(0)
                .getResultList();
        return terms;
    }

    @Override
    public void persist(Paper paper) {
        super.persist(paper);
    }

    @Override
    public int insertNative(String paperId, String doi, String alternativeId, String title, String authors, String keywords, boolean isFreeText) {
        return getEntityManager()
                .createNamedQuery("Paper.insertNative")
                .setParameter("paperId", paperId)
                .setParameter("doi", doi)
                .setParameter("alternativeId", alternativeId)
                .setParameter("title", title)
                .setParameter("authors", authors)
                .setParameter("keywords", keywords)
                .setParameter("freeText", isFreeText)
                .executeUpdate();
    }

    @Override
    public int insertNativeUrl(String paperId, String urlId) {
        return getEntityManager()
                .createNamedQuery("PaperUrl.insertNative")
                .setParameter("paperId", paperId)
                .setParameter("urlId", urlId)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(String paperId) {
        Paper paper = findById( paperId );
        if(paper ==null)
            return false;
        super.delete(paper);
        return true;
    }

    @Override
    public void delete(Paper paper) {
        super.delete(paper);
    }

    @Override
    public Paper update(Paper paper) {
        return super.update(paper);
    }

    @Override
    public int updateByIdQuery(Paper paper) {
        return 0;
    }
}
