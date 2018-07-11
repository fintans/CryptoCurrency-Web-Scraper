/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrape;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author fintan
 */


public class DateFormat {

    public static void main(String[] args) throws ParseException {

        //  String sDate6 = "31-Dec-1998 23:37:50";  
        // SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"); 
//            String date1 = "2018-06-28T06:48:00+03:00";
//            //2013-08-05T12:13:49.000Z
//           // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
//            SimpleDateFormat formatter6=new SimpleDateFormat("yyyy-MMM-dd'T'HH:mm:ss.SSS'Z'");
//            //2013-08-05T12:13:49.000Z
//            //"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
//            Date date6=formatter6.parse(date1);  
//            System.out.println("HERE" + date6);


        SimpleDateFormat formatterTest = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String test = "2018-06-28T06:48:00+03:00";
        Date dateTest = formatterTest.parse(test);
        System.out.println(dateTest);

//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//        Date result;
//        
//            result = df.parse("2018-06-28T06:48:00+03:00");
//            System.out.println("date:" + result); //prints date in current locale
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//            System.out.println(sdf.format(result)); //prints date in the format sdf
        
    }

}
