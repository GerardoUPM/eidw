package edu.upm.midas.data.extraction.album.client.fallback;
import edu.upm.midas.data.extraction.album.client.DiseaseAlbumClient;
import edu.upm.midas.data.extraction.album.model.request.RequestFather;
import edu.upm.midas.data.extraction.album.model.request.RequestGDLL;
import edu.upm.midas.data.extraction.album.model.response.ResponseGDLL;
import edu.upm.midas.data.extraction.album.model.response.ResponseLA;
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
public class DiseaseAlbumClientFallback implements DiseaseAlbumClient {

    @Override
    public ResponseLA getDiseaseAlbum(RequestFather request) {
        return new ResponseLA();
    }

    @Override
    public ResponseGDLL getDiseaseLinkList(RequestGDLL request) {
        return new ResponseGDLL();
    }
}

