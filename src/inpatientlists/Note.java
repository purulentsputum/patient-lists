

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 01/06/2013 19:36
 */
public class Note {

    private String key;
    private String note;
    private User createdBy;
    private User editedBy;
    private java.util.Date createdDate;
    private java.util.Date  editedDate;
    private Patients patient;
    
    Note(){
      loadDefaults();  
    }
    Note(String key){
        loadData(key);
    }
    Note(Patients patient){
        loadPatientData(patient.getURN());
    }
        
    Note(Note notes){
        //defensive copy
        
    }
    private void loadDefaults(){
        key = Utilities.GenerateKey();
        note = "";
        createdBy = new User(User.CurrentUser);
        editedBy = new User(User.CurrentUser);
        createdDate = new Date(MyDates.CurrentDateTime().getTime());
        editedDate = new Date(MyDates.CurrentDateTime().getTime());
        patient = new Patients();
    }
    //getters
    public String getKey(){
        return key;
    }
    public String getNote(){
        return note;
    }
    public User getCreatedBy(){
        return createdBy;
    }
    public User getEditedBy(){
        return editedBy;
    }
    public Date getCreatedDate(){
        return createdDate;
    }
    public Date getEditedDate(){
        return editedDate;
    }
    public Patients getPatient(){
        return patient;
    }
    public String getNoteText(){
        String retVar = "";
        if(note.length()>5) {
            retVar= note + Utilities.NewLine +" edited by " + editedBy.getName() +
                " on " + MyDates.ConvertDateTimeToString(editedDate) ;
        }
        return retVar;        
    }
    //setters
    private void setKey(String key){
        this.key = key;
    }
    public void setNote(String note){
        this.note = note;
    }
    private void setCreatedBy(User createdByu){
        this.createdBy = new User(createdBy);
    }
    private void setCreatedBy(String key){
        this.createdBy = new User(key);
    }
    public void setEditedBy(User editedBy){
        this.editedBy = new User(editedBy);
    }
    public void setEditedBy(String key){
        this.editedBy = new User(key);
    }
    private void setCreatedDate(Date createdDate){
        this.createdDate = new Date(createdDate.getTime());
    }
    public void setEditedDate(Date editedDate){
        this.editedDate = new Date(editedDate.getTime());
    }
    private void setPatient(Patients patient){
        this.patient = new Patients(patient);
    }
    private void setPatient(String urn){
        this.patient = new Patients(urn);
    }
    
    
    
    
    
    private void loadData(String key){
    /**
         * loads individual data
         *  @param key data tables key
         */
        
        
        String strSQL = "SELECT tbl_100_notes.* " +
                "FROM tbl_100_notes  " +
                "WHERE(tbl_100_notes.Key = '" + key + "');";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.key  = rs.getString("Key");
                this.createdDate = rs.getTimestamp("DateCreated");
                this.editedDate = rs.getTimestamp("DateEdited");
                this.createdBy = new User(rs.getString("CreatedBy"));
                this.editedBy = new User(rs.getString("EditedBy"));
                this.patient = new Patients(rs.getString("URN"));
                this.note = Utilities.ReplaceNonAlpha(rs.getString("Note"));
                                                                
            }else{
                // new record
                loadDefaults();                 
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadNote").log(Level.SEVERE, null, ex);
        }
    }
    public void saveData(){
        /**
         * saves current data 
         * 
         */
        
        String strSQL = "SELECT tbl_100_notes.* " +
                "FROM tbl_100_notes  " +
                "WHERE(tbl_100_notes.Key = '" + key + "');";
        
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                rs.updateString("Key",key);
                rs.updateTimestamp("DateCreated",MyDates.JavaDateTimeToSQLasDate(createdDate));
                rs.updateTimestamp("DateEdited",MyDates.JavaDateTimeToSQLasDate(MyDates.CurrentDateTime()));
                rs.updateString("CreatedBy",createdBy.getUID());
                rs.updateString("EditedBy",editedBy.getUID());
                rs.updateString("Note",Utilities.RemoveNonAlpha(note));                
                rs.updateString("URN", patient.getURN());
                                
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                //generate key
                key = Utilities.GenerateKey();
                
                rs.updateString("Key",key);
                rs.updateTimestamp("DateCreated",MyDates.JavaDateTimeToSQLasDate(createdDate));
                rs.updateTimestamp("DateEdited",MyDates.JavaDateTimeToSQLasDate(MyDates.CurrentDateTime()));
                rs.updateString("CreatedBy",createdBy.getUID());
                rs.updateString("EditedBy",editedBy.getUID());
                rs.updateString("Note",Utilities.RemoveNonAlpha(note));                
                rs.updateString("URN", patient.getURN());
                
                rs.insertRow();
            }
            rs.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger("saveNote").log(Level.SEVERE, null, ex);
        }
    }
    public void loadPatientData(String urn){
    /**
         * loads individual data
         *  @param key data tables key
         */
        
        
        String strSQL = "SELECT tbl_100_notes.* " +
                "FROM tbl_100_notes  " +
                "WHERE(tbl_100_notes.URN = '" + urn + "');";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.key  = rs.getString("Key");
                this.createdDate = rs.getTimestamp("DateCreated");
                this.editedDate = rs.getTimestamp("DateEdited");
                this.createdBy = new User(rs.getString("CreatedBy"));
                this.editedBy = new User(rs.getString("EditedBy"));
                this.patient = new Patients(rs.getString("URN"));
                this.note = Utilities.ReplaceNonAlpha(rs.getString("Note"));
                                                                
            }else{
                // new record
                loadDefaults(); 
                this.patient = new Patients(urn);
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadNote").log(Level.SEVERE, null, ex);
        }
    }
    
}
