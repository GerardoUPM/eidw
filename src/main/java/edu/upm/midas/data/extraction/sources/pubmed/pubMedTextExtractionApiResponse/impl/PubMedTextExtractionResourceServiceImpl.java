package edu.upm.midas.data.extraction.sources.pubmed.pubMedTextExtractionApiResponse.impl;

import edu.upm.midas.data.extraction.sources.pubmed.client.PubMedTextExtractionClient;
import edu.upm.midas.data.extraction.sources.pubmed.model.Request;
import edu.upm.midas.data.extraction.sources.pubmed.model.Response;
import edu.upm.midas.data.extraction.sources.pubmed.pubMedTextExtractionApiResponse.PubMedTextExtractionResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edsss
 * @className PubMedTextExtractionResourceServiceImpl
 * @see
 */
@Service
public class PubMedTextExtractionResourceServiceImpl implements PubMedTextExtractionResourceService {

    @Autowired
    private PubMedTextExtractionClient pubMedTextExtractionClient;

    @Override
    public Response getPubMedTexts(Request request) {
        return pubMedTextExtractionClient.getPubMedTexts( request );
    }

    @Override
    public Response getPubMedTextsByJSON(Request request) {
        return pubMedTextExtractionClient.getPubMedTextsByJSON(request);
    }
}
