package edu.upm.midas.data.validation.tvp.tvpApiResponse.impl;

import edu.upm.midas.data.validation.tvp.client.TvpClient;
import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;
import edu.upm.midas.data.validation.tvp.tvpApiResponse.TvpResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class TvpResourceServiceImpl implements TvpResourceService {

    @Autowired
    private TvpClient tvpClient;

    @Override
    public List<MatchNLP> getValidateSymptoms(List<Concept> concepts) {
        return tvpClient.getValidateSymptoms( concepts );
    }
}
