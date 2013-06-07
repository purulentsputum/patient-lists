

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 25/05/2013 22:02
 */
public class AdmissionType {
    private String key;
    private String desc;
    private Boolean active;
    
    AdmissionType(){
        loadDefaults();
    }
    AdmissionType(String key){
        loadData(key,false);
    }
    AdmissionType(AdmissionType value){
        //defensive copy
    }
    
    private void loadDefaults(){
        key="";
        desc = "";
        active = true;
    }
    
    public String getAdmType(){
        return key;
    }
    public String getAdmTypeDesc(){
        return desc;
    }
    public void setAdmType(String admType){
        key=admType;
    }
    public void setAdmTypeDesc(String desc){
        this.desc = desc;
    }
    
    public void loadData(String admType, Boolean activeOnly){
        /**
         * loads individual data
         *  @param AdmType admission type code, eg 01 = acute
         *  @param activeOnly flags whether to load all types incl past ones, or only those currently active
         */
        
        
        String strSQL;
        if (activeOnly){
            strSQL = "SELECT tbl_010_AdmissionTypes.* " +
                "FROM tbl_010_AdmissionTypes  " +
                "WHERE(((tbl_010_AdmissionTypes.AdmType) = '" + admType + "') " +
                "AND ((tbl_010_AdmissionTypes.Active)=1));";
        }else{
            strSQL = "SELECT tbl_010_AdmissionTypes.* " +
                "FROM tbl_010_AdmissionTypes  " +
                "WHERE(tbl_010_AdmissionTypes.AdmType = '" + admType + "');";
        }        
        
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.key  = rs.getString("AdmType");
                this.desc = rs.getString("AdmDesc");
                this.active = rs.getBoolean("Active");
                
            }else{
                // new record
                loadDefaults(); 
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadAdmType").log(Level.SEVERE, null, ex);
        }
                        
    }
    // no save data required
    
    public static AdmissionType[] loadActiveAdmissionTypes(){
        return loadAllAdmissionTypes(true);
    }
    public static AdmissionType[] loadAllAdmissionTypes(Boolean activeOnly){
        /**
         * loads individual data
         *  @param activeOnly a flag to indicate if all data or only active ones are loaded
         */
         String strSQL;
         AdmissionType[] retVar = new AdmissionType[0];
         
          if (activeOnly){
            strSQL = "SELECT tbl_010_AdmissionTypes.* " +
                "FROM tbl_010_AdmissionTypes  " +
                "WHERE ((tbl_010_AdmissionTypes.Active)=1) " +
                "ORDER BY tbl_010_AdmissionTypes.AdmType;";
        }else{
            strSQL = "SELECT tbl_010_AdmissionTypes.* " +
                "FROM tbl_010_AdmissionTypes  " +
                "ORDER BY tbl_010_AdmissionTypes.AdmType;";
        }        
                
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new AdmissionType[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                    retVar[LoopVar] = new AdmissionType();
                    retVar[LoopVar].key  = rs.getString("AdmType");
                    retVar[LoopVar].desc = rs.getString("AdmDesc");
                    retVar[LoopVar].active = rs.getBoolean("Active");       
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadAllAdmissionTypes").log(Level.SEVERE, null, ex);
            }
            return retVar;
            
            

    }
    
    public static String findAdmTypeDesc(String code){
        String retVar="";
        AdmissionType[] list = AdmissionType.loadAllAdmissionTypes(true);
        for(AdmissionType adm : list){
            if(adm.getAdmType().equals(code)){
                retVar = adm.getAdmTypeDesc();
            }
        }               
        return retVar;
    }
}
