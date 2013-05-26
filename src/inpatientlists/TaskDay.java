

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 26/05/2013 11:35
 */
public class TaskDay {
    /**
     * each day of a task (esp for multiday tasks) incl completion
     */
    private String key;
    private Task task;
    private Date taskDate;
    private Boolean completed;
    private User completedBy;
    private Date dateCompleted;
    private String notes;
    
    TaskDay(){
        loadDefaults();
    }
    TaskDay(String key){
        loadData(key);
    }
    TaskDay(TaskDay taskDay){
        //defensive copy
    }
    
    
    private void loadDefaults(){
        key = Utilities.GenerateKey();
        task = new Task();
        taskDate = new Date(MyDates.CurrentDate().getTime());
        completed = false;
        completedBy = new User();
        dateCompleted = null;
        notes = "";
    }
    //getters
    public String getKey(){
        return key;
    }
    public Task getTask(){
        return task;
    }
    public Date getTaskDate(){
        return taskDate;
    }
    public Boolean isCompleted(){
        return completed;
    }
    public User getCompletedBy(){
        return completedBy;
    }
    public Date getDateCompleted(){
        return dateCompleted;
    }
    public String getNotes(){
        return notes;
    }    
    //setters
    public void setCompleted(Boolean completed){
        this.completed = completed; 
    }
    public void setCompleted(User user){
        setCompleted (user,"");
    }
    public void setCompleted(User user, String notes){
        this.completed = true;
        this.completedBy =  new User(user);
        this.dateCompleted = new Date(MyDates.CurrentDateTime().getTime());
        this.notes = notes;
    }
    //load and save
    
    public void loadData(String key){
        /**
         * loads individual data
         *  @param key data tables key
         */
        
        
        String strSQL = "SELECT tbl_111_taskdays.* " +
                "FROM tbl_111_taskdays  " +
                "WHERE(tbl_111_taskdays.Key = '" + key + "');";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                this.key  = rs.getString("Key");
                this.taskDate = rs.getDate("Date");
                this.task = new Task(rs.getString("TaskKey"));
                this.completed = rs.getBoolean("Completed");
                this.dateCompleted = rs.getTimestamp("DateCompleted");
                this.completedBy = new User(rs.getString("UIDCompletedBy"));
                this.notes = Utilities.ReplaceNonAlpha(rs.getString("Notes"));
                                                                
            }else{
                // new record
                loadDefaults();                 
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadTaskDay").log(Level.SEVERE, null, ex);
        }
    }
    public void saveData(){
        /**
         * saves current data 
         * 
         */
        
        String strSQL = "SELECT tbl_111_taskdays.* " +
                "FROM tbl_111_taskdays  " +
                "WHERE(tbl_111_taskdays.Key = '" + key + "');";
        
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                rs.updateString("Key",key);
                rs.updateDate("Date",MyDates.JavaDateToSQLasDate(taskDate));
                rs.updateString("TaskKey", task.getKey());
                rs.updateBoolean("Completed", completed);
                rs.updateTimestamp("DateCompleted",MyDates.JavaDateTimeToSQLasDate(dateCompleted));                                
                rs.updateString("UIDCompletedBy",completedBy.getUID());
                rs.updateString("Notes",Utilities.RemoveNonAlpha(notes));

                
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                //generate key
                key = Utilities.GenerateKey();
                
                rs.updateString("Key",key);
                rs.updateDate("Date",MyDates.JavaDateToSQLasDate(taskDate));
                rs.updateString("TaskKey", task.getKey());
                rs.updateBoolean("Completed", completed);
                rs.updateTimestamp("DateCompleted",MyDates.JavaDateTimeToSQLasDate(dateCompleted));                                
                rs.updateString("UIDCompletedBy",completedBy.getUID());
                rs.updateString("Notes",Utilities.RemoveNonAlpha(notes));
                
                rs.insertRow();
            }
            rs.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger("saveTaskDay").log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createTaskDaysList(Task task){
        
        String strSQL;
        String tempKey;
        
        for (Date loopDate=task.getFirstDate(); !loopDate.after(task.getLastDate());loopDate=MyDates.incrementDate(loopDate, 1, 1)){
            strSQL = "SELECT tbl_111_taskdays.* "+
                    "FROM tbl_111_taskdays " +
                    "WHERE (((tbl_111_taskdays.TaskKey)= '" +task.getKey() + "') "+
                        "AND ((tbl_111_taskdays.Date) = " + MyDates.JavaDateToSQLasString(loopDate) + "));";
             try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists so no need to create anything
                
            }else{
                // no data exists so create new record
                
                rs.moveToInsertRow();
                //generate key
                tempKey = Utilities.GenerateKey();
                
                rs.updateString("Key",tempKey);
                rs.updateDate("Date",MyDates.JavaDateToSQLasDate(loopDate));
                rs.updateString("TaskKey", task.getKey());
                rs.updateBoolean("Completed", false);
                rs.updateTimestamp("DateCompleted",null);                                
                rs.updateString("UIDCompletedBy",null);
                rs.updateString("Notes","");
                
                rs.insertRow();
            }
            rs.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger("saveTaskDay").log(Level.SEVERE, null, ex);
        }
        }
    }
    public static TaskDay[] loadIncompleteTasks(String urn, Boolean futureOnly){
        /**
         * loads individual user
         *  @param activeOnly a flag to indicate if all users or only active ones are loaded
         */
         String strSQL;
         TaskDay[] retVar = new TaskDay[0];
         
         if (futureOnly){
             strSQL = "SELECT tbl_110_tasks.*, tbl_111_taskdays.* " +
                "FROM tbl_110_tasks INNER JOIN tbl_111_taskdays ON tbl_110_tasks.Key = tbl_111_taskdays.TaskKey  " +
                "WHERE (((tbl_110_tasks.URN)='"+urn+"') AND ((tbl_111_taskdays.Date)<="+MyDates.JavaDateToSQLasString(MyDates.CurrentDate())+") AND ((tbl_111_taskdays.Completed)=1)) " +
                "ORDER BY tbl_111_taskdays.Date;";
         }else{
              strSQL = "SELECT tbl_110_tasks.*, tbl_111_taskdays.* " +
                "FROM tbl_110_tasks INNER JOIN tbl_111_taskdays ON tbl_110_tasks.Key = tbl_111_taskdays.TaskKey  " +
                "WHERE (((tbl_110_tasks.URN)='"+urn+"') AND ((tbl_111_taskdays.Completed)=1)) " +
                "ORDER BY tbl_111_taskdays.Date;";
         }
                    
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new TaskDay[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                    retVar[LoopVar] = new TaskDay();
                    retVar[LoopVar].key  = rs.getString("Key");
                    retVar[LoopVar].taskDate = rs.getDate("Date");
                    retVar[LoopVar].task = new Task(rs.getString("TaskKey"));
                    retVar[LoopVar].completed = rs.getBoolean("Completed");
                    retVar[LoopVar].dateCompleted = rs.getTimestamp("DateCompleted");
                    retVar[LoopVar].completedBy = new User(rs.getString("UIDCompletedBy"));
                    retVar[LoopVar].notes = Utilities.ReplaceNonAlpha(rs.getString("Notes"));                      
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadIncompleteTasks").log(Level.SEVERE, null, ex);
            }
        return retVar;                       
    }
            
}
