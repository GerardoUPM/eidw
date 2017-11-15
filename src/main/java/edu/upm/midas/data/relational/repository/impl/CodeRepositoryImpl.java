package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Code;
import edu.upm.midas.data.relational.entities.edsssdb.CodePK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.CodeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className CodeRepositoryImpl
 * @see
 */
@Repository("CodeRepositoryDao")
public class CodeRepositoryImpl extends AbstractDao<CodePK, Code>
                                implements CodeRepository {


    public Code findById(CodePK codePK) {
        Code code = getByKey(codePK);
        return code;
    }

    @SuppressWarnings("unchecked")
    public Code findByIdQuery(CodePK codePK) {
        Code code = null;
        List<Code> codeList = (List<Code>) getEntityManager()
                .createNamedQuery("Code.findById")
                .setParameter("code", codePK.getCode())
                .setParameter("resourceId", codePK.getResourceId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(codeList))
            code = codeList.get(0);
        return code;
    }

    @SuppressWarnings("unchecked")
    public Code findByCodeQuery(String code) {
        Code cod = null;
        List<Code> codeList = (List<Code>) getEntityManager()
                .createNamedQuery("Code.findByCode")
                .setParameter("code", code)
                .getResultList();
        if (CollectionUtils.isNotEmpty(codeList))
            cod = codeList.get(0);
        return cod;
    }

    @SuppressWarnings("unchecked")
    public Code findByResourceIdQuery(int resourceId) {
        Code code = null;
        List<Code> codeList = (List<Code>) getEntityManager()
                .createNamedQuery("Code.findByResourceId")
                .setParameter("resourceId", resourceId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(codeList))
            code = codeList.get(0);
        return code;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Code findByIdNativeMapping(CodePK codePK) {
        Code code = null;
        List<Code> codeList = (List<Code>) getEntityManager()
                .createNamedQuery("Code.findByIdNativeMapping")
                .setParameter("code", codePK.getCode())
                .setParameter("resourceId", codePK.getResourceId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(codeList))
            code = codeList.get(0);
        return code;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Code findByIdNativeResultClass(CodePK codePK) {
        Code code = null;
        List<Code> codeList = (List<Code>) getEntityManager()
                .createNamedQuery("Code.findByIdNativeResultClass")
                .setParameter("code", codePK.getCode())
                .setParameter("resourceId", codePK.getResourceId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(codeList))
            code = codeList.get(0);
        return code;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] findByIdNative(String code, int resourceId) {
        Object[] cod = null;
        List<Object[]> codeList = (List<Object[]>) getEntityManager()
                .createNamedQuery("Code.findByIdNative")
                .setParameter("code", code)
                .setParameter("resourceId", resourceId)
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(codeList))
            cod = codeList.get(0);
        return cod;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Code> findAllQuery() {
        return (List<Code>) getEntityManager()
                .createNamedQuery("Code.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    public void persist(Code code) {
        super.persist(code);
    }

    @Override
    public int insertNative(String code, int resourceId) {
        return getEntityManager()
                .createNamedQuery("Code.insertNative")
                .setParameter("code", code)
                .setParameter("resourceId", resourceId)
                .executeUpdate();
    }

    @Override
    public int insertNativeUrl(String code, int resourceId, String urlId) {
        return getEntityManager()
                .createNamedQuery("CodeUrl.insertNative")
                .setParameter("code", code)
                .setParameter("resourceId", resourceId)
                .setParameter("urlId", urlId)
                .executeUpdate();
    }

    @Override
    public int insertNativeHasCode(String documentId, Date date, String code, int resourceId) {
        return getEntityManager()
                .createNamedQuery("HasCode.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .setParameter("code", code)
                .setParameter("resourceId", resourceId)
                .executeUpdate();
    }

    public boolean deleteById(CodePK codePK) {
        Code code = findById( codePK );
        if(code ==null)
            return false;
        super.delete(code);
        return true;
    }

    public void delete(Code code) {
        super.delete(code);
    }

    public Code update(Code code) {
        return super.update(code);
    }

    @Override
    public int updateByIdQuery(Code code) {
        return 0;
    }
}
