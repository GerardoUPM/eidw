package edu.upm.midas.controller;

import edu.upm.midas.data.validation.metamap.service.MetamapService;
import edu.upm.midas.data.validation.model.Consult;
import edu.upm.midas.data.validation.tvp.service.TvpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gerardo on 05/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project validation_medical_term
 * @className ValidationController
 * @see
 */
@RestController
@RequestMapping("/api")
public class ValidationController {

    @Autowired
    private MetamapService metamapService;
    @Autowired
    private TvpService tvpService;

    @RequestMapping(path = { "/metamap" }, //Term Validation Procedure
            method = RequestMethod.GET)
    public String metamapFilter() throws Exception {

        Consult consult = new Consult("wikipedia",
                "2017-06-26");

        metamapService.filter( consult );

        return "Algo pasa";
    }

    @RequestMapping(path = { "/tvp" }, //Term Validation Procedure
            method = RequestMethod.GET)
    public String tvpValidation() throws Exception {

        Consult consult = new Consult("wikipedia",
                "2017-06-26");

        tvpService.validation( consult );

        return "Algo pasa TVP";
    }

}
