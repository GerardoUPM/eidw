package edu.upm.midas.data.extraction.service;

import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.model.Connect;
import edu.upm.midas.enums.StatusHttpEnum;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by gerardo on 29/3/17.
 * @project ExtractionInformationWikipedia
 * @version ${<VERSION>}
 * @author Gerardo Lagunes G.
 * @className ConnectDisease
 * @see
 */
@Component
public class ConnectDocument {

    private Connect oConnect = new Connect();
    /**
     * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
     * EJM:
     * 		200 OK			300 Multiple Choices
     * 		301 Moved Permanently	305 Use Proxy
     * 		400 Bad Request		403 Forbidden
     * 		404 Not Found		500 Internal Server Error
     * 		502 Bad Gateway		503 Service Unavailable
     * @paramFromObject getLink()
     * @return Status Code
     *//*REGRESAR UN OBJETO CONNECTOBJECTRESPONSE*/
    public Connect connect(String link) throws Exception {
        //Response oResponse; ya no se usa
        oConnect.setLink( link );
        oConnect.setsStatusEnum( StatusHttpEnum.NOT_FOUND );

        try {
            Connection connection = Jsoup.connect(Constants.HTTP_HEADER + oConnect.getLink() );//oResponse = connection.execute();
            oConnect.setStatus( connection.execute().statusMessage() );
            oConnect.setoDoc( getHtmlDocument(connection) );
        } catch (IOException ex) {
            System.out.println("Exception to obtain el Status Code: " + ex.getMessage() + " " +ex.getCause() + " " + Constants.HTTP_HEADER + oConnect.getLink());
        }

        return oConnect;
    }

    /**
     * Este método devuelve un objeto de la clase Doc con el contenido del
     * HTML de la página web y así manipularlo con los métodos de la biblioteca JSoup
     * @paramFromObject getLink()
     * @return Documento HTML
     */
    public Document getHtmlDocument(Connection connection) throws Exception {
        Document oDoc = null;

        try {
            oDoc = connection.get();
        } catch (IOException ex) {
            System.out.println("Exception to obtain HTML document (" + oConnect.getLink() + ")" + ex.getMessage());
        }

        return oDoc;
    }

}
