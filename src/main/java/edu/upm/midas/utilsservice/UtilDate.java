package edu.upm.midas.utilsservice;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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

    private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Date getSqlDate(){
        //return new Date(118, 4, 15);
        return new Date(new java.util.Date().getTime());
    }

    public long getTimestampNumber(){
        return new Timestamp( System.currentTimeMillis() ).getTime();
    }

    public String getTime(){
        return String.format(new java.util.Date().toString(), dtf);
    }

    public String dateFormatyyyMMdd(java.util.Date date){
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public String getNowFormatyyyyMMdd(){
        //return new Date(117, 05, 29);
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(new java.util.Date().getTime()));
    }

    public java.util.Date stringToDate(String date) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    public java.sql.Date convertSQLDateToUtilDate(java.util.Date date){
        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
        return sqlStartDate;
    }

}
