package edu.upm.midas.data.validation.tvp.tvpApiResponse;

import edu.upm.midas.data.validation.tvp.model.request.Request;
import edu.upm.midas.data.validation.tvp.model.response.Response;

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

    Response getValidateSymptoms(Request request);

}
