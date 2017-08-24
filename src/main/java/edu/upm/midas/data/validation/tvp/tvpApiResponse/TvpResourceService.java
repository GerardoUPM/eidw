package edu.upm.midas.data.validation.tvp.tvpApiResponse;

import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;

import java.util.List;

/**
 * Created by gerardo on 08/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpResourceService
 * @see
 */
public interface TvpResourceService {

    List<MatchNLP> getValidateSymptoms(List<Concept> concepts);

}
