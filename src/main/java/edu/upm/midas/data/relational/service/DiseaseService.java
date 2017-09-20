package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.Disease;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DiseaseService
 * @see
 */
public interface DiseaseService {

    Disease findById(String diseaseId);

    Disease findByName(String diseaseName);

    Disease findByCui(String cui);

    Disease findLastDiseaseQuery();

    List<Disease> findAll();

    List<Object[]> findAllBySourceAndVersionNative(String sourceName, Date version);

    Object[] findByIdAndSourceAndVersionNative(String diseaseId, String sourceName, Date version);

    Object[] findByCuiAndSourceAndVersionNative(String cui, String sourceName, Date version);

    void save(Disease disease);

    int insertNative(String diseaseId, String name, String cui);

    int insertNativeHasDisease(String documentId, Date date, String diseaseId);

    boolean updateFindFull(Disease disease);

    boolean updateFindPartial(Disease disease);

    boolean deleteById(String diseaseId);

    int updateCuiByIdAndSourceAndVersionNative(String diseaseId, String cui, String sourceName, Date version);

}
