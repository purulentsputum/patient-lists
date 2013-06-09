

package inpatientlists;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

/**
 * 
 * 
 * @author ross sellars
 * @created 24/05/2013 12:30
 */
public class MyDates {
    
    public static final String DefaultTimeFormat = "HH:mm";
    public static final String DefaultDateFormat = "dd-MMM-yyyy";
    public static final String DefaultDateTimeFormat = DefaultDateFormat + " " + DefaultTimeFormat;


    public static String JavaDateToSQLasString(java.util.Date nDate) {
        /*
         * converts standard java date to string for insertion in MySQL
         * use this wifh rs.setString() and SQL statements
         */

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(nDate);

    }
    
    public static java.sql.Date JavaDateToSQLasDate (java.util.Date nDate) {
        java.sql.Date  RetVar;
        java.util.Calendar cal = Calendar.getInstance();

        if(nDate != null){
           cal.setTime(nDate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            RetVar = new java.sql.Date(cal.getTime().getTime()); // your sql date 
        }else{
            RetVar = null;
        }
        
        return RetVar;
    }
    public static java.sql.Timestamp JavaDateTimeToSQLasDate (java.util.Date nDateTime) {
        //java.sql.Timestamp  RetVar;
        //java.util.Calendar cal = Calendar.getInstance();

        //cal.setTime(nDateTime);
        
        //RetVar = new java.sql.Timestamp(cal.getTime().getTime()); // your sql date
        //maybe could be achieved with 
        java.sql.Timestamp RetVar = new java.sql.Timestamp(nDateTime.getTime());
        return RetVar;
    }
    public static java.sql.Timestamp CurrentTimestamp(){
        return JavaDateTimeToSQLasDate( CurrentDateTime());
    } 
    
    static Date ConvertStringToDate(String date,String inFormat){
        SimpleDateFormat df = new SimpleDateFormat(inFormat);
        Date dt=null;
        try
         {
	  dt= df.parse(date);
         } catch (ParseException e)
         {
        //handle exception
        }
        return dt;
    }
    static Date ConvertStringToDate(String date){
        SimpleDateFormat df = new SimpleDateFormat(DefaultDateFormat);
        Date dt=null;
        try
         {
	  dt= df.parse(date);
         } catch (ParseException e)
         {
        //handle exception
        }
        return dt;
    }
    
    static Date ConvertStringToTime(String nTime,String inFormat){
        SimpleDateFormat df = new SimpleDateFormat(inFormat);
        Date dt=null;
        try
         {
	  dt= df.parse(nTime);
         } catch (ParseException e)
         {
        //handle exception
        }
        return dt;
    }
    
    static Date ConvertStringToTime(String nTime){
        SimpleDateFormat df = new SimpleDateFormat(DefaultTimeFormat);
        Date dt=null;
        try
         {
	  dt= df.parse(nTime);
         } catch (ParseException e)
         {
        //handle exception
        }
        return dt;
    }
    static Date ConvertStringToDateTime(String nDate, String nTime){
        Date RetVar;
        if (ValidateDate(nDate, DefaultDateFormat)){
            // valid date
            if (ValidateTime(nTime)){
                // valid date and time
                RetVar = ConvertStringToDateTime(nDate + " "  + nTime);
            }else{
                // valid date, not time
                RetVar = ConvertStringToDate(nDate);
            }
        }else{
            // not valid date
            if (ValidateTime(nTime)){
                // not valid date, valid time
                RetVar = ConvertStringToTime(nTime);
            }else{
                // both invalid
                RetVar = null;
            }
        }        
        
        return RetVar;
    }
    
        public static Boolean ValidateDate(String strDate, String strFormat){
        /**
         * check that it is a date entered
         * @return returns true is date in selected format
         */
         

        Boolean RetVar = false;
        try {
            DateFormat df;
            df = new SimpleDateFormat(strFormat);

            Date dateTemp = (Date) df.parse(strDate);
            RetVar = true;

        } catch (ParseException e) {

            RetVar = false;
        }

        return RetVar;
    }


    static boolean ValidateTime(String strString) {
        /** 
         *   check that it is a date entered
         *   @return returns true is date in selected format
         */
        boolean RetVar = false;
        // TODO fix this one  


        try {
            SimpleDateFormat mydateformat = new SimpleDateFormat ("HH:mm");
            Date dateStr = mydateformat.parse(strString);
            RetVar = true;

        } catch (ParseException ex1) {
            try{
               SimpleDateFormat mydateformat = new SimpleDateFormat ("hh:mm a");  // may need hh 
               Date dateStr = mydateformat.parse(strString);
               RetVar = true;

            } catch (ParseException ex2) {
                RetVar = false;
            }
        }
        
        return RetVar;
        
                
    }
    
    static Date ConvertStringToDateTime(String nDateTime){
        SimpleDateFormat df = new SimpleDateFormat(DefaultDateTimeFormat);
        Date dt=null;
        try
         {
	  dt= df.parse(nDateTime);
         } catch (ParseException e)
         {
        //handle exception
        }
        return dt;
    }
    
    static String ConvertDateToString(java.util.Date dt){
        /**
         * returns the date portion of date dt as a string
         */
        String RetVar;
        try {
            SimpleDateFormat formatter=new SimpleDateFormat(DefaultDateFormat);
            RetVar =formatter.format(dt);
        } catch (Exception e) {
            RetVar = "";
    }

    return RetVar;
 }
    
    static String ConvertTimeToString(java.util.Date dt){
        /**
         * returns the time portion of date dt as a string
         */
        String RetVar;
        try {
            SimpleDateFormat formatter=new SimpleDateFormat(DefaultTimeFormat);
            RetVar =formatter.format(dt);
        } catch (Exception e) {
            RetVar = "";
    }

    return RetVar;
 }
    public static String ConvertDateTimeToString(java.util.Date dt){
        /**
         * returns the full  portion of date dt as a string
         */
        String RetVar;
        try {
            SimpleDateFormat formatter=new SimpleDateFormat(DefaultDateTimeFormat);
            RetVar =formatter.format(dt);
        } catch (Exception e) {
            RetVar = "";
    }

    return RetVar;
 }
    public static String ConvertDateTimeToString(java.util.Date dt, String dtFormat){
        /**
         * returns the full  portion of date dt as a string
         */
        String RetVar;
        try {
            SimpleDateFormat formatter=new SimpleDateFormat(dtFormat);
            RetVar =formatter.format(dt);
        } catch (Exception e) {
            RetVar = "";
    }

    return RetVar;
 }
    
    
    
 static Date incrementDate(Date nDate, double nNumIntervals, double IntervalsPerDay){
        // this returns a date that is incremented by the selected amount
        Date retVar;
        
        //get start date in millisecs
        long startDate = nDate.getTime();
        
        //get days to add
        double daysToIncrement = nNumIntervals / IntervalsPerDay;
        double milisecsInaDay = (double)24*60*60*1000;
        double milisecToIncrement = daysToIncrement * milisecsInaDay;
        long endDate = startDate + (long)milisecToIncrement;
        retVar = new Date(endDate);
           
        return retVar;
       
    }   
    
    
    

    public static java.util.Date CurrentDate() {
        java.util.Calendar dateNow = Calendar.getInstance();
        dateNow.set(Calendar.HOUR_OF_DAY, 0);
        dateNow.set(Calendar.MINUTE, 0);
        dateNow.set(Calendar.SECOND, 0);
        dateNow.set(Calendar.MILLISECOND, 0);
        return dateNow.getTime();
    }

    public static java.util.Date CurrentDateTime() {
        java.util.Calendar dateNow = Calendar.getInstance();
        dateNow.set(Calendar.SECOND, 0);
        dateNow.set(Calendar.MILLISECOND, 0);
        return dateNow.getTime();
    }

}
