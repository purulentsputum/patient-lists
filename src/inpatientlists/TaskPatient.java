

package inpatientlists;

/**
 *
 * @author ross sellars
 * @created 09/06/2013 21:55
 */
public class TaskPatient {
    /**
     * combination of task, patient, and latest admission
     * used for TaskDateDialog.java
     */
    private Task task;
    private Patients patient;
    private Admissions admission;
    
    TaskPatient(){
        task = new Task();
        patient = new Patients();
        admission = new Admissions();
    }
    TaskPatient(Task task){
        task = new Task(task);
        patient = new Patients(task.getURN());
        admission = Admissions.loadLatestPatientAdmissions(task.getURN());
    }
    TaskPatient(TaskPatient taskPatient){
        //defensive copy
        task = new Task(taskPatient.getTask());
        patient = new Patients(taskPatient.getPatient());
        admission = new Admissions(taskPatient.getAdmission());
    }
    
    public Task getTask(){
        return task;
    }
    public Patients getPatient(){
        return patient;
    }
    public Admissions getAdmission(){
        return admission;
    }
    public static TaskPatient[] loadTasksOnADate(java.util.Date date,String taskTypeKey ){
        Task taskList[] = Task.loadTasksOnADay(date,taskTypeKey);
        return getPatientTasks(taskList);
    }
            
    public static TaskPatient[] loadTasksOnADate(java.util.Date date){        
        Task taskList[] = Task.loadTasksOnADay(date);
        return getPatientTasks(taskList);
        
    }
     private static TaskPatient[] getPatientTasks(Task[] taskList){
         TaskPatient retVar[] = new TaskPatient[0];
         retVar = new TaskPatient[taskList.length];
         int i=-1;
         for(Task task:taskList){
             retVar[++i] = new TaskPatient();
             retVar[i].task = new Task(task);
             retVar[i].patient = new Patients(task.getURN());
             retVar[i].admission = Admissions.loadLatestPatientAdmissions(task.getURN());
         }
         return retVar;
     }
    
}
