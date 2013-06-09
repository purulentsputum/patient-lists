

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
 * @edited 07/06/2013 to contain full task info for a single task/day
 */
public class Task {
    private String key;
    private Date taskDate;
    private String urn;
    private TaskType taskType;
    private String taskTypeKey;
    private String taskDesc;
    private User createdBy;
    private User editedBy;
    private User completedBy;
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
        key = value.getKey();
        taskDate=new Date(value.getDate().getTime());
        urn=value.getURN();
        taskDesc=value.getTaskDesc();
        taskType = TaskType.getTaskTypeFromKey(value.getTaskType().getKey());
        createdBy= new User(value.getCreatedBy());
        editedBy=new User(value.getEditedBy());
        completedBy = new User(value.getCompletedBy());
        dateCreated=new Date(value.getDateCreated().getTime());
        dateEdited=new Date(value.getDateEdited().getTime());
        dateCompleted = new Date(value.getDateCompleted().getTime());
        completed = value.isCompleted();
    }
    private void loadDefaults(){
        key = Utilities.GenerateKey();
        taskDate=MyDates.CurrentDate();
        urn="";
        taskDesc=" ";
        taskType = TaskType.getDefault();
        createdBy= new User(User.CurrentUser.getUID());
        editedBy=new User(User.CurrentUser.getUID());
        completedBy = new User();
        dateCreated=MyDates.CurrentDateTime();
        dateEdited=MyDates.CurrentDateTime();
        dateCompleted = null;
        completed = false;
    }
    //getters
    public final String getKey(){
        return key;
    }
    public final Date getDate(){
        return taskDate;
    }
    public String getURN(){
        return urn;
    }
    public String getTaskDesc(){
        return taskDesc;
    }
    public TaskType getTaskType(){
        return taskType;
    }
    public Date getDateCreated(){
        return dateCreated;
    }
    public Date getDateEdited(){
        return dateEdited;
    }
    public Date getDateCompleted(){
        return dateEdited;
    }
    public User getCreatedBy(){
        return createdBy;
    }
    public User getEditedBy(){
        return editedBy;
    }
    public User getCompletedBy(){
        return completedBy;
    }
    public Boolean isCompleted(){
        return completed;
    }
    public String getUsersOneLine(){
        String retVar =  "created by: " + this.createdBy.getName() + " on " + MyDates.ConvertDateTimeToString(this.dateCreated);
        if (this.editedBy.getName().length()>1){
            retVar = retVar + "; edited by: " + this.editedBy.getName() + " on " + MyDates.ConvertDateTimeToString(this.getDateEdited());
        }
        if (completed){
            retVar = retVar + "; completed by: " + this.completedBy.getName() + " on " + MyDates.ConvertDateTimeToString(this.getDateCompleted());
        }
        return retVar;
    }
    
    //setters
    public void setKey(String key){
        this.key=key;
    }
    public void setDate(Date taskDate){
        this.taskDate = new Date(taskDate.getTime());
    }
    public void setURN(String urn){
        this.urn = urn;
    }
    public void setTaskType(TaskType taskType){
        this.taskType = TaskType.getTaskTypeFromKey(taskType.getKey());
    }
    public void setTaskType(String key){
        this.taskType= TaskType.getTaskTypeFromKey(key);
    }
    public void setTaskDesc(String tasks){
        this.taskDesc = tasks;
    }
    public void setDateCreated(Date dateCreated){
        this.dateCreated = new Date (dateCreated.getTime());
    }
    public void setDateEdited(Date dateEdited){
        this.dateEdited = new Date(dateEdited.getTime());
    }
    public void setDateCompleted(Date dateCompleted){
        this.dateCompleted = new Date(dateCompleted.getTime());
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
    public void setCompletedBy(User user){
        this.completedBy = new User(user);
    }
    public void setCompletedBy(String uid){
        this.completedBy = new User(uid);
    }
    public void setCompleted(Boolean completed){
        this.completed = completed;
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
                this.taskDate = rs.getDate("TaskDate");
                this.urn = rs.getString("URN");
                this.taskType = TaskType.getTaskTypeFromKey(rs.getString("TaskType"));
                this.taskDesc = Utilities.ReplaceNonAlpha(rs.getString("TaskDesc"));
                this.createdBy = new User(rs.getString("UIDCreatedBy"));
                this.editedBy = new User(rs.getString("UIDEditedBy"));
                this.completedBy = new User(rs.getString("UIDCompletedBy"));
                this.dateCreated = rs.getTimestamp("DateCreated");
                this.dateEdited = rs.getTimestamp("DateEdited");
                this.dateCompleted = rs.getTimestamp("DateEdited");
                this.completed = rs.getBoolean("Completed");
                                                
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
                rs.updateDate("TaskDate",MyDates.JavaDateToSQLasDate(taskDate));
                rs.updateString("URN",urn);
                rs.updateString("TaskType",taskType.getKey());
                rs.updateString(Utilities.RemoveNonAlpha("TaskDesc"),taskDesc);
                rs.updateString("UIDCreatedBy",createdBy.getUID());
                rs.updateString("UIDEditedBy",editedBy.getUID());
                rs.updateString("UIDCompletedBy",completedBy.getUID());
                rs.updateTimestamp("DateCreated",MyDates.JavaDateTimeToSQLasDate(dateCreated));
                rs.updateTimestamp("DateEdited",MyDates.JavaDateTimeToSQLasDate(dateEdited));
                rs.updateTimestamp("DateCompleted",MyDates.JavaDateTimeToSQLasDate(dateCompleted));
                rs.updateBoolean("Completed", completed);
                                 
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                //generate key
                key = Utilities.GenerateKey();
                
                rs.updateString("Key",key);
                rs.updateDate("TaskDate",MyDates.JavaDateToSQLasDate(taskDate));
                rs.updateString("URN",urn);
                rs.updateString("TaskType",taskType.getKey());
                rs.updateString(Utilities.RemoveNonAlpha("TaskDesc"),taskDesc);
                rs.updateString("UIDCreatedBy",createdBy.getUID());
                rs.updateString("UIDEditedBy",editedBy.getUID());
                rs.updateString("UIDCompletedBy",completedBy.getUID());
                rs.updateTimestamp("DateCreated",MyDates.JavaDateTimeToSQLasDate(dateCreated));
                rs.updateTimestamp("DateEdited",MyDates.JavaDateTimeToSQLasDate(dateEdited));
                rs.updateTimestamp("DateCompleted",MyDates.JavaDateTimeToSQLasDate(dateCompleted));
                rs.updateBoolean("Completed", completed);
                
                rs.insertRow();
            }
            rs.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger("saveTask").log(Level.SEVERE, null, ex);
        }
    }
    public static Task[] loadAllPatientTasks(String urn){
        String strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks ;  ";
        return loadSelectedTasks(strSQL);
    }
    public static Task[] loadSelectedPatientTasks(String urn, String taskTypeKey, Boolean futureOnly){
        String strSQL;
        
        if (futureOnly){
             strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks  " +
                "WHERE(((tbl_110_tasks.URN) = '" +urn +"') " +
                "AND ((tbl_110_tasks.TaskType) = '" + taskTypeKey + "') " +
                "AND ((tbl_110_tasks.TaskDate) >= '"+ MyDates.JavaDateToSQLasString(MyDates.CurrentDate()) + "')) " +
                "ORDER BY Tbl_110_tasks.TaskDate DESC, Tbl_110_tasks.TaskType;";
         }else{
             strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks  " +
                "WHERE(((tbl_110_tasks.URN) = '" +urn +"') " +
                "AND ((tbl_110_tasks.TaskType) = '" + taskTypeKey + "')) "+
                "ORDER BY Tbl_110_tasks.TaskDate DESC, Tbl_110_tasks.TaskType;";
               
         }  
         return loadSelectedTasks(strSQL);
    }
    
    public static Task[] loadAllPatientTasks(String urn,Boolean futureOnly){
        /**
         * loads individual data
         *  @param futureOnly a flag to indicate if all tasks or only future ones are loaded
         */
         String strSQL;
         
         if (futureOnly){
             strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks  " +
                "WHERE(((tbl_110_tasks.URN) = '" +urn +"') " +
                " AND ((tbl_110_tasks.TaskDate) >= '"+ MyDates.JavaDateToSQLasString(MyDates.CurrentDate()) + "')) "+
                "ORDER BY Tbl_110_tasks.TaskDate DESC, Tbl_110_tasks.TaskType;";
         }else{
             strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks  " +
                "WHERE(tbl_110_tasks.URN = '" +urn +"') "+
                "ORDER BY Tbl_110_tasks.TaskDate DESC, Tbl_110_tasks.TaskType;";
         }         
         return loadSelectedTasks(strSQL);         
    }
    
    public static Task[] loadTasksOnADay(Date nDate){
         String strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks " +
                "WHERE ((tbl_110_tasks.TaskDate) = '" + MyDates.JavaDateToSQLasString(nDate) + "') "+
                "ORDER BY Tbl_110_tasks.TaskDate DESC, Tbl_110_tasks.TaskType;";
        return loadSelectedTasks(strSQL);
    }
    
    public static Task[] loadTasksOnADay(Date nDate,String taskTypeKey){
         String strSQL = "SELECT tbl_110_tasks.* " +
                "FROM tbl_110_tasks " +
                 "WHERE (((tbl_110_tasks.TaskDate) = '" + MyDates.JavaDateToSQLasString(nDate) + "')" +
                 "AND ((tbl_110_tasks.TaskType) = '" + taskTypeKey + "')) "+
                 "ORDER BY Tbl_110_tasks.TaskDate DESC, Tbl_110_tasks.TaskType;";
        return loadSelectedTasks(strSQL);
    }
    
    private static Task[] loadSelectedTasks(String strSQL){
        /*
         * loads individual data
         *  @param strSQL SQL statement
         */
        
        Task[] retVar = new Task[0];
                               
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
                    retVar[LoopVar].taskDate = rs.getDate("TaskDate");
                    retVar[LoopVar].urn = rs.getString("URN");
                    retVar[LoopVar].taskType = TaskType.getTaskTypeFromKey(rs.getString("TaskType"));
                    retVar[LoopVar].taskDesc = Utilities.ReplaceNonAlpha(rs.getString("TaskDesc"));
                    retVar[LoopVar].createdBy = new User(rs.getString("UIDCreatedBy"));
                    retVar[LoopVar].editedBy = new User(rs.getString("UIDEditedBy"));
                    retVar[LoopVar].completedBy = new User(rs.getString("UIDCompletedBy"));
                    retVar[LoopVar].dateCreated = rs.getTimestamp("DateCreated");
                    retVar[LoopVar].dateEdited = rs.getTimestamp("DateEdited");
                    retVar[LoopVar].dateCompleted = rs.getTimestamp("DateEdited");
                    retVar[LoopVar].completed = rs.getBoolean("Completed");                                                                        
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
                Logger.getLogger("LoadAllTasks").log(Level.SEVERE, null, ex);
            }
            return retVar;
                        
    }
    
   
        
    
}
