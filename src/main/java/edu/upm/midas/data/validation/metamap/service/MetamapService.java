package edu.upm.midas.data.validation.metamap.service;
import edu.upm.midas.data.relational.service.helperNative.SymptomHelperNative;
import edu.upm.midas.data.validation.metamap.Metamap;
import edu.upm.midas.data.validation.model.Consult;
import edu.upm.midas.data.validation.model.query.ResponseText;
import edu.upm.midas.data.validation.helper.ConsultHelper;
import edu.upm.midas.data.validation.model.Concept;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.ReplaceUTF8;
import gov.nih.nlm.nls.metamap.Ev;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private Metamap metamap;
    @Autowired
    private ReplaceUTF8 replaceUTF8;
    @Autowired
    private Common common;

    /**
     *
     * @param consult
     * @return
     * @throws Exception
     */
    @Transactional
    public void filter(Consult consult) throws Exception {
        List<ResponseText> responseTexts = consultHelper.findTextsByVersionAndSource( consult );

        int countText = 1;
        for (ResponseText text:
                responseTexts) {
            System.out.println("Analizando texto(" + countText + ") => " + text.getTextId() + " | text : " + text.getText());
            String textNonAscii = replaceUTF8.replaceLooklike( text.getText() );

            if ( !common.isEmpty( textNonAscii ) ){
                int conceptCount = 1;
                for (Ev conceptEv :
                        metamap.performNLP( textNonAscii ) ) {
                    Concept concept = new Concept();

                    concept.setId( text.getTextId() + "/" + conceptCount );
                    concept.setName( conceptEv.getConceptName() );
                    concept.setCui( conceptEv.getConceptId() );
                    concept.setSemanticTypes( conceptEv.getSemanticTypes() );

                    symptomHelperNative.insertIfExist( concept, text.getTextId() );

                    conceptCount++;
                } // busqueda de terminos en metamap
                System.out.println("    Conceptos del texto: " + conceptCount);
                //System.out.println("Text("+textCount+") | Tiempo: "+ (totalTiempo) + " ms | TextId: " + hasText.getSymptomName().getTextId() +" | No. Concepts: "+conceptCount+" | Doc: " + document.getDocumentPK().getDocumentId() + " - " + document.getDocumentPK().getDate() + " | Section: "+ hasSection.getSection().getDescription() );
            }
            countText++;
        }

    }

}
