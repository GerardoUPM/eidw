package edu.upm.midas.data.validation.metamap.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.relational.service.DiseaseService;
import edu.upm.midas.data.relational.service.helperNative.ConfigurationHelper;
import edu.upm.midas.data.relational.service.helperNative.SymptomHelperNative;
import edu.upm.midas.data.validation.helper.ConsultHelper;
import edu.upm.midas.data.validation.metamap.Metamap;
import edu.upm.midas.data.validation.metamap.metamapApiResponse.impl.MetamapResourceServiceImpl;
import edu.upm.midas.data.validation.metamap.model.receiver.Configuration;
import edu.upm.midas.data.validation.metamap.model.receiver.Request;
import edu.upm.midas.data.validation.metamap.model.receiver.Text;
import edu.upm.midas.data.validation.metamap.model.response.Concept;
import edu.upm.midas.data.validation.metamap.model.response.ProcessedText;
import edu.upm.midas.data.validation.metamap.model.response.Response;
import edu.upm.midas.data.validation.metamap.model.special.HasSymptom;
import edu.upm.midas.data.validation.metamap.model.special.SemanticType;
import edu.upm.midas.data.validation.metamap.model.special.Symptom;
import edu.upm.midas.data.validation.model.Consult;
import edu.upm.midas.data.validation.model.query.ResponseText;
import edu.upm.midas.utilsservice.ReplaceUTF8;
import edu.upm.midas.utilsservice.UniqueId;
import edu.upm.midas.utilsservice.UtilDate;
import gov.nih.nlm.nls.metamap.Ev;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.*;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className MetamapService
 * @see
 */
@Service
public class MetamapService {

    @Autowired
    private ConsultHelper consultHelper;
    @Autowired
    private SymptomHelperNative symptomHelperNative;
    @Autowired
    private MetamapResourceServiceImpl metamapResourceService;
    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private ConfigurationHelper configurationHelper;

    @Autowired
    private Metamap metamap;
    @Autowired
    private ReplaceUTF8 replaceUTF8;
    @Autowired
    private UtilDate utilDate;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Constants constants;



    public void filter(Consult consult) throws Exception {
        Request request = new Request();//VALIDAR CONSULT
        Configuration conf = new Configuration();
        List<Text> texts = new ArrayList<>();
        String sourceId = "";
        Date version = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        conf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        conf.setSources(sources);
        conf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);

        request.setConfiguration( conf );

        System.out.println("Get all texts by version and source...");
        List<ResponseText> responseTexts = consultHelper.findTextsByVersionAndSource( consult );
        System.out.println("size: " + responseTexts.size());
        if (responseTexts != null) {
            int countRT = 1;
            for (ResponseText responseText : responseTexts) {
                if (countRT == 1){
                    sourceId = responseText.getSourceId();
                    version = responseText.getVersion();
                }

                Text text = new Text();
                text.setId( responseText.getTextId() );
                text.setText( responseText.getText() );
                texts.add(text);
                //System.out.println(responseText.getTextId());
                //System.out.println("LLAMAR("+countRT+"): " + responseText.isCall());
                if (responseText.isCall()){
                    // Se agregan los textos hasta el momento

                    //request.setTextList( texts );
                    //System.out.println("textsList size is: " + texts.size() + " AND request.textList is:" + request.getTextList().size());

                    //System.out.println( gson.toJson( request ) );


                    // Se eliminan los textos hasta el momento para dar paso a los nuevos y no superar nunca envíos
                    // de mas de 300 elementos.
                    //request.getTextList().clear();
                    //texts.clear();
                }
                countRT++;
            }

            //<editor-fold desc="BLOQUE QUE LLAMA Y OBTIENE RESULTADOS DE LA API">
            request.setTextList( texts );
            request.setToken(Constants.TOKEN);

            System.out.println( "Connection_ with METAMAP API..." );
            System.out.println( "Founding medical concepts in a texts... please wait, this process can take from minutes to hours... " );
            Response response = metamapResourceService.filterTexts( request );
            System.out.println( "Texts Size request..." + request.getTextList().size());
            System.out.println( "Filter Texts Size response..." + response.getTextList().size() );
            response.setAuthorized(response.getTextList().size()>=request.getTextList().size());
            System.out.println("Authorization: "+ response.isAuthorized());

            if (response.isAuthorized()) {
                System.out.println("save metamap reponse...");
                writeJSONFile(gson.toJson(response.getTextList()), utilDate.dateFormatyyyMMdd(version) /*utilDate.getNowFormatyyyyMMdd()*/);
                System.out.println("Insert symptoms starting...");
                System.out.println(request.getTextList().size());
                int count = 1;//VALIDAR
                if (response.getTextList() != null) {
                    for (edu.upm.midas.data.validation.metamap.model.response.Text filterText : response.getTextList()) {
                        System.out.println("TEXT_ID: " + filterText.getId() + " | CONCEPTS(" + filterText.getConcepts().size() + "): ");
                        int countSymptoms = 1;
                        for (edu.upm.midas.data.validation.metamap.model.response.Concept concept : filterText.getConcepts()) {
                            System.out.println("Concept{ cui: " + concept.getCui() + " name: " + concept.getName() + " semTypes:" + concept.getSemanticTypes().toString() + "}");
                            symptomHelperNative.insertIfExist(concept, filterText.getId());//text.getId()
                            countSymptoms++;
                        }
                        count++;
                    }
                    System.out.println("Insert symptoms ready!...");

                } else {
                    System.out.println("ERROR");
                /*System.out.println(gson.toJson( response ) );*/
                }
                //</editor-fold>

                System.out.println("Insert configuration...");
                String configurationJson = gson.toJson(request.getConfiguration());
                configurationHelper.insert(Constants.SOURCE_WIKIPEDIA, version, constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
                //System.out.println("Insert configuration ready!...");
            }else{
                System.out.println("Authorization message: " + response.getAuthorizationMessage() + " | token: " + response.getToken());
            }
        }

    }


