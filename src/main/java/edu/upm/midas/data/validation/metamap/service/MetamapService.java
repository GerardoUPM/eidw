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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Transactional
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
                System.out.println("TotalSize: " + responseTexts.size() +" - CALL("+countRT+"): " + responseText.isCall());
                if (responseText.isCall()){
                    // Se agregan los textos hasta el momento
                    //System.out.println( gson.toJson( request ) );

                    //<editor-fold desc="BLOQUE QUE LLAMA Y OBTIENE RESULTADOS DE LA API">
                    request.setTextList( texts );
                    request.setToken(Constants.TOKEN);

                    //request.setTextList( texts );
                    System.out.println("textsList size is: " + texts.size() + " AND request.textList is:" + request.getTextList().size());

                    System.out.println( "Connection_ with METAMAP API..." );
                    System.out.println( "Founding medical concepts in a texts... please wait, this process can take from minutes to hours... " );
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
                                    //symptomHelperNative.insertIfExist(concept, filterText.getId());//text.getId()
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
            configurationHelper.insert(Constants.SOURCE_WIKIPEDIA, version, constants.SERVICE_METAMAP_CODE + " - " + constants.SERVICE_METAMAP_NAME, configurationJson);
            //System.out.println("Insert configuration ready!...");



        }

    }



    /**
     *
     * @param consult
     * @return
     * @throws Exception
     */

    @Transactional
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

            System.out.println( "Request: " + request);
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
                writeJSONFile(gson.toJson(processedText), utilDate.dateFormatyyyMMdd(version) /*utilDate.getNowFormatyyyyMMdd()*/);
                System.out.println("save metamap ready...");
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
