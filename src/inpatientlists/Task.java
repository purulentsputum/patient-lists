

package inpatientlists;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 26/05/2013 08:35
 */
public class Task {
    private String key;
    private Date firstDate;
    private Date lastDate;
    private String urn;
    private TaskTypes taskType;
    private String tasks;
    private User createdBy;
    private User editedBy;
    private User CompletedBy;
    //private User taskedTo;
    private Date dateCreated;
    private Date dateEdited;
    private Date dateCompleted; // may be flagged 
    private Boolean completed;
    
    Task(){
        loadDefaults();
    }
    Task(String key){
        loadData(key);
    }
    Task (Task value){
        //defensive copy
    }
    private void loadDefaults(){
        key = Utilities.GenerateKey();
        firstDate=MyDates.CurrentDate();
        lastDate=firstDate;
        urn="";
        tasks=" ";
        taskType = new TaskTypes();
        createdBy= new User(User.CurrentUser.getUID());
        editedBy=new User(User.CurrentUser.getUID());
        dateCreated=MyDates.CurrentDate();
        dateEdited=MyDates.CurrentDate();
    }
    //getters
    public final String getKey(){
        return key;
    }
    public final Date getFirstDate(){
        return firstDate;
    }
    public Date getLastDate(){
        return lastDate;
    }
    public String getURN(){
        return urn;
    }
    public String getTasks(){
        return tasks;
    }
    public TaskTypes getTaskType(){
        return taskType;
    }
    public Date getDateCreated(){
        return dateCreated;
    }
    public Date getDateEdited(){
        return dateEdited;
    }
    public User getCreatedBy(){
        return createdBy;
    }
    public User getEditedBy(){
        return editedBy;
    }
    //setters
    public void setKey(String key){
        this.key=key;
    }
    public void setFirstDate(Date firstDate){
        this.firstDate = new Date(firstDate.getTime());
    }
    public void setLastDate(Date lastDate){
        this.lastDate = new Date(lastDate.getTime());
    }
    public void setURN(String urn){
        this.urn = urn;
    }
    public void setTaskType(TaskTypes taskType){
        this.taskType = new TaskTypes(taskType);
    }
    public void setTaskType(String key){
        this.taskType=new TaskTypes(key);
    }
    public void setTasks(String tasks){
        this.tasks = tasks;
    }
    public void setDateCreated(Date dateCreated){
        this.dateCreated = new Date (dateCreated.getTime());
    }
    public void setDateEdited(Date dateEdited){
        this.dateEdited = new Date(dateEdited.getTime());
    }
    public void setCreatedBy(User user){
        this.createdBy = new User(user);
    }
    public void setCreatedBy(String uid){
        this.createdBy = new User(uid);
    }
    public void setEditedBy(User user){
        this.editedBy = new User(user);
    }
    public void setEditedBy(String uid){
        this.editedBy = new User(uid);
    }
    // other
    public void loadData(String key){
        /**
         * loads individual data
         *  @param key data tables key
         */
        
        
        String strSQL = "SELECT tbl_110_Tasks.* " +
                "FROM tbl_110_tasks  " +
                "WHERE(tbl_110_tasks.Key = '" + key + "');";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.key  = rs.getString("Key");
                this.firstDate = rs.getDate("DateFirst");
                this.lastDate = rs.getDate("DateLast");
                this.urn = rs.getString("URN");
                this.taskType = new TaskTypes(rs.getString("TaskType"));
                this.tasks = Utilities.ReplaceNonAlpha(rs.getString("Tasks"));
                this.createdBy = new User(rs.getString("UIDCreatedBy"));
                this.editedBy = new User(rs.getString("UIDEditedBy"));
                this.dateCreated = rs.getTimestamp("DateCreated");
                this.dateEdited = rs.getTimestamp("DateEdited");
                                                
            }else{
                // new record
                loadDefaults();                 
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadTask").log(Level.SEVERE, null, ex);
        }
                        
    }
    public void saveData(){
        /**
         * saves current data 
         * 
         */
        
        String strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks  " +
                "WHERE(tbl_110_tasks.Key = '" + key + "');";
        
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                rs.updateString("Key",key);
                rs.updateDate("DateFirst",MyDates.JavaDateToSQLasDate(firstDate));
                rs.updateDate("DateLast",MyDates.JavaDateToSQLasDate(lastDate));
                rs.updateString("URN",urn);
                rs.updateString("TaskType",taskType.getKey());
                rs.updateString(Utilities.RemoveNonAlpha("Tasks"),tasks);
                rs.updateString("UIDCreatedBy",createdBy.getUID());
                rs.updateString("UIDEditedBy",editedBy.getUID());
                rs.updateTimestamp("DateCreated",MyDates.JavaDateTimeToSQLasDate(dateCreated));
                rs.updateTimestamp("DateEdited",MyDates.JavaDateTimeToSQLasDate(dateEdited));
                                 
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                //generate key
                key = Utilities.GenerateKey();
                
                rs.updateString("Key",key);
                rs.updateDate("DateFirst",MyDates.JavaDateToSQLasDate(firstDate));
                rs.updateDate("DateLast",MyDates.JavaDateToSQLasDate(lastDate));
                rs.updateString("URN",urn);
                rs.updateString("TaskType",taskType.getKey());
                rs.updateString(Utilities.RemoveNonAlpha("Tasks"),tasks);
                rs.updateString("UIDCreatedBy",createdBy.getUID());
                rs.updateString("UIDEditedBy",editedBy.getUID());
                rs.updateTimestamp("DateCreated",MyDates.JavaDateTimeToSQLasDate(dateCreated));
                rs.updateTimestamp("DateEdited",MyDates.JavaDateTimeToSQLasDate(dateEdited));
                
                rs.insertRow();
            }
            rs.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger("saveTask").log(Level.SEVERE, null, ex);
        }
    }
    public static Task[] loadAllPatientTasks(String urn){
        return loadAllPatientTasks(urn,false);
    }
    public static Task[] loadAllPatientTasks(String urn,Boolean futureOnly){
        /**
         * loads individual data
         *  @param futureOnly a flag to indicate if all tasks or only future ones are loaded
         */
         String strSQL;
         Task[] retVar = new Task[0];
         
         if (futureOnly){
             strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks  " +
                "WHERE(tbl_110_tasks.URN = '" +urn +"');";
         }else{
             strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks ;  "; 
         }
                
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new Task[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                    retVar[LoopVar] = new Task();
                                        
                    retVar[LoopVar].key  = rs.getString("Key");
                    retVar[LoopVar].firstDate = rs.getDate("DateFirst");
                    retVar[LoopVar].lastDate = rs.getDate("DateLast");
                    retVar[LoopVar].urn = rs.getString("URN");
                    retVar[LoopVar].taskType = new TaskTypes(rs.getString("TaskType"));
                    retVar[LoopVar].tasks = Utilities.ReplaceNonAlpha(rs.getString("Tasks"));
                    retVar[LoopVar].createdBy = new User(rs.getString("UIDCreatedBy"));
                    retVar[LoopVar].editedBy = new User(rs.getString("UIDEditedBy"));
                    retVar[LoopVar].dateCreated = rs.getTimestamp("DateCreated");
                    retVar[LoopVar].dateEdited = rs.getTimestamp("DateEdited");
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadAllTasks").log(Level.SEVERE, null, ex);
            }
            return retVar;
            
            

    }
    
   
        
    
}
