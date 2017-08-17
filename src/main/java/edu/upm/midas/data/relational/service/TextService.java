package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className TextService
 * @see
 */
public interface TextService {

    Text findById(String textId);

    Text findByContentTypeQuery(String contentType);

    Text findByTextQuery(String text);

    List<Text> findAll();

    List<Object[]> findBySourceAndVersionNative(Date version, String source);

    void save(Text text);

    int insertNative(String textId, String contentType, String text);

    int insertNativeUrl(String textId, String urlId);

    boolean updateFindFull(Text text);

    boolean updateFindPartial(Text text);

    boolean deleteById(String textId);

}
