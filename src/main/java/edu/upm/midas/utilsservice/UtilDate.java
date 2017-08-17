package edu.upm.midas.utilsservice;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * Created by gerardo on 04/05/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className CurrentDate
 * @see
 */
@Service("date")
public class UtilDate {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public Date getSqlDate(){
        return new Date(117, 05, 29);
        //return new Date(new java.util.Date().getTime());
    }

    public Timestamp getSqlTimestamp(){
        return new Timestamp(new java.util.Date().getTime());
    }

}