    /**
     *
     * @param consult
     * @return
     * @throws Exception
     */
    @Transactional
    public void localFilter(Consult consult) throws Exception {
        Request request = new Request();//VALIDAR CONSULT
        Configuration conf = new Configuration();
        List<Text> texts = new ArrayList<>();
        String sourceId = "";
        Date version = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        conf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        conf.setSources(sources);
        conf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);

        request.setConfiguration( conf );

        System.out.println("Get all texts by version and source...");
        List<ResponseText> responseTexts = consultHelper.findTextsByVersionAndSource( consult );
        System.out.println("size: " + responseTexts.size());
        if (responseTexts != null) {
            int countRT = 1;
            for (ResponseText responseText : responseTexts) {
                if (countRT == 1){
                    sourceId = responseText.getSourceId();
                    version = responseText.getVersion();
                }
                System.out.println("("+countRT+") Filter text: " + responseText.getTextId());
                String textNonAscii = replaceUTF8.replaceLooklike( responseText.getText() );

                if (!textNonAscii.isEmpty()){
                    for (Ev conceptEv : metamap.performNLP( textNonAscii ) ) {

                        Concept concept = new Concept();
                        concept.setCui( conceptEv.getConceptId() );
                        concept.setName( conceptEv.getConceptName() );
                        concept.setSemanticTypes( conceptEv.getSemanticTypes() );
                        concept.setMatchedWords(conceptEv.getMatchedWords());
                        concept.setPositionalInfo(conceptEv.getPositionalInfo().toString());

                        System.out.println( "   Insert symptom..." + concept.toString() );
                        symptomHelperNative.insertIfExist(concept, responseText.getTextId());
                    }// busqueda de conceptos con metamap
                }// validación del texto no vacío
                countRT++;
            }
        }

        if (!sourceId.isEmpty() && !version.toString().isEmpty()) {
            System.out.println("Insert configuration...");
            String configurationJson = gson.toJson(request.getConfiguration());
            configurationHelper.insert(Constants.SOURCE_WIKIPEDIA, version, constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
            System.out.println("Insert configuration ready!...");
        }

    }





    /**
     *
     * @param consult
     * @return
     * @throws Exception
     */
    public void filterByParts(Consult consult) throws Exception {
        Request request = new Request();//VALIDAR CONSULT
        Configuration conf = new Configuration();
        List<Text> texts = new ArrayList<>();
        String sourceId = "";
        Date version = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        conf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        conf.setSources(sources);
        conf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);
        conf.setConcept_location(true);

        request.setConfiguration( conf );

