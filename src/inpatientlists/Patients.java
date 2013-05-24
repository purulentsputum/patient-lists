
package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 19:30
 */
public class Patients {
    private String firstName;
    private String lastName;
    private String sex;
    private Date dob;
    private String urn;
    
    Patients(){
        setDefaults();
    }
    Patients(String urn){
        loadData(urn);
    }
    Patients (String lastName, String firstName, String sex, Date dob, String urn){
        this.lastName = lastName;
        this.firstName=firstName;
        setSex(sex);
        this.dob=dob;
        this.urn=urn;
    }
    Patients (Patients newPatient){
        //defensive copy
        
    }
    
    private void setDefaults(){
        firstName = "";
        lastName = "";
        sex = "U";
        dob = null;
    }
    // getters
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }
    public String getSex(){
        return sex;
    }
    public String getURN(){
        return urn;
    }
    public Date getDOB(){
        return dob;
    }
    public String getOneLineDetails(){
        return lastName + ", " + firstName + "  " + dob + "  " + sex + "  " + urn; 
    }
    //setters
    public void setLastName (String lastName){
        this.lastName = lastName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setSex(String sex){
        if(sex.equalsIgnoreCase("M")){
            this.sex = "M";
        }else if(sex.equalsIgnoreCase("F")){
            this.sex="F";
        }else{
            this.sex = "U";
        }
    }
    public void setDOB(Date dob){
        this.dob=dob;
    }
    public void setURN(String urn){
        this.urn  = urn;
    }
    //database access
    
    public void loadData(String URN){
        /**
         * loads current patient data
         * @param URN patients UR Number
         */
        
        String strSQL = "SELECT tbl_001_patients.* " +
                "FROM tbl_001_patients  " +
                "WHERE(tbl_001_patients.URN = '" + URN + "');";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.urn  = rs.getString("URN");
                this.lastName = rs.getString("Surname");
                this.firstName = rs.getString("GivenNames");
                this.sex=rs.getString("Gender");
                this.dob=rs.getDate("DateOfBirth");                
            }else{
                // new record
                setDefaults();               
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("Loadpatients").log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void saveData(){
        /**
         * saves current patient data 
         * 
         */
        
        String strSQL = "SELECT tbl_001_patients.* " +
                "FROM tbl_001_patients  " +
                "WHERE(tbl_001_patients.URN = '" + urn + "');";
        
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                rs.updateString("URN", urn);
                rs.updateString("Surname",lastName);
                rs.updateString("GivenNames",firstName);
                rs.updateString("Gender",sex);
                rs.updateDate("DateOfBirth",MyDates.JavaDateToSQLasDate(dob));
                
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                
                rs.updateString("URN", urn);
                rs.updateString("Surname",lastName);
                rs.updateString("GivenNames",firstName);
                rs.updateString("Gender",sex);
                rs.updateDate("DateOfBirth",MyDates.JavaDateToSQLasDate(dob));
                
                rs.insertRow();
            }
            rs.close();
            //PracticeLocality.LoadLocality(PracLocalityKey);
            
        } catch (SQLException ex) {
            Logger.getLogger("savePatient").log(Level.SEVERE, null, ex);
        }
     
    }
    
    public static Patients[] loadAllPatients(){
         /**
         * loads individual user
         *  @param activeOnly a flag to indicate if all users or only active ones are loaded
         */
        
         Patients[] retVar = new Patients[0];
         
          String strSQL = "SELECT tbl_001_patients.* " +
                "FROM tbl_001_patients  ;";
                
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new Patients[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                    retVar[LoopVar] = new Patients();
                    retVar[LoopVar].urn  = rs.getString("UID");
                    retVar[LoopVar].lastName = rs.getString("Surname");
                    retVar[LoopVar].firstName = rs.getString("GivenNames");
                    retVar[LoopVar].sex =rs.getString("Gender");
                    retVar[LoopVar].dob =rs.getDate("DateOfBirth");                     
                           
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadAllPatients").log(Level.SEVERE, null, ex);
            }
            return retVar;
            
            

    }
    
    
    
    
}
