package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.HasText;
import edu.upm.midas.data.relational.entities.edsssdb.HasTextPK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasTextRepository
 * @see
 */
public interface HasTextRepository {

    HasText findById(HasTextPK hasTextPK);

    HasText findByIdQuery(HasTextPK hasTextPK);

    HasText findByTextOrderQuery(int textOrder);

    HasText findByIdNative(HasTextPK hasTextPK);

    HasText findByIdNativeResultClass(HasTextPK hasTextPK);

    List<HasText> findAllQuery();

    void persist(HasText hasText);

    int insertNative(String documentId, Date date, String sectionId, String textId, int textOrder);

    boolean deleteById(HasTextPK hasTextPK);

    void delete(HasText hasText);

    HasText update(HasText hasText);

    int updateByIdQuery(HasText hasText);
    
}