        System.out.println("Get all texts by version and source from DB...");
        List<ResponseText> responseTexts = consultHelper.findTextsByVersionAndSource( consult );
        System.out.println("size: " + responseTexts.size());
        if (responseTexts != null) {
            int countRT = 1;
            //Se recorren todos los textos recuperados
            for (ResponseText responseText : responseTexts) {
                if (countRT == 1){
                    sourceId = responseText.getSourceId();
                    version = responseText.getVersion();
                }
                //SE generan objetos textos para despues ser procesados
                Text text = new Text();
                text.setId( responseText.getTextId() );
                text.setText( responseText.getText() );
                if (countRT == 1){
                    text.setId( "MyIDGLG");
                    text.setText( "Fever" );
                }
                texts.add(text);

                //System.out.println(responseText.getTextId());
                System.out.println(countRT+". CALL " + responseText.isCall() + " FROM: " + responseTexts.size() );
                //isCall=true indica cuando se llamará al servicio Web de Metamap
                if (responseText.isCall()){
                    // Se agregan los textos hasta el momento
                    //System.out.println( gson.toJson( request ) );
                    System.out.println("");
                    //<editor-fold desc="BLOQUE QUE LLAMA Y OBTIENE RESULTADOS DE LA API">
                    request.setTextList( texts );
                    request.setToken(Constants.TOKEN);

                    //request.setTextList( texts );
                    System.out.println("textsList size is: " + texts.size() + " AND request.textList is:" + request.getTextList().size());

                    System.out.println( "Connection_ with METAMAP API..." );
                    System.out.println( "Founding medical concepts in a texts... please wait, this process can take from minutes to hours... " );
                    //Se llama a la METAMAP REST API
                    Response response = metamapResourceService.filterTexts( request );
                    System.out.println( "Texts Size request..." + request.getTextList().size());
                    System.out.println( "Filter Texts Size response..." + response.getTextList().size() );
                    response.setAuthorized(response.getTextList().size()>=request.getTextList().size());
                    System.out.println("Authorization: "+ response.isAuthorized());

                    if (response.isAuthorized()) {

                        System.out.println("Insert symptoms starting...");
                        System.out.println(request.getTextList().size());
                        int count = 1;//VALIDAR
                        if (response.getTextList() != null) {
                            for (edu.upm.midas.data.validation.metamap.model.response.Text filterText : response.getTextList()) {
                                System.out.println("TEXT_ID: " + filterText.getId() + " | CONCEPTS(" + filterText.getConcepts().size() + "): ");
                                int countSymptoms = 1;
                                for (edu.upm.midas.data.validation.metamap.model.response.Concept concept : filterText.getConcepts()) {
                                    System.out.println("Concept{ cui: " + concept.getCui() + " name: " + concept.getName() + " semTypes:" + concept.getSemanticTypes().toString() + "Position: " + concept.getMatchedWords() + "}");
                                    symptomHelperNative.insertIfExist(concept, filterText.getId());//text.getId()
                                    System.out.println("Concept insert ready!");
                                    countSymptoms++;
                                }
                                count++;
                            }
                            System.out.println("Insert symptoms ready!...");

                        } else {
                            System.out.println("ERROR");
                /*System.out.println(gson.toJson( response ) );*/
                        }
                        //</editor-fold>


                    }else{
                        System.out.println("Authorization message: " + response.getAuthorizationMessage() + " | token: " + response.getToken());
                    }


                    // Se eliminan los textos hasta el momento para dar paso a los nuevos y no superar nunca envíos
                    // de mas de 300 elementos.
                    request.getTextList().clear();
                    texts.clear();
                }
                countRT++;
            }


            System.out.println("Insert configuration...");
            String configurationJson = gson.toJson(request.getConfiguration());
            configurationHelper.insert(consult.getSource(), sourceId, version, constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
            //System.out.println("Insert configuration ready!...");



        }

    }



    /**
     *
     * @param consult
     * @return
     * @throws Exception
     */


    public void filterAndStorageInJASON(Consult consult) throws Exception {
        Request request = new Request();//VALIDAR CONSULT
        Configuration conf = new Configuration();
        List<Text> texts = new ArrayList<>();
        String sourceId = "";
        Date version = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        conf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        //sources.add("DSM-5");
        conf.setSources(sources);
        conf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);
        conf.setConcept_location(true);

        request.setConfiguration( conf );
        request.setSnapshot(consult.getVersion());

