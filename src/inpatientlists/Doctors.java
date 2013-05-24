

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 21/05/2013 22:06
 */
public class Doctors {

    private String code;
    private String name;
    
    
    
    Doctors(){
        loadDefaults();
    }
    Doctors(String code, String name){
        this.code = code;
        this.name = name;
    }
    Doctors (String code){
        loadData(code);
    }
    Doctors(Doctors doctor){
        // defensive copy
    }
    private void loadDefaults(){
        code = "";
        name = "";
    }
    public void setCode(String code){
        this.code = code;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getCode(){
        return code;
    }
    public String getName(){
        return name;
    }
    
    private void loadData(String code){
        /**
         * loads data
         */
         String strSQL = "SELECT tbl_005_DoctorList.* " +
                "FROM tbl_005_DoctorList " +
                "WHERE ((tbl_005_DoctorList.DrCode) ='" + code +"'); " ;
          try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.code  = rs.getString("DrCode");
                this.name = rs.getString("Title") + " " + rs.getString("GivenNames") + " " + rs.getString("Surname");
                
            }else{
                // new record
                loadDefaults();               
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadUser").log(Level.SEVERE, null, ex);
        }
    }
    
    public static Doctors[] loadDoctors(){
         /**
         * loads data
         */
         String strSQL;
         String tempName;
         Doctors[] retVar = new Doctors[0];
                  
         strSQL = "SELECT tbl_005_DoctorList.* " +
                "FROM tbl_005_DoctorList " +
                "ORDER BY Tbl_005_DoctorList.Surname;"; 
                
         try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new Doctors[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                   retVar[LoopVar] = new Doctors();
                   retVar[LoopVar].setCode(rs.getString("DrCode"));
                   tempName = rs.getString("Title") + " " + rs.getString("GivenNames") + " " + rs.getString("Surname");
                   retVar[LoopVar].setName(tempName);  
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadDoctors").log(Level.SEVERE, null, ex);
            }
            return retVar;
        
        
    }
    
}
