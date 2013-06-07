

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rosssellars
 * @created 07/06/2013 11:06
 */
//TODO error trapping for dates - last date not > first, min date today, max date 1 year
public class TaskNew {
    private Date firstDate;
    private Date lastDate;
    private Task task;
    
    
     TaskNew(){
        loadDefaults();
    }
    
    TaskNew (TaskNew value){
        //defensive copy
    }
    private void loadDefaults(){
       
        firstDate=new Date(MyDates.CurrentDate().getTime());
        lastDate=new Date(firstDate.getTime());
        task = new Task();               
    }
    public final Date getFirstDate(){
        return firstDate;
    }
    public Date getLastDate(){
        return lastDate;
    }    
    public Task getTask(){
        return task;
    }
        
    public void setFirstDate(Date firstDate){
        this.firstDate = new Date(firstDate.getTime());
    }
    public void setLastDate(Date lastDate){
        this.lastDate = new Date(lastDate.getTime());
    }
    public void setTask(Task task){
        this.task = new Task(task);
    }
    public void setTask(String key){
        this.task = new Task(key);
    }
    
    
    public void saveData(){
        // this will only be called when a new task is created
        String strSQL;
        String tempKey;
        
        for (Date loopDate=firstDate; !loopDate.after(lastDate);loopDate=MyDates.incrementDate(loopDate, 1, 1)){
            strSQL = "SELECT tbl_110_tasks.* "+
                    "FROM tbl_110_tasks ;" ;
             try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            
                // create new record
                
                rs.moveToInsertRow();
                //generate key
                tempKey = Utilities.GenerateKey();
                
                rs.updateString("Key",tempKey);
                rs.updateDate("Date",MyDates.JavaDateToSQLasDate(loopDate));                
                rs.updateString("URN",task.getURN());
                rs.updateString("TaskType",task.getTaskType().getKey());
                rs.updateString(Utilities.RemoveNonAlpha("Tasks"),task.getTaskDesc());
                rs.updateString("UIDCreatedBy",task.getCreatedBy().getUID());
                rs.updateString("UIDEditedBy",task.getEditedBy().getUID());
                rs.updateTimestamp("DateCreated",MyDates.JavaDateTimeToSQLasDate(task.getDateCreated()));
                rs.updateTimestamp("DateEdited",MyDates.JavaDateTimeToSQLasDate(task.getDateEdited()));
                rs.updateBoolean("Completed", false);
                rs.updateTimestamp("DateCompleted",null);                                
                rs.updateString("UIDCompletedBy",null);
                                
                rs.insertRow();
           
            rs.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger("saveNewTask").log(Level.SEVERE, null, ex);
        }
        }
    }
    
}