        System.out.println("Get all texts by version and source...");
        List<ResponseText> responseTexts = consultHelper.findTextsByVersionAndSource( consult );
        System.out.println("size: " + responseTexts.size());
        if (responseTexts != null) {
            int countRT = 1;
            for (ResponseText responseText : responseTexts) {
                if (countRT == 1){
                    sourceId = responseText.getSourceId();
                    version = responseText.getVersion();
                }

                Text text = new Text();
                text.setId( responseText.getTextId() );
                text.setText( responseText.getText() );
                texts.add(text);
                //System.out.println(responseText.getTextId());
                //System.out.println("LLAMAR("+countRT+"): " + responseText.isCall());
                if (responseText.isCall()){
                    // Se agregan los textos hasta el momento

                    //request.setTextList( texts );
                    //System.out.println("textsList size is: " + texts.size() + " AND request.textList is:" + request.getTextList().size());

                    //System.out.println( gson.toJson( request ) );


                    // Se eliminan los textos hasta el momento para dar paso a los nuevos y no superar nunca envíos
                    // de mas de 300 elementos.
                    //request.getTextList().clear();
                    //texts.clear();
                }
                countRT++;
            }

            //<editor-fold desc="BLOQUE QUE LLAMA Y OBTIENE RESULTADOS DE LA API">
            request.setTextList( texts );
            request.setToken(Constants.TOKEN);

            //System.out.println( "Request: " + request);
            System.out.println( "Connection_ with METAMAP API..." );
            System.out.println( "Founding medical concepts in a texts... please wait, this process can take from minutes to hours... " );
            Response response = metamapResourceService.filterTexts( request );
            System.out.println( "Texts Size request..." + request.getTextList().size());
            System.out.println( "Filter Texts Size response..." + response.getTextList().size() );
            response.setAuthorized(response.getTextList().size()>=request.getTextList().size());
            System.out.println("Authorization: "+ response.isAuthorized());

            if (response.isAuthorized()) {
                System.out.println("save metamap reponse...");
                ProcessedText processedText = new ProcessedText();
                processedText.setTexts(response.getTextList());
                //writeJSONFile(gson.toJson(processedText), utilDate.dateFormatyyyMMdd(version) /*utilDate.getNowFormatyyyyMMdd()*/);
                System.out.println("save metamap ready...");

                System.out.println("Insert configuration...");
                String configurationJson = gson.toJson(request.getConfiguration());
                configurationHelper.insert(Constants.SOURCE_WIKIPEDIA, version, constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
            }else{
                System.out.println("Authorization message: " + response.getAuthorizationMessage() + " | token: " + response.getToken());
            }
        }

    }


    /**
     *
     * @param consult
     * @return
     * @throws Exception
     */
    public void populateTextsStoredJSON(Consult consult) throws Exception {
        Request request = new Request();//VALIDAR CONSULT
        Configuration conf = new Configuration();
        List<Text> texts = new ArrayList<>();
        String sourceId = "";
        Date version = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        conf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        conf.setSources(sources);
        conf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);
        conf.setConcept_location(true);

        request.setConfiguration( conf );

        System.out.println("Get all texts by version and source...");
        List<ResponseText> responseTexts = consultHelper.findTextsByVersionAndSource( consult );
        System.out.println("size: " + responseTexts.size());
        if (responseTexts != null) {
            int countRT = 1;
            for (ResponseText responseText : responseTexts) {
                if (countRT == 1){
                    sourceId = responseText.getSourceId();
                    version = responseText.getVersion();
                }

                Text text = new Text();
                text.setId( responseText.getTextId() );
                text.setText( responseText.getText() );
                texts.add(text);
                //System.out.println(responseText.getTextId());
                //System.out.println("LLAMAR("+countRT+"): " + responseText.isCall());
                if (responseText.isCall()){
                    // Se agregan los textos hasta el momento

                    //request.setTextList( texts );
                    //System.out.println("textsList size is: " + texts.size() + " AND request.textList is:" + request.getTextList().size());

                    //System.out.println( gson.toJson( request ) );


                    // Se eliminan los textos hasta el momento para dar paso a los nuevos y no superar nunca envíos
                    // de mas de 300 elementos.
                    //request.getTextList().clear();
                    //texts.clear();
                }
                countRT++;
            }

            //<editor-fold desc="BLOQUE QUE LLAMA Y OBTIENE RESULTADOS DE LA API">
            request.setTextList( texts );
            request.setToken(Constants.TOKEN);

            System.out.println( "Connection_ with METAMAP API..." );
            System.out.println( "Founding medical concepts in a texts... please wait, this process can take from minutes to hours... " );
            List<edu.upm.midas.data.validation.metamap.model.response.Text> textList = readMetamapResponseJSON(consult);

            System.out.println( "Texts Size request..." + request.getTextList().size());
            System.out.println( "Filter Texts Size response..." + textList.size() );

            if (textList.size() == request.getTextList().size()) {
                System.out.println("Insert symptoms starting...");
                int count = 1;//VALIDAR
                for (edu.upm.midas.data.validation.metamap.model.response.Text filterText : textList) {
                    System.out.println(count + ". to ("+textList.size() + ") TEXT_ID: " + filterText.getId() + " | CONCEPTS(" + filterText.getConcepts().size() + "): ");
                    int countSymptoms = 1;
                    for (edu.upm.midas.data.validation.metamap.model.response.Concept concept : filterText.getConcepts()) {
                        System.out.println("Concept{ cui: " + concept.getCui() + " name: " + concept.getName() + " semTypes:" + concept.getSemanticTypes().toString() + "}");
                        symptomHelperNative.insertIfExist(concept, filterText.getId());//text.getId()
                        countSymptoms++;
                    }
                    count++;
                }
                System.out.println("Insert symptoms ready!...");
                //</editor-fold>

                System.out.println("Insert configuration...");
                String configurationJson = gson.toJson(request.getConfiguration());
                configurationHelper.insert(Constants.SOURCE_WIKIPEDIA, version, constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
                System.out.println("Insert configuration ready!...");
            }else{
                System.out.println("Texts Size Different: request: " + request.getTextList().size() + " | json: " + textList.size());
            }
        }

    }


