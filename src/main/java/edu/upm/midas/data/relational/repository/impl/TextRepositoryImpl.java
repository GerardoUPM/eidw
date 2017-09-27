package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.edsssdb.Text;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.TextRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className TextRepositoryImpl
 * @see
 */
@Repository("TextRepositoryDao")
public class TextRepositoryImpl extends AbstractDao<String, Text>
                                implements TextRepository {

    public Text findById(String textId) {
        Text text = getByKey(textId);
        return text;
    }

    @SuppressWarnings("unchecked")
    public Text findByIdQuery(String textId) {
        Text text = null;
        List<Text> textList = (List<Text>) getEntityManager()
                .createNamedQuery("Text.findById")
                .setParameter("textId", textId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(textList))
            text = textList.get(0);
        return text;
    }

    @SuppressWarnings("unchecked")
    public Text findByContentTypeQuery(String contentType) {
        Text text = null;
        List<Text> textList = (List<Text>) getEntityManager()
                .createNamedQuery("Text.findByContentType")
                .setParameter("contentType", contentType)
                .getResultList();
        if (CollectionUtils.isNotEmpty(textList))
            text = textList.get(0);
        return text;
    }

    @SuppressWarnings("unchecked")
    public Text findByTextQuery(String text) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Text findByIdNative(String textId) {
        Text text = null;
        List<Text> textList = (List<Text>) getEntityManager()
                .createNamedQuery("Text.findByIdNative")
                .setParameter("textId", textId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(textList))
            text = textList.get(0);
        return text;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Text findByIdNativeResultClass(String textId) {
        Text text = null;
        List<Text> textList = (List<Text>) getEntityManager()
                .createNamedQuery("Text.findByIdNativeResultClass")
                .setParameter("textId", textId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(textList))
            text = textList.get(0);
        return text;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findBySourceAndVersionNative(Date version, String source) {
        List<Object[]> texts = null;
        List<Object[]> textList = (List<Object[]>) getEntityManager()
                .createNamedQuery("Text.findBySourceAndVersionNative")
                .setParameter("version", version)
                .setParameter("source", source)
                .setMaxResults(10)
                .getResultList();
        if (CollectionUtils.isNotEmpty(textList))
            texts = textList;
        return texts;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Text> findAllQuery() {
        return (List<Text>) getEntityManager()
                .createNamedQuery("Text.findAll")
                .setMaxResults(0)
                .getResultList();
    }


    public void persist(Text text) {
        super.persist(text);
    }

    @Override
    public int insertNative(String textId, String contentType, String text) {
        return getEntityManager()
                .createNamedQuery("Text.insertNative")
                .setParameter("textId", textId)
                .setParameter("contentType", contentType)
                .setParameter("text", text)
                .executeUpdate();
    }

    @Override
    public int insertNativeUrl(String textId, String urlId) {
        return getEntityManager()
                .createNamedQuery("TextUrl.insertNative")
                .setParameter("textId", textId)
                .setParameter("urlId", urlId)
                .executeUpdate();
    }

    public boolean deleteById(String textId) {
        Text text = findById( textId );
        if(text ==null)
            return false;
        super.delete(text);
        return true;
    }

    public void delete(Text text) {
        super.delete(text);
    }

    public Text update(Text text) {
        return super.update(text);
    }

    public int updateByIdQuery(Text text) {
        return getEntityManager()
                .createNamedQuery("Text.updateById")
                .setParameter("textId", text.getTextId())
                .setParameter("contentType", text.getContentType())
                .setParameter("text", text.getText())
                .executeUpdate();
    }
}
