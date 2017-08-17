package edu.upm.midas.data.validation.tvp.tvpApiResponse;

import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by gerardo on 08/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpResource
 * @see
 */
@Headers("Accept: application/json")
public interface TvpResource {

    @Headers("Content-Type: application/json")
    @RequestLine("POST /validation")
    List<MatchNLP> getValidateSymptoms(List<Concept> concepts);

}