    /**
     *
     * @param consult
     * @return
     * @throws Exception
     */
    public void restartPopulateTextsStoredJSON(Consult consult) throws Exception {
        Request request = new Request();//VALIDAR CONSULT
        Configuration conf = new Configuration();
        List<Text> texts = new ArrayList<>();
        String sourceId = "";
        Date version = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        conf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        conf.setSources(sources);
        conf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);
        conf.setConcept_location(true);

        request.setConfiguration( conf );

        System.out.println("Get all texts by version and source...");
        List<ResponseText> responseTexts = consultHelper.restartFindTextsByVersionAndSource( consult );
        System.out.println("size: " + responseTexts.size());
        if (responseTexts != null) {
            int countRT = 1;
            for (ResponseText responseText : responseTexts) {
                if (countRT == 1){
                    sourceId = responseText.getSourceId();
                    version = responseText.getVersion();
                }

                Text text = new Text();
                text.setId( responseText.getTextId() );
                text.setText( responseText.getText() );
                texts.add(text);
                //System.out.println(responseText.getTextId());
                //System.out.println("LLAMAR("+countRT+"): " + responseText.isCall());
                if (responseText.isCall()){
                    // Se agregan los textos hasta el momento

                    //request.setTextList( texts );
                    //System.out.println("textsList size is: " + texts.size() + " AND request.textList is:" + request.getTextList().size());

                    //System.out.println( gson.toJson( request ) );


                    // Se eliminan los textos hasta el momento para dar paso a los nuevos y no superar nunca envíos
                    // de mas de 300 elementos.
                    //request.getTextList().clear();
                    //texts.clear();
                }
                countRT++;
            }

            //<editor-fold desc="BLOQUE QUE LLAMA Y OBTIENE RESULTADOS DE LA API">
            request.setTextList( texts );//resultado de la consulta
            request.setToken(Constants.TOKEN);

            System.out.println( "Connection_ with METAMAP API..." );
            System.out.println( "Founding medical concepts in a texts... please wait, this process can take from minutes to hours... " );
            List<edu.upm.midas.data.validation.metamap.model.response.Text> textList = readMetamapResponseJSON(consult);

            System.out.println( "Texts Size request..." + request.getTextList().size());
            System.out.println( "Filter Texts Size response..." + textList.size() );
            int insertados = 1, noinsertados = 1;
            System.out.println("Insert symptoms starting...");
            int count = 1;//VALIDAR
            for (edu.upm.midas.data.validation.metamap.model.response.Text filterText : textList) {
                //Verifica que se encuentre en la lista de los textos
                if (contains(texts, filterText.getId())) {insertados++;
                    System.out.println(count + ". to (" + textList.size() + ") 'Inserta' TEXT_ID: " + filterText.getId() + " | CONCEPTS(" + filterText.getConcepts().size() + "): ");
                    int countSymptoms = 1;
                        for (edu.upm.midas.data.validation.metamap.model.response.Concept concept : filterText.getConcepts()) {
                            System.out.println("Concept{ cui: " + concept.getCui() + " name: " + concept.getName() + " semTypes:" + concept.getSemanticTypes().toString() + "}");
                            symptomHelperNative.insertIfExist(concept, filterText.getId());//text.getId()
                            countSymptoms++;
                        }
                }else{noinsertados++;
                    System.out.println(count + ". to (" + textList.size() + ") Ya insertado textId: " + filterText.getId());
                }
                count++;
                //if (count==18000) break;
            }
            System.out.println("Insert symptoms ready!...");
            //</editor-fold>

            System.out.println("Insert configuration...");
            String configurationJson = gson.toJson(request.getConfiguration());
            configurationHelper.insert(Constants.SOURCE_WIKIPEDIA, version, constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
            System.out.println("Insert configuration ready!...");
            System.out.println("insertados: " + insertados + " noinsertados: " + noinsertados);

        }

    }

