

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 23:19
 */
public class User {
    private String uid;
    private String name;
    private String defaultWard;
    private String defaultUnit;
    private String defaultCons;
    private Boolean admin;
    private Boolean active;
    
    public static User CurrentUser;
    
    User(){
        loadDefaults();
    }
    User (String uid){
        loadData(uid);
    }
    User(User user){
        //defensive copy
        
    }
    private void loadDefaults(){
        uid="";
        name="";
        defaultWard="";
        defaultUnit="";
        defaultCons="SELLA";
        admin = false;
        active = false;
        /*
         * maybe the following
         * Boolean locked;
         * Date lastIncorrectLogin;
         * Integer numberIncorrectLgins;
         * Date lastCorrectLogin;
         * 
         */
    }
    
    //getters
    public String getUID(){
        return uid;
    }
    public String getName(){
        return name;
    }
    public String getWard(){
        return defaultWard;
    }
    public String getUnit(){
        return defaultUnit;
    }
    public String getConsultant(){
        return defaultCons;
    }
    public Boolean isAdmin(){
        return admin;
    }
    public Boolean isActive(){
        return active;
    }
    //setters
    public void setUID(String uid){
        this.uid = uid;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setDefaultWard(String defaultWard){
        this.defaultWard = defaultWard;
    }
    public void setDefaultUnit(String defaultUnit){
        this.defaultUnit=defaultUnit;
    }
    public void setDefaultConsultant(String defaultConsultant){
        this.defaultCons=defaultConsultant;
    }
    public void setAdmin(Boolean admin){
        this.admin = admin;
    }
    public void setActive(Boolean active){
        this.active = active;
    }
    //load and save
    private void loadData(String UID){
        /**
         * loads individual user
         *  @param URN patients UR Number
         */
        
        
        String strSQL = "SELECT tbl_900_users.* " +
                "FROM tbl_900_users  " +
                "WHERE(tbl_900_users.UID = '" + UID + "');";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.uid  = rs.getString("UID");
                this.name = rs.getString("Name");
                this.defaultWard = rs.getString("DefaultWard");
                this.defaultUnit =rs.getString("DefaultUnit");
                this.defaultCons =rs.getString("DefaultCons");  
                this.admin = rs.getBoolean("Admin");
                this.active = rs.getBoolean("Active");
            }else{
                // new record
                loadDefaults();               
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadUser").log(Level.SEVERE, null, ex);
        }
                        
    }
    public void saveData(){
        /**
         * saves current data 
         * 
         */
        
        String strSQL = "SELECT tbl_900_users.* " +
                "FROM tbl_900_users  " +
                "WHERE(tbl_900_users.UID = '" + uid + "');";
        
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                rs.updateString("UID", uid);
                rs.updateString("Name",name);
                rs.updateString("DefaultWard",defaultWard);
                rs.updateString("DefaultUnit",defaultUnit);
                rs.updateString("DefaultCons",defaultCons);
                rs.updateBoolean("Admin",admin);
                rs.updateBoolean("Active",active);
                                 
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                
                rs.updateString("UID", uid);
                rs.updateString("Name",name);
                rs.updateString("DefaultWard",defaultWard);
                rs.updateString("DefaultUnit",defaultUnit);
                rs.updateString("DefaultCons",defaultCons);
                rs.updateBoolean("Admin",admin);
                rs.updateBoolean("Active",active);
                
                rs.insertRow();
            }
            rs.close();
            //PracticeLocality.LoadLocality(PracLocalityKey);
            
        } catch (SQLException ex) {
            Logger.getLogger("saveUser").log(Level.SEVERE, null, ex);
        }
    }
    public static User[] loadAllUsers(){
        return loadAllUsers(false);
    }
    public static User[] loadAllUsers(Boolean activeOnly){
        /**
         * loads individual user
         *  @param activeOnly a flag to indicate if all users or only active ones are loaded
         */
         String strSQL;
         User[] retVar = new User[0];
         
         if (activeOnly){
             strSQL = "SELECT tbl_900_users.* " +
                "FROM tbl_900_users  " +
                "WHERE(tbl_900_users.Active = 1);";
         }else{
             strSQL = "SELECT tbl_900_users.* " +
                "FROM tbl_900_users ;  "; 
         }
                
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new User[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                    retVar[LoopVar] = new User();
                    retVar[LoopVar].uid  = rs.getString("UID");
                    retVar[LoopVar].name = rs.getString("Name");
                    retVar[LoopVar].defaultWard = rs.getString("DefaultWard");
                    retVar[LoopVar].defaultUnit =rs.getString("DefaultUnit");
                    retVar[LoopVar].defaultCons =rs.getString("DefaultCons");  
                    retVar[LoopVar].admin = rs.getBoolean("Admin");
                    retVar[LoopVar].active = rs.getBoolean("Active");
                           
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadAllUsers").log(Level.SEVERE, null, ex);
            }
            return retVar;
            
            

    }
    
    
}
