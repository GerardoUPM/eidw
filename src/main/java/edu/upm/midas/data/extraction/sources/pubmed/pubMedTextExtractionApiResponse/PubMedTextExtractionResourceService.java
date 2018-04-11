package edu.upm.midas.data.extraction.sources.pubmed.pubMedTextExtractionApiResponse;
import edu.upm.midas.data.extraction.sources.pubmed.model.Request;
import edu.upm.midas.data.extraction.sources.pubmed.model.Response;

/**
 * Created by gerardo on 02/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PubMedTextExtractionResourceService
 * @see
 */
public interface PubMedTextExtractionResourceService {

    Response getPubMedTexts(Request request);

    Response getPubMedTextsByJSON(Request request);

}