    public boolean contains(final List<Text> texts, String textId){
        return texts.stream()
                .filter(o -> o.getId().trim() != null)
                .filter(o -> o.getId().trim().contentEquals(textId.trim()))
                .findFirst()
                .isPresent();
                //return texts.stream().anyMatch(o -> Objects.equals(o.getId().trim(), textId.trim()));
        /*boolean res = false;
        for (Text text: texts) {
            if (text.getId().trim().equals(textId.trim())){
                //System.out.println(text.getId() +"=="+textId.trim());
                res = true; break;}
        }
        return res;*/
    }


    public void createMySQLInserts(Consult consult) throws Exception {

        List<SemanticType> semanticTypes = new ArrayList<>();
        List<Symptom> symptoms = new ArrayList<>();
        List<HasSymptom> hasSymptoms = new ArrayList<>();

        String fileName = consult.getVersion() + "_inserts_has_symptom.txt";
        String fileNameSemType = consult.getVersion() + "_inserts_semantic_types.txt";
        String fileNameSymptoms = consult.getVersion() + "_inserts_symptoms.txt";
        String path = Constants.EXTRACTION_HISTORY_FOLDER + fileName;
        String pathSemTypes = Constants.EXTRACTION_HISTORY_FOLDER + fileNameSemType;
        String pathSymptoms = Constants.EXTRACTION_HISTORY_FOLDER + fileNameSymptoms;
        FileWriter fileWriter = new FileWriter(path);
        FileWriter fileWriterSemTypes = new FileWriter(pathSemTypes);
        FileWriter fileWriterSymptoms = new FileWriter(pathSymptoms);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //Metamap configuración
        Configuration metamapConf = new Configuration();
        metamapConf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        metamapConf.setSources(sources);
        metamapConf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);
        metamapConf.setConcept_location(true);

        List<edu.upm.midas.data.validation.metamap.model.response.Text> textList = readMetamapResponseJSON(consult);
        System.out.println("Read JSON ready!");
        String has_symptoms_inserts = "";

        try {
            int textCount = 1, conceptCount = 1;
            for (edu.upm.midas.data.validation.metamap.model.response.Text metamapText : textList) {
                System.out.println(textCount + ". to " + textList.size() + " TextId: " + metamapText.getId());
                //Validar que haya conceptos
                if (metamapText.getConcepts() != null) {
                    //Al menos un concepto
                    if (metamapText.getConcepts().size() > 0) {
                        //List<Concept> conceptsAux = metamapText.getConcepts();
                        List<Concept> noRepeatedConcepts = removeRepetedConcepts(metamapText.getConcepts());
                        conceptCount = createHasSymptom(metamapText.getConcepts(), noRepeatedConcepts, hasSymptoms, metamapText.getId(), conceptCount, fileWriter);
                        for (Concept concept : metamapText.getConcepts()) {
                            //Se crea un sintoma
                            Symptom symptom = new Symptom(concept.getCui(), concept.getName(), concept.getSemanticTypes());
                            //Se agrega a la lista
                            symptoms.add(symptom);

                            //Se recorren los semantic types del concepto
                            for (String semanticType : concept.getSemanticTypes()) {
                                SemanticType semType = new SemanticType(semanticType);
                                //Se crea la lista de semantic types
                                semanticTypes.add(semType);
                            }
                            //Se elimina el elemento de la lista principal para no contarlo y no agregarlo al hacer merge
                            //metamapText.getConcepts().remove(concept);
                        }
                        //if (textCount == 50) break;
                        textCount++;
                    }
                }
            }
            fileWriter.close();
        }catch (Exception e){
            System.out.println("Mensaje de la excepción 2: " + e.getMessage());
        }

