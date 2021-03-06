package edu.upm.midas.data.relational.service.helperNative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.text.*;
import edu.upm.midas.data.relational.service.HasTextService;
import edu.upm.midas.data.relational.service.TextService;
import edu.upm.midas.enums.ContentType;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className TextHelper
 * @see
 */
@Service
public class TextHelperNative {

    @Autowired
    private TextService textService;
    @Autowired
    private HasTextService hasTextService;

    @Autowired
    private DocumentHelperNative documentHelperNative;
    @Autowired
    private UrlHelperNative urlHelperNative;

    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(TextHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;


    /**
     * @param text
     * @param sectionId
     * @param documentId
     * @param version
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public String insert(Text text, String sectionId, String documentId, Date version, String paperId) throws JsonProcessingException {
        //System.out.println(text+" - "+ sectionId+" - "+ documentId +" - "+ version);
        String textId = getTextId( documentId, version, sectionId, text.getId(), paperId);
        String text_;
        //System.out.println();

        if(text instanceof Paragraph){
            //System.out.println(((Paragraph) text).getTitle() + " =>" + ((Paragraph) text).getText());
            text_ = (!(text.getTitle().equals("")) ? (text.getTitle() + " => " + ((Paragraph) text).getText()) : ((Paragraph) text).getText());
            textService.insertNative(textId, ContentType.PARA.getClave(), text_.trim());
        }else if (text instanceof List_){
            //System.out.println(((List_) text).getBulletList().toString());
            String textList = "";int bulletCount = 1;
            List<String> bulletList = ( (List_) text).getBulletList();
            for (String bullet: bulletList ) {
                if (bulletCount == bulletList.size())
                    textList += bullet;
                else
                    textList += bullet + "&";
                bulletCount++;
            }
            /*if (!textList.equals(""))
                textList = common.cutStringPerformance(0, 1, textList);*/
            text_ = (!text.getTitle().equals(""))?text.getTitle() + " => "+ textList:textList;
            textService.insertNative( textId, ContentType.LIST.getClave(), text_.trim() );
        } else if (text instanceof Table){
            //System.out.println(((Table) text).getTrList().toString());
            String textList = "";
            List<Tr> trList = ( (Table) text ).getTrList();
            for (Tr oTr: trList) {
                textList += oTr.getTd() + " ";
            }
            text_ = (!text.getTitle().equals(""))?text.getTitle() + " => "+ textList:textList;
            textService.insertNative( textId, ContentType.TABLE.getClave(), text_.trim() );
        }

        //<editor-fold desc="INSERTAR URLS">
        if (text.getUrlList()!=null) {
            List<String> urlList = urlHelperNative.getUrl(text.getUrlList(), textId);
            for (String urlId :
                    urlList) {
                textService.insertNativeUrl(textId, urlId);
            }
        }
        //</editor-fold>
        //System.out.println(documentId+"|"+ version+"|"+ sectionId+"|"+ textId +"|"+ text.getTextOrder());
        hasTextService.insertNative( documentId, version, sectionId, textId, text.getTextOrder() );

        return textId;

    }


    /**
     * @param text
     * @param sectionId
     * @param documentId
     * @param version
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public String insertPubMedTextArticles(Text text, String sectionId, String documentId, Date version, String paperId) throws JsonProcessingException {
        //System.out.println(text+" - "+ sectionId+" - "+ documentId +" - "+ version);
        String textId = getTextId( documentId, version, sectionId, text.getId(), paperId);
        String text_;
        //System.out.println();

        if(text instanceof Paragraph || text instanceof Text){
            if (text instanceof Paragraph) {
                //System.out.println(((Paragraph) text).getTitle() + " =>" + ((Paragraph) text).getText());
                text_ = (!(text.getTitle().equals("")) ? (text.getTitle() + " => " + ((Paragraph) text).getText()) : ((Paragraph) text).getText());
                textService.insertNative(textId, ContentType.PARA.getClave(), text_.trim());
            }else{
                //System.out.println((text.getTitle() + " =>" + text.getText()));
                text_ = text.getText();
                textService.insertNative(textId, ContentType.PARA.getClave(), text_.trim());
            }
        }

        //<editor-fold desc="INSERTAR URLS">
        if (text.getUrlList()!=null) {
            List<String> urlList = urlHelperNative.getUrl(text.getUrlList(), textId);
            for (String urlId :
                    urlList) {
                textService.insertNativeUrl(textId, urlId);
            }
        }
        //</editor-fold>
        //System.out.println(documentId+"|"+ version+"|"+ sectionId+"|"+ textId +"|"+ text.getTextOrder());
        hasTextService.insertNative( documentId, version, sectionId, textId, text.getTextOrder() );

        return textId;

    }


    /**
     * @param documentId
     * @param version
     * @param sectionId
     * @param textId
     * @return
     */
    public String getTextId(String documentId, Date version, String sectionId, int textId, String paperId) {
        String docId = documentHelperNative.getDocumentId( documentId, version );
        if (!common.isEmpty(paperId)){
            return uniqueId.generateTextFromPaperAbstract(docId, sectionId, textId, paperId);
        }else {
            return uniqueId.generateText(docId, sectionId, textId);
        }
    }

}
