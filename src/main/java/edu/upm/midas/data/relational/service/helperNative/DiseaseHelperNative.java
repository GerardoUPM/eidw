package edu.upm.midas.data.relational.service.helperNative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.model.Doc;
import edu.upm.midas.data.extraction.model.Synonym;
import edu.upm.midas.data.extraction.model.code.Code;
import edu.upm.midas.data.extraction.model.code.Resource;
import edu.upm.midas.data.relational.entities.edsssdb.*;
import edu.upm.midas.data.relational.service.*;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import edu.upm.midas.utilsservice.UtilDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DiseaseHelper
 * @see
 */
@Service
public class DiseaseHelperNative {

    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private HasDiseaseService hasDiseaseService;
    @Autowired
    private SynonymService synonymService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DiseaseSynonymService diseaseSynonymService;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private DocumentHelperNative documentHelperNative;
    @Autowired
    private CodeHelperNative codeHelperNative;
    @Autowired
    private Common common;
    @Autowired
    private UtilDate utilDate;

    private static final Logger logger = LoggerFactory.getLogger(DiseaseHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;


    /**
     * @param document
     * @param documentId
     * @param version
     * @return
     * @throws JsonProcessingException
     */
    public String insertIfExist(Doc document, String documentId, Date version) throws JsonProcessingException {
        String diseaseName = document.getDisease().getName();
        String url = document.getUrl().getUrl();

        Disease diseaseEntity = diseaseService.findByName( diseaseName );
        //System.out.println(diseaseName+ "DIS: "+ diseaseEntity);
        if ( diseaseEntity == null ){
            String diseaseId = getDiseaseId();
            diseaseService.insertNative( diseaseId, diseaseName, "" );
            diseaseService.insertNativeHasDisease( documentId, version, diseaseId );
            return diseaseId;
        }else{
            //System.out.println("HasDisease: "+ documentId + " | " + version + " | " + diseaseEntity.getDiseaseId() );
            diseaseService.insertNativeHasDisease( documentId, version, diseaseEntity.getDiseaseId() );
            return diseaseEntity.getDiseaseId();
        }
    }


    /**
     * @param document
     * @param documentId
     * @param version
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public String insertIfExistPubMedArticles(Doc document, String documentId, Date version, String sourceName) throws IOException {
        String diseaseName = document.getDisease().getName();

//        Disease diseaseEntity = findDiseaseBySeveralWays(documentId, document.getDisease()/*, null*/);
        Disease diseaseEntity = diseaseService.findByName(diseaseName);
        //System.out.println(diseaseName+ "DIS: "+ diseaseEntity);
        //Si no encuentro la enfermedad, se inserta junto con sus sinonimos
        if ( diseaseEntity == null ){
            System.out.println(documentId + " | " + version  + " Nombre nuevo | PubMedDis: "+document.getDisease()+")");
            //Crea el id de enfermedad
            String diseaseId = getDiseaseId();
            //inserta la enfermedad
            diseaseService.insertNative( diseaseId, diseaseName, "" );
            //inserta la relacion entre disease y document
            diseaseService.insertNativeHasDisease( documentId, version, diseaseId );
            //Insertar sinonimos y sus códigos
            insertSynonyms(document.getDisease(), diseaseId, sourceName);
            return diseaseId;
        }else{
            System.out.println(documentId + " | " + version  + " Nombre encontrado (WikipediaDis:" + diseaseEntity + " | PubMedDis: "+document.getDisease()+")");
//            System.out.println("Match with DISNET Disease: "+ diseaseEntity.getDiseaseId() +" | "+ diseaseEntity.getName());
            //System.out.println("HasDisease: "+ documentId + " | " + version + " | " + diseaseEntity.getDiseaseId() );
            //inserta la relacion entre disease y document
            HasDisease existHasDisease = hasDiseaseService.findById(new HasDiseasePK(documentId, utilDate.convertSQLDateToUtilDate(version), diseaseEntity.getDiseaseId()));
            if (existHasDisease==null) {
                diseaseService.insertNativeHasDisease(documentId, version, diseaseEntity.getDiseaseId());
            }
            //inserta sinonimos si no tiene
            insertSynonyms(document.getDisease(), diseaseEntity.getDiseaseId(), sourceName);
            return diseaseEntity.getDiseaseId();
        }
    }


    private void insertSynonyms(edu.upm.midas.data.extraction.model.Disease disease, String diseaseId, String sourceName) throws JsonProcessingException {
        //Buscar sinonimo
        edu.upm.midas.data.relational.entities.edsssdb.Synonym synonym = null;
        //Si existen sinonimos
        if (disease.getSynonyms()!=null){
            for (Synonym syn: disease.getSynonyms()) {
                //Se busca si existe el sinonimo por su nombre
                edu.upm.midas.data.relational.entities.edsssdb.Synonym existSynonym = synonymService.findByNameQuery(syn.getName().trim());
                //Si no existe se inserta
                if (existSynonym==null){
                    //inserta sinonimo
                    if (synonymService.insertNative(syn.getName().trim()) > 0){
                        //obtiene el id de ese sinonimo
                        int synonymId = synonymService.findIdByNameQuery(syn.getName().trim());
                        //busca si existe ya la relación disease synonym
                        DiseaseSynonym diseaseSynonym = diseaseSynonymService.findById(new DiseaseSynonymPK(diseaseId, synonymId));
                        //si no existe la inserta
                        if (diseaseSynonym==null)
                            diseaseSynonymService.insertNative(diseaseId, synonymId);

                        //inserta los codigos del sinonimo, si existen
                        if (syn.getCodes()!=null){
                            //Busca si existe el código
                            codeHelperNative.insertIfExistSynonymCode(syn.getCodes(), synonymId);
                        }

                    }
                //Insertar la relacion disease synonym si no existe
                }else{
                    //busca si existe ya la relación disease synonym
                    DiseaseSynonym diseaseSynonym = diseaseSynonymService.findById(new DiseaseSynonymPK(diseaseId, existSynonym.getSynonymId()));
                    //si no existe la inserta
                    if (diseaseSynonym==null)
                        diseaseSynonymService.insertNative(diseaseId, existSynonym.getSynonymId());
                }
                //Exista o no sinonimo el nombre de la enfermedad se registrará como sinonimo aunque sea igual al nombre
                //de la enfermedad. ESTO ES PARA PUBMED
                if (sourceName.equals(Constants.SOURCE_PUBMED)){
                    //insertar nombre como sinonimo mesh
                    if (!common.isEmpty(disease.getName())){
                        //inserta sinonimo
                        if (synonymService.insertNative(disease.getName().trim()) > 0){
                            //obtiene el id de ese sinonimo
                            int synonymId = synonymService.findIdByNameQuery(disease.getName().trim());
                            //busca si existe ya la relación disease synonym
                            DiseaseSynonym diseaseSynonym = diseaseSynonymService.findById(new DiseaseSynonymPK(diseaseId, synonymId));
                            //si no existe la inserta
                            if (diseaseSynonym==null)
                                diseaseSynonymService.insertNative(diseaseId, synonymId);

                            //inserta los codigos del sinonimo, si existen
                            if (!common.isEmpty(disease.getMeSHUI())){
                                //Busca si existe el código
                                List<Code> codes = new ArrayList<>();
                                edu.upm.midas.data.relational.entities.edsssdb.Resource resourceEntity = resourceService.findByName(Constants.MESH_RESOURCE_NAME);
                                Resource resource = new Resource(resourceEntity.getResourceId(), resourceEntity.getName());
                                Code code = new Code();
                                code.setCode(disease.getMeSHUI());
                                code.setResource(resource);
                                codes.add(code);
                                codeHelperNative.insertIfExistSynonymCode(codes, synonymId);
                            }

                        }
                    }
                }
            }
        }

    }


    public Disease findDiseaseBySeveralWays(String documentId, edu.upm.midas.data.extraction.model.Disease disease/*, FileWriter fileWriter*/) throws IOException {
        Disease diseaseEntity = diseaseService.findByName(disease.getName());
        if (diseaseEntity!=null){
            System.out.println(documentId + "Nombre encontrado (WikipediaDis:" + diseaseEntity + " | PubMedDis: "+disease+")");
        }else{
            System.out.println(documentId + "Nombre nuevo | PubMedDis: "+disease+")");
        }
        return diseaseEntity;
//        Date version = documentHelperNative.getLastVersion();
//        Disease dis = null;
//        //List<Disease> diseaseEntityByCode = null;
//        Disease diseaseEntityByCode = null;
//        Disease diseaseEntityByCodeAndDiseaseName = null;
//        int codeCount = 0;
//
//        //Buscar enfermedad por nombre
//        Disease diseaseEntityByName = diseaseService.findByNameNativeUnrestricted( disease.getName().trim() );
//        List<Disease> diseaseEntityByMeshCode = diseaseService.findBySourceAndVersionAndCode( Constants.SOURCE_WIKIPEDIA, version, disease.getMeSHUI(), Constants.MESH_RESOURCE_NAME );
//        Disease diseaseEntityByMeshCodeAndDiseaseName = diseaseService.findBySourceAndVersionAndCodeAndDiseaseName( Constants.SOURCE_WIKIPEDIA, version, disease.getMeSHUI(), Constants.MESH_RESOURCE_NAME, disease.getName().trim() );
//        if (disease.getCodes()!=null) {
//            //diseaseEntityByCode = new ArrayList<>();
//            codeCount=0;
//            for (Code code: disease.getCodes()) {
//                diseaseEntityByCode = diseaseService.findOneBySourceAndVersionAndCode(Constants.SOURCE_WIKIPEDIA, version, code.getCode(), code.getResource().getName());
//                diseaseEntityByCodeAndDiseaseName = diseaseService.findBySourceAndVersionAndCodeAndDiseaseName( Constants.SOURCE_WIKIPEDIA, version, code.getCode(), code.getResource().getName(), disease.getName().trim() );
//                //Disease diseaseByCode = diseaseService.findOneBySourceAndVersionAndCode(Constants.SOURCE_WIKIPEDIA, version, code.getCode(), code.getResource().getName());
//                /*if (diseaseEntityByCode!=null){
//                    diseaseEntityByCode.add(diseaseByCode);
//                    codeCount++;
//                }*/
//
//                break;
//            }
//        }
//
//        if (diseaseEntityByMeshCode!=null){// diseaseEntityByName
//            if (diseaseEntityByMeshCodeAndDiseaseName!=null && diseaseEntityByMeshCode.size() > 1){
//                dis = diseaseEntityByMeshCodeAndDiseaseName;
//                System.out.println(documentId + " Match with (ByMeshCodeAndDiseaseName): (WikipediaDis:" + dis + " | PubMedDis: "+disease+")");
//            } else if (diseaseEntityByMeshCodeAndDiseaseName==null && diseaseEntityByMeshCode.size() > 1){
//                dis = diseaseEntityByMeshCode.get(0);
//                System.out.println(documentId + " Match with MeSH Code List (ByMeshCode>1): (WikipediaDis:" + dis + " | PubMedDis: "+disease+") | more match: " + diseaseEntityByMeshCode.toString() + "");
//            } else if (diseaseEntityByMeshCode.size() == 1){
//                dis = diseaseEntityByMeshCode.get(0);
//                System.out.println(documentId + " Match with unique MeSH Code (ByMeshCode==1): (WikipediaDis:" + dis + " | PubMedDis: "+disease+")");
//            } else {
//                if (diseaseEntityByCode!=null) {
//                    dis = diseaseEntityByCode;
//                    System.out.println(documentId + " Match (ByCode): (WikipediaDis:" + dis + " | PubMedDis: "+disease+")");
//                }else{
//                    System.out.println(documentId + " No Match (1): (PubMedDis: "+disease+")");
//                }
//            }
//        }else {
//            if (diseaseEntityByCodeAndDiseaseName!=null){
//                dis = diseaseEntityByCodeAndDiseaseName;
//                System.out.println(documentId + " Match with Code (3): (WikipediaDis:" + dis + " | PubMedDis: " + disease + ")");
//            }else {
//                if (diseaseEntityByCode != null) {
//                    dis = diseaseEntityByCode;
//                    System.out.println(documentId + " Match with Code (2): (WikipediaDis:" + dis + " | PubMedDis: " + disease + ")");
//                } else {
//                    System.out.println(documentId + " No Match (2): (PubMedDis: " + disease + ")");
//                }
//            }
//        }
//
//        return dis;
    }


    /**
     * @param diseaseName
     * @return
     */
    public boolean exist(String diseaseName){
        Disease disease = diseaseService.findByName( diseaseName );
        if( disease != null )
            return true;
        else
            return false;
    }


    public String getDiseaseId(){
        String lastId = diseaseService.findLastIdNative();
        if (!common.isEmpty(lastId)){
            int last = Integer.parseInt( common.cutStringPerformance(3, 0, lastId ) );
            return uniqueId.generateDisease( last + 1 );
        }else{
            return uniqueId.generateDisease(1);
        }
    }



}
