package edu.upm.midas.controller;

import edu.upm.midas.data.validation.metamap.service.MetamapService;
import edu.upm.midas.data.validation.model.Consult;
import edu.upm.midas.data.validation.tvp.service.TvpService;
import edu.upm.midas.utilsservice.UtilDate;
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
    @Autowired
    private UtilDate utilDate;

    @RequestMapping(path = { "/metamap" }, //Term Validation Procedure
            method = RequestMethod.GET)
    public String metamapFilter() throws Exception {

        Consult consult = new Consult("wikipedia",
                "2017-12-15");

        String inicio = utilDate.getTime();
        metamapService.localFilter( consult );
        System.out.println("Inicio:" + inicio + " | Termino: " +utilDate.getTime());

        return "It has been successfully filtered with Metamap";
    }


    @RequestMapping(path = { "/metamap/diseases" }, //Term Validation Procedure
            method = RequestMethod.GET)
    public String metamapFilterDiseasesName() throws Exception {

        Consult consult = new Consult("wikipedia",
                "2017-09-03");

        metamapService.filterDiseaseName( consult );

        return "It has been successfully filtered DISEASES NAME with Metamap";
    }


    @RequestMapping(path = { "/tvp" }, //Term Validation Procedure
            method = RequestMethod.GET)
    public String tvpValidation() throws Exception {

        Consult consult = new Consult("wikipedia",
                "2017-12-15");

        String inicio = utilDate.getTime();
        tvpService.validation( consult );
        System.out.println("Inicio:" + inicio + " | Termino: " +utilDate.getTime());

        return "It has been validated filtered with TVP";
    }

}
