package edu.upm.midas.data.extraction.sources.pubmed.client.fallback;

import edu.upm.midas.data.extraction.sources.pubmed.client.PubMedTextExtractionClient;
import edu.upm.midas.data.extraction.sources.pubmed.model.Request;
import edu.upm.midas.data.extraction.sources.pubmed.model.Response;
import org.springframework.stereotype.Component;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpClientFallback
 * @see
 */
@Component
public class PubMedTextExtractionClientFallback implements PubMedTextExtractionClient {

    @Override
    public Response getPubMedTexts(Request request) {
        return new Response();
    }

    @Override
    public Response getPubMedTextsByJSON(Request request) {
        return new Response();
    }
}

