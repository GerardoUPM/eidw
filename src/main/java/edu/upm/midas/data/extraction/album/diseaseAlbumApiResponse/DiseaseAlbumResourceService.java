package edu.upm.midas.data.extraction.album.diseaseAlbumApiResponse;
import edu.upm.midas.data.extraction.album.model.request.RequestAlbum;
import edu.upm.midas.data.extraction.album.model.request.RequestFather;
import edu.upm.midas.data.extraction.album.model.request.RequestGDLL;
import edu.upm.midas.data.extraction.album.model.response.ResponseGDLL;
import edu.upm.midas.data.extraction.album.model.response.ResponseLA;

/**
 * Created by gerardo on 02/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PubMedTextExtractionResourceService
 * @see
 */
public interface DiseaseAlbumResourceService {

    ResponseLA getDiseaseAlbum(RequestFather request);

    ResponseGDLL getDiseaseLinkList(RequestGDLL request);

    ResponseLA getSpecificAlbum(RequestAlbum request);

}
