package edu.upm.midas.data.extraction.album.diseaseAlbumApiResponse.impl;

import edu.upm.midas.data.extraction.album.client.DiseaseAlbumClient;
import edu.upm.midas.data.extraction.album.diseaseAlbumApiResponse.DiseaseAlbumResourceService;
import edu.upm.midas.data.extraction.album.model.request.RequestAlbum;
import edu.upm.midas.data.extraction.album.model.request.RequestFather;
import edu.upm.midas.data.extraction.album.model.request.RequestGDLL;
import edu.upm.midas.data.extraction.album.model.response.ResponseGDLL;
import edu.upm.midas.data.extraction.album.model.response.ResponseLA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpResourceServiceImpl
 * @see
 */
@Service
public class DiseaseAlbumResourceServiceImpl implements DiseaseAlbumResourceService {

    @Autowired
    private DiseaseAlbumClient diseaseAlbumClient;

    @Override
    public ResponseLA getDiseaseAlbum(RequestFather request) {
        return diseaseAlbumClient.getDiseaseAlbum( request );
    }

    @Override
    public ResponseGDLL getDiseaseLinkList(RequestGDLL request) {
        return diseaseAlbumClient.getDiseaseLinkList(request);
    }

    @Override
    public ResponseLA getSpecificAlbum(RequestAlbum request) {
        return diseaseAlbumClient.getSpecifictDiseaseAlbum(request);
    }


}