        //Eliminar repetidos
        //Tipos semanticos <<formar los insert para insertar semantics types "semantic_type">>
        System.out.println("SemanticTypes repetidos size: " + semanticTypes.size());
        semanticTypes = removeRepetedSemanticTypes(semanticTypes);
        System.out.println("SemanticTypes sin repetir size: " + semanticTypes.size());
        //formar inserts
        try {
            for (SemanticType semanticType : semanticTypes) {
                //INSERT IGNORE INTO symptom (cui, name) VALUES ('C0231418', "At risk for violence");INSERT IGNORE INTO has_semantic_type (cui, semantic_type) VALUES ('C0231418', 'fndg');
                fileWriterSemTypes.write("INSERT IGNORE INTO semantic_type (semantic_type, description) VALUES ('"+semanticType.getType()+"', '');\n");
            }
            fileWriterSemTypes.close();
        }catch (Exception e){
            System.out.println("Mensaje de la excepción 4: " + e.getMessage());
        }

        //Sintomas <<formar los insert para insertar sintomas "symptom" y sus tipos semanticos "has_semantic_type">>
        System.out.println("symptoms repetidos size: " + symptoms.size());
        symptoms = removeRepetedSymptoms(symptoms);
        System.out.println("symptoms sin repetir size: " + symptoms.size());
        //formar inserts para los sintomas y sus tipos semanticos
        try {
            for (Symptom symptom : symptoms) {
                fileWriterSymptoms.write("INSERT IGNORE INTO symptom (cui, name) VALUES ('"+symptom.getCui()+"', \""+symptom.getName()+"\");\n");
                for (String semType: symptom.getSemanticTypes()) {
                    fileWriterSymptoms.write("INSERT IGNORE INTO has_semantic_type (cui, semantic_type) VALUES ('"+symptom.getCui()+"', '"+semType+"');\n");
                }
            }
            fileWriterSymptoms.close();
        }catch (Exception e){
            System.out.println("Mensaje de la excepción 5: " + e.getMessage());
        }

        //HasSymptoms resultado del proceso de metamap en la tabla "has_symptom"
        System.out.println("has_symptoms size: " + hasSymptoms.size());


        //insertar configuración
        System.out.println("Insert configuration...");
        String configurationJson = gson.toJson(metamapConf);
        configurationHelper.insert(consult.getSource(), consult.getDate(), constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
        System.out.println("Insert configuration ready!...");

    }


    public List<Concept> removeRepetedConcepts(List<Concept> elements){
        //Se crea esta lista para no afectar a la original
        List<Concept> elements_2 = new ArrayList<>();
        elements_2.addAll(elements);
        List<Concept> resList = elements_2;
        Set<Concept> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(elements_2);
        elements_2.clear();
        elements_2.addAll(linkedHashSet);

        return resList;
    }

    public List<Symptom> removeRepetedSymptoms(List<Symptom> elements){
        List<Symptom> resList = elements;
        Set<Symptom> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(elements);
        elements.clear();
        elements.addAll(linkedHashSet);

        return resList;
    }

    public List<SemanticType> removeRepetedSemanticTypes(List<SemanticType> elements){
        List<SemanticType> resList = elements;
        Set<SemanticType> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(elements);
        elements.clear();
        elements.addAll(linkedHashSet);

        return resList;
    }

    //Para hacer inserta más rápidos noRepeatedConcepts
    public int createHasSymptom(List<Concept> concepts, List<Concept> noRepeatedConcepts, List<HasSymptom> hasSymptoms, String textId, int conceptCount, FileWriter fileWriter){
        //System.out.println("concepts: " + concepts.size() + " noRepetead: " + noRepeatedConcepts.size());
        try {
            for (Concept uniqueConcept : noRepeatedConcepts) {
                HasSymptom hasSymptom = new HasSymptom(textId, uniqueConcept.getCui(), (byte) 0);
                //System.out.println("ConceptUnique: " + uniqueConcept.getCui());
                final int[] count = {1};
                concepts.stream().filter(o -> o.getCui().equals(uniqueConcept.getCui())).forEach(
                        o -> {
                            String matchedWords_ = "";
                            String positionalInfo_ = "";
                            if (count[0] == 1) {
                                matchedWords_ = o.getMatchedWords().toString();
                                positionalInfo_ = o.getPositionalInfo();
                            } else {
                                matchedWords_ = hasSymptom.getMatchedWords() + "&" + o.getMatchedWords().toString();
                                positionalInfo_ = hasSymptom.getPositionalInfo() + "&" + o.getPositionalInfo();
                            }
                            hasSymptom.setMatchedWords(matchedWords_);
                            hasSymptom.setPositionalInfo(positionalInfo_);

                            //System.out.println("    " + count + ". concept: " + o.getCui() + " = match: " + o.getMatchedWords().toString());

                            count[0]++;
                        }
                );
                //
                hasSymptoms.add(hasSymptom);
                fileWriter.write("INSERT IGNORE INTO has_symptom (text_id, cui, validated, matched_words, positional_info) VALUES ('"+hasSymptom.getTextId()+"', '"+hasSymptom.getCui()+"', 0, \""+hasSymptom.getMatchedWords()+"\", \""+hasSymptom.getPositionalInfo()+"\");\n");
                System.out.println(conceptCount + ". " +hasSymptom);
                conceptCount++;
            }
        }catch (Exception e){
            System.out.println("Mensaje de la excepción 2: " + e.getMessage());
        }

        return conceptCount;

    }


