package edu.upm.midas.data.extraction.service;

import edu.upm.midas.data.extraction.model.Connection_;
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
    public Connection_ connect(String link) throws Exception {
        //Response oResponse; ya no se usa
        Connection_ connection_ = new Connection_();
        connection_.setLink( link );

        try {
            Connection connection = Jsoup.connect( connection_.getLink().replace("http", "https") ).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0").referrer("http://www.google.com").timeout(20*1000);//oResponse = connection.execute(); || Jsoup.connect(Constants.HTTP_HEADER + connection_.getLink()
            connection_.setoDoc( connection.get()/*getHtmlDocument(connection)*/ );
            connection_.setStatus( connection.execute().statusMessage() );
            connection_.setStatusCode( connection.execute().statusCode() );
        } catch (Exception e) {
            System.out.println("Exception to connect with the page: (" + connection_.getLink() + ") " + e.getMessage() + " " +e.getStackTrace());
            connection_.setStatus( StatusHttpEnum.NOT_FOUND.getDescripcion() );
            connection_.setStatusCode( StatusHttpEnum.NOT_FOUND.getClave() );
            connection_.setoDoc(null);
        }

        return connection_;
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
/*
            System.out.println("Exception to obtain HTML document (" + oConnect.getLink() + ")" + ex.getMessage());
*/
        }

        return oDoc;
    }

}
