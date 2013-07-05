

package inpatientlists;

import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author rosssellars
 * @created 20/05/2013 22:41
 * @edited 24/05/2013 12:00 added static variables and generatekey()
 * @edited 10/06/2013 added selectDirectory() for data export
 */
public class Utilities {
    public static final String NewLine  = System.getProperty("line.separator");
       
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
    public static void upDateUnitList(){
        // called after inpatient list update to current inpatients
        String strSQL = "SELECT Tbl_002_Admissions.DischargeUnit FROM Tbl_002_Admissions " +
                "GROUP BY Tbl_002_Admissions.DischargeUnit " +
                "WHERE ((DischargeUnit.DischargeStatus)='' );";
        
    
    }
    
    static String GenerateKey () {
        /**
         * This generates a key with length of 30 characters
         */

        String strRetVar = "";
        String strComputerName = System.getenv("COMPUTERNAME");

        Date dateNow =  new Date(); // current date/time

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = df.format( dateNow );
        String strRandom = Integer.toString((int)(Math.random()*100000000));
        // create key and pad out incase computer name is short
        strRetVar = strDate + strComputerName + strRandom + "0000000000";
        // return first 30 characters eg 20111225000001QH00000000000001
        return strRetVar.substring(0, 29);
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

    public static String getSHA256(String strInput){
        /*
         * creates SHA2 hash from data provided
         * this is used to compare records
         */
        String RetVar = "";
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(strInput.getBytes("UTF-8"));

            byte byteData[] = md.digest();

            StringBuffer hexString = new StringBuffer();

            for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
            }

            RetVar = hexString.toString();

        } catch (Exception ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }

        return RetVar;
    }
    
    public static Integer convertStringToInt(String value){
        
        int retVar = 0;
        
        try{
            retVar = Integer.parseInt(value);
        }catch (Exception ex){
            retVar = 0;
        }
        return retVar;
    }
    
    public static String noNulls(String value){
        String retVar;
        if (value!=null){
            retVar = value;
        }else{
            retVar = "";
        }
        return retVar;
    }
    
    public static String selectDirectory(){
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        f.showSaveDialog(null);

        //return (f.getCurrentDirectory().getPath());  //returns the directory of the selected folder.
        return(f.getSelectedFile().getPath());      //returns the selected folder
        
    }
    
}
