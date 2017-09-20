package edu.upm.midas.data.validation.metamap.metamapApiResponse;
import edu.upm.midas.data.validation.metamap.model.receiver.Request;
import edu.upm.midas.data.validation.metamap.model.response.Response;

/**
 * Created by gerardo on 31/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className MetamapResourceService
 * @see
 */
public interface MetamapResourceService {

    Response filterDiseaseName(Request request);

    Response filterTexts(Request request);
}