    public boolean containsProcess(final List<Text> texts, String textId){
        return texts.stream()
                .filter(o -> o.getId().trim() != null)
                .filter(o -> o.getId().trim().contentEquals(textId.trim()))
                .findFirst()
                .isPresent();
    }


    public List<edu.upm.midas.data.validation.metamap.model.response.Text> readMetamapResponseJSON(Consult consult) throws Exception {
        List<edu.upm.midas.data.validation.metamap.model.response.Text> texts = new ArrayList<>();
        System.out.println("Read JSON!...");
        Gson gson = new Gson();
        String fileName = consult.getVersion() + "_metamap_filter.json";//adis = disease album
        String path = Constants.EXTRACTION_HISTORY_FOLDER + fileName;

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            ProcessedText resp = gson.fromJson(br, ProcessedText.class);
            texts = resp.getTexts();
        }catch (Exception e){
            System.out.println("Error to read or convert JSON!...");
        }

        /*for (edu.upm.midas.data.validation.metamap.model.response.Text text: resp.getTexts()) {
            System.out.println("TextId: " + text.getId() + " | Concepts: " + text.getConcepts().toString());
        }*/

        return texts;
    }


    public void writeJSONFile(String diseaseJsonBody, String version) throws IOException {
        String fileName = version + "_metamap_filter.json";//adis = disease album
        String path = Constants.EXTRACTION_HISTORY_FOLDER + fileName;
        InputStream in = getClass().getResourceAsStream(path);
        //BufferedReader bL = new BufferedReader(new InputStreamReader(in));
        File file = new File(path);
        BufferedWriter bW;

        if (!file.exists()){
            bW = new BufferedWriter(new FileWriter(file));
            bW.write(diseaseJsonBody);
            bW.close();
        }
    }





    /**
     * @param consult
     */
    @Transactional
    public void filterDiseaseName(Consult consult){
        Request request = new Request();
        Configuration conf = new Configuration();
        List<Text> texts = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        conf.setOptions("-y -R");
        List<String> sources = new ArrayList<>();
        sources.add("SNOMEDCT_US");
        conf.setSources(sources);
        conf.setSemanticTypes(Constants.SEMANTIC_TYPES_LIST);

        request.setConfiguration( conf );

        List<Object[]> diseases = diseaseService.findAllBySourceAndVersionNative(consult.getSource(), consult.getDate());
        if (diseases != null) {
            for (Object[] disease : diseases) {
                Text text = new Text();
                text.setId((String) disease[0]);
                text.setText((String) disease[1]);
                texts.add(text);
                System.out.println((String) disease[1]);
            }
        }
        request.setTextList(texts);

        System.out.println( "Connection_ with METAMAP API..." );
        System.out.println( "Founding medical concepts in a texts... please wait, this process can take from minutes to hours... " );
        Response response = metamapResourceService.filterDiseaseName( request );

        System.out.println( gson.toJson( response ) );
        //response.getConfiguration().toString();
        int count = 1;
        for (edu.upm.midas.data.validation.metamap.model.response.Text text:
             response.getTextList()) {
            System.out.println(String.format("%06d", count));
            System.out.println("TEXT_ID: " + text.getId() + " | CONCEPTS("+text.getConcepts().size()+"): " + text.getConcepts());
            count++;
        }

        String json = gson.toJson( response.getConfiguration() );
        //System.out.println(json);
        System.out.println(utilDate.getTimestampNumber());
        System.out.println(utilDate.getTime());

    }



}
