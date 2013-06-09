

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 23:19
 * @edited 24/05/2013 13:45 added SQL filter to name field and updated user table items
 * @edited 26/05/2013 10:28 added defensive copy code
 */
public class User {
    private String uid;
    private String name;
    private String defaultWard;
    private String defaultUnit;
    private String defaultCons;
    private Boolean admin;
    private Boolean active;             //used for users who have left
    private Boolean locked;             //used for users with bad passwords etc
    private Boolean limited;            // default uer can see list but do no more
    private Date dateCreated;
    private Date dateLastLogin;
    private Date dateLastInvalidLogin;
    private Integer numberValidLogins;
    private Integer numberInvalidLogins; //number since last valid login
    private Boolean newUser;            // flags if a user is new 
    
    public static User CurrentUser;
    
    User(){
        loadDefaults();
    }
    User (String uid){
        loadData(uid);
    }
    User(User user){
        //defensive copy
        this.uid=user.getUID();
        this.name=user.getName();
        this.defaultWard=user.getWard();
        this.defaultUnit=user.getUnit();
        this.defaultCons=user.getConsultantCode();
        this.admin = user.isAdmin();
        this.active = user.isActive();
        this.locked = user.isLocked(); 
        this.limited = user.isLimited();
        this.dateCreated = new Date(user.getDateCreated().getTime());
        this.dateLastLogin = new Date(user.getDateLastLogin().getTime());
        this.dateLastInvalidLogin = new Date(user.getDateLastInvalidLogin().getTime());
        this.numberValidLogins =user.getNumberValidLogins();
        this.numberInvalidLogins =user.getNumberInvalidLogins();
        this.newUser = user.isNewUser();
    }
    private void loadDefaults(){
        uid="";
        name="";
        defaultWard="none";
        defaultUnit="none";
        defaultCons="none";
        admin = false;
        active = false;
        locked = false; 
        limited = true;
        dateCreated = MyDates.CurrentDateTime();
        dateLastLogin = MyDates.CurrentDateTime();
        dateLastInvalidLogin = MyDates.CurrentDateTime();
        numberValidLogins =0;
        numberInvalidLogins =0;
        newUser = true;                
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
    public String getConsultantCode(){
        return defaultCons;
    }
    public Boolean isAdmin(){
        return admin;
    }
    public Boolean isActive(){
        return active;
    }
    public Boolean isLocked(){
        return locked;
    }
    public Boolean isLimited(){
        return limited;
    }
    public Date getDateCreated(){
        return dateCreated;
    }
    public Date getDateLastLogin(){
        return dateLastLogin;
    }
    public Date getDateLastInvalidLogin(){
        return dateLastInvalidLogin;
    }
    public Integer getNumberValidLogins(){
        return numberValidLogins;
    }    
    public Integer getNumberInvalidLogins(){
        return numberInvalidLogins;
    } 
    public Boolean isNewUser(){
        return newUser;
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
    public void setLocked(Boolean locked){
        this.locked=locked;
    }
    public void setLimited(Boolean limited){
        this.limited = limited;
    }
    public void setDateCreated(Date dateCreated){
        this.dateCreated=dateCreated;
    }           
    public void setDateLastLogin(Date dateLastLogin){
        this.dateLastLogin=dateLastLogin;
    }
    public void setDateLastInvalidLogin(Date dateLastInvalidLogin){
        this.dateLastInvalidLogin=dateLastInvalidLogin;
    }
    public void setNumberValidLogins(Integer numberValidLogins){
        this.numberValidLogins=numberValidLogins;
    }    
    public void setNumberInvalidLogins(Integer numberInvalidLogins){
        this.numberInvalidLogins=numberInvalidLogins;
    } 
    public void setNewUser(Boolean newUser){
        this.newUser=newUser;
    }
    //
    public void incrementNumberValidLogins(){
        numberValidLogins++;
        dateLastLogin = MyDates.CurrentDateTime();
        saveData();
    }    
    public void incrementNumberInvalidLogins(){
        numberInvalidLogins++;
        dateLastInvalidLogin = MyDates.CurrentDateTime();
        saveData();
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
                this.name = Utilities.ReplaceNonAlpha(rs.getString("Name"));
                this.defaultWard = rs.getString("DefaultWard");
                this.defaultUnit =rs.getString("DefaultUnit");
                this.defaultCons =rs.getString("DefaultCons");  
                this.admin = rs.getBoolean("Admin");
                this.active = rs.getBoolean("Active");
                this.locked = rs.getBoolean("Locked");
                this.limited = rs.getBoolean("Limited");
                this.dateCreated = rs.getTimestamp("Created");
                this.dateLastLogin = rs.getTimestamp("LastValidLogin");
                this.dateLastInvalidLogin = rs.getTimestamp("LastInvalidLogin");
                this.numberValidLogins =rs.getInt("ValidLogins");
                this.numberInvalidLogins =rs.getInt("InvalidLogins");
                this.newUser=false;
            }else{
                // new record
                loadDefaults(); 
                this.uid=UID;
                this.newUser=true;
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
                rs.updateString("Name",Utilities.RemoveNonAlpha(name));
                rs.updateString("DefaultWard",defaultWard);
                rs.updateString("DefaultUnit",defaultUnit);
                rs.updateString("DefaultCons",defaultCons);
                rs.updateBoolean("Admin",admin);
                rs.updateBoolean("Active",active);                               
                rs.updateBoolean("Locked",locked);
                rs.updateBoolean("Limited",limited);
                rs.updateTimestamp("Created",MyDates.JavaDateTimeToSQLasDate(dateCreated));
                rs.updateTimestamp("LastValidLogin",MyDates.JavaDateTimeToSQLasDate(dateLastLogin));
                rs.updateTimestamp("LastInvalidLogin",MyDates.JavaDateTimeToSQLasDate(dateLastInvalidLogin));
                rs.updateInt("ValidLogins",numberValidLogins);
                rs.updateInt("InvalidLogins",numberInvalidLogins);
                                 
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                
                rs.updateString("UID", uid);
                rs.updateString("Name",Utilities.RemoveNonAlpha(name));
                rs.updateString("DefaultWard",defaultWard);
                rs.updateString("DefaultUnit",defaultUnit);
                rs.updateString("DefaultCons",defaultCons);
                rs.updateBoolean("Admin",admin);
                rs.updateBoolean("Active",active);
                rs.updateBoolean("Locked",locked);
                rs.updateBoolean("Limited",limited);
                rs.updateTimestamp("Created",MyDates.JavaDateTimeToSQLasDate(dateCreated));
                rs.updateTimestamp("LastValidLogin",MyDates.JavaDateTimeToSQLasDate(dateLastLogin));
                rs.updateTimestamp("LastInvalidLogin",MyDates.JavaDateTimeToSQLasDate(dateLastInvalidLogin));
                rs.updateInt("ValidLogins",numberValidLogins);
                rs.updateInt("InvalidLogins",numberInvalidLogins);
                
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
                    retVar[LoopVar].name = Utilities.ReplaceNonAlpha(rs.getString("Name"));
                    retVar[LoopVar].defaultWard = rs.getString("DefaultWard");
                    retVar[LoopVar].defaultUnit =rs.getString("DefaultUnit");
                    retVar[LoopVar].defaultCons =rs.getString("DefaultCons");  
                    retVar[LoopVar].admin = rs.getBoolean("Admin");
                    retVar[LoopVar].active = rs.getBoolean("Active");
                    retVar[LoopVar].locked = rs.getBoolean("Locked");
                    retVar[LoopVar].limited = rs.getBoolean("Limited");
                    retVar[LoopVar].dateCreated = rs.getTimestamp("Created");
                    retVar[LoopVar].dateLastLogin = rs.getTimestamp("LastValidLogin");
                    retVar[LoopVar].dateLastInvalidLogin = rs.getTimestamp("LastInvalidLogin");
                    retVar[LoopVar].numberValidLogins =rs.getInt("ValidLogins");
                    retVar[LoopVar].numberInvalidLogins =rs.getInt("InvalidLogins");       
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadAllUsers").log(Level.SEVERE, null, ex);
            }
            return retVar;
            
            

    }
    
   
    
    
}
