

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rosssellars
 * @created 20/05/2013 22:41
 */
public class Utilities {
    
    
    public static String[] loadWards(){
        /**
         * loads wards
         */
         String strSQL;
         String[] retVar = new String[0];
                  
         strSQL = "SELECT tbl_004_WardList.* " +
                "FROM tbl_004_WardList " +
                "ORDER BY Tbl_004_WardList.Ward;"; 
                
         try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new String[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                   retVar[LoopVar]  = rs.getString("Ward");                    
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadWards").log(Level.SEVERE, null, ex);
            }
            return retVar;
    }
    
    public static String[] loadUnits(){
        /**
         * loads wards
         * 
         */
         String strSQL;
         String[] retVar = new String[0];
                  
         strSQL = "SELECT tbl_003_UnitList.* " +
                "FROM tbl_003_UnitList " +
                "ORDER BY Tbl_003_UnitList.Unit;"; 
                
         try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new String[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                   retVar[LoopVar]  = rs.getString("Unit");                    
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadUnits").log(Level.SEVERE, null, ex);
            }
            return retVar;
    }
    
    
    public static java.sql.Date JavaDateToSQLasDate (Date nDate) {
        java.sql.Date  RetVar;
        java.util.Calendar cal = Calendar.getInstance();

        cal.setTime(nDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        RetVar = new java.sql.Date(cal.getTime().getTime()); // your sql date
        return RetVar;
    }
    static String RemoveNonAlpha (String strInput) {
        /**
         * this removes any character that is not A-Z, a-z or 0-9
         * from a string and converts it to a non-threatening
         * set of & unicode ;
         */
        String strItem = "";
        String strResult = "";
        int intUnicode = 0;

         if(strInput!=null){
            for (int i=0;i<strInput.length();i++) {
                strItem = strInput.substring (i,i+1);
                if ((strItem.compareTo("A") >=0) && (strItem.compareTo("Z") <=0)) {
                    // A to Z
                    strResult = strResult + strItem;
                } else if ((strItem.compareTo("a") >=0) && (strItem.compareTo("z") <=0)){
                    // a to z
                    strResult = strResult + strItem;
                } else if ((strItem.compareTo("0") >=0) && (strItem.compareTo("9") <=0)) {
                    // 0 to 9
                    strResult = strResult + strItem;
                } else if ((strItem.compareTo(" ") == 0) || (strItem.compareTo(".") == 0)){
                    //  space or period
                    strResult = strResult + strItem;
                } else {
                    // all others - convert to something non-threatening
                    intUnicode = strItem.codePointAt(0);
                    strResult = strResult + "&"+ Integer.toString(intUnicode) + ";";
                }
             } //next i
         }
        return strResult;
    }

    static String ReplaceNonAlpha(String strInput) {
          /**
         * this removes any character that is not A_Z, a-z or 0-9
         * from a string and converts it to a non-threatening
         * set of & unicode ;
         */
        String strItem = "";
        String strResult = "";
        String strSegment = "";
        int intUnicode = 0;
        int intLoopVar = 0;
        if(strInput!=null){
            while (intLoopVar < strInput.length()) {
                strItem = strInput.substring (intLoopVar,intLoopVar+1);
                if (strItem.compareTo("&") == 0) {
                    // start of non-alpha character unicode
                    strSegment = "";
                    do {
                        intLoopVar ++;
                        if (intLoopVar >= strInput.length()) {
                            //reached the end - problem here so drop the character
                            strSegment = "";
                            break;
                        }
                        strItem = strInput.substring (intLoopVar,intLoopVar+1);
                        if (strItem.compareTo(";") == 0) {
                            //end of unicode block
                            break;
                        }
                        strSegment = strSegment + strItem;
                    } while (intLoopVar < strInput.length());
                    // convert unicode to char and add to string
                    if (strSegment.length() >0) {
                        intUnicode = Integer.parseInt(strSegment);
                        strResult = strResult + (char) intUnicode;
                    }
                 } else {
                    // add char without modification
                    strResult = strResult + strItem;
                 }
                // increment loop counter
                intLoopVar ++;
            } // end while

        }
        return strResult;
    }
    
    
}
