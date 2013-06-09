

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;
import com.toedter.calendar.JCalendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author rosssellars
 * @created 07/06/2013 10:01
 */
public class TaskDialogNew extends JDialog{
    /**
     * data edit/entry for tasks
     */
    
    public TaskDialogNew(Frame aFrame, Patients patient) {
        /*
         * new task for given patient
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
        
        this.patient = new Patients(patient);
        StartUp();
        
        
    }    

    public TaskNew ReturnData() {
        setVisible(true);
        return Data;
    }
   public boolean ReturnCompleted() {
        setVisible(true);
        return RetVar;
    }
   
    private void StartUp() {
        // the general initiation for frame

        initComponents();
        PopulateTaskTypeCombo();
        jLblHeadCol1.setText(patient.getOneLineDetails());        
        
        Data = new TaskNew();
        
        pack();
        setLocationRelativeTo(null);        
        
    }

    private void FinishUp() {
        getResults();
        Data.saveData();        
        RetVar = true;

        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }
    private void setResults(){
        /*
         * loads data from memory (Retvar) into appropriate text/lable etc locations
         */
        //Task pre
                       
        
                        
    }
    private void getResults(){
        /*
         * loads data into Data 
         */        
                
        Data = new TaskNew();
        Data.setFirstDate(jDateFirst.getDate());
        Data.setLastDate(jDateLast.getDate());
        Data.getTask().setTaskType( TaskType.getTaskTypeFromDesc((String)jCboTaskType.getSelectedItem()));
        Data.getTask().setTaskDesc(this.jTxtTask.getText());
        Data.getTask().setURN(patient.getURN());
        // the rest stay as new defaults
                              
    }
    
    private void PopulateTaskTypeCombo(){
        
        jCboTaskType.removeAllItems();
        // generate list
        int defaultIndex = -1; 
        int tempCount=-1;
                 
        for (TaskType tt: TaskType.values() ){
            tempCount++;
            jCboTaskType.insertItemAt(tt.getDesc(), tempCount);            
        }
                
        // mark default as selected        
        jCboTaskType.setSelectedItem(TaskType.getDefault().getDesc());   
        
    }
    
    
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Initialization Code">
    private void initComponents() {

        jPanMain = new javax.swing.JPanel();
        jPanData = new javax.swing.JPanel();
        jPanReport = new javax.swing.JPanel();
        
        jBtnSave = new javax.swing.JButton();
        jBtnCancel = new javax.swing.JButton();
        
        jLblHeadCol1 = new javax.swing.JLabel();
                         
        jLblRow1 = new javax.swing.JLabel();
        jLblRow2 = new javax.swing.JLabel();
        jLblRow3 = new javax.swing.JLabel();
        jLblRow4 = new javax.swing.JLabel();
        jLblRow5 = new javax.swing.JLabel();
        jLblRow6 = new javax.swing.JLabel();
        
        
        jTxtTask = new javax.swing.JTextField();    
        jCboTaskType = new javax.swing.JComboBox();
        jDateFirst = new com.toedter.calendar.JDateChooser();
        jDateLast = new com.toedter.calendar.JDateChooser();              
        jLblCreatedBy = new javax.swing.JLabel();
        jLblDateCreated = new javax.swing.JLabel();
        
        jPanData.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Task"));

        jLblRow1.setText("Task Note:");
        jLblRow2.setText("Task Type:");
        jLblRow3.setText("Start Date:");
        jLblRow4.setText("End Date:");
        jLblRow5.setText("Created by:");
        jLblRow6.setText("Date created:");
             
          
        

        jTxtTask.setText(" ");
        jTxtTask.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTask.selectAll();
            }            
        });

        
        jCboTaskType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        jDateFirst.setDate(MyDates.CurrentDate());
        jDateLast.setDate(MyDates.CurrentDate()); 
        jDateFirst.setDateFormatString(MyDates.DefaultDateFormat);
        jDateLast.setDateFormatString(MyDates.DefaultDateFormat);
        
        jLblCreatedBy.setText(User.CurrentUser.getName());
        jLblDateCreated.setText(MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime(),MyDates.DefaultDateTimeFormat));
                
        jLblHeadCol1.setText("Task Details:");

        jBtnSave.setText("Save");
        jBtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSaveActionPerformed(evt);
            }
        });

        jBtnCancel.setText("Cancel");
        jBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanDataLayout = new javax.swing.GroupLayout(jPanData);
        jPanData.setLayout(jPanDataLayout);


        jPanDataLayout.setHorizontalGroup(
                jPanDataLayout.createSequentialGroup()
                
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow1)
                .addComponent(jLblRow2)
                .addComponent(jLblRow3)
                .addComponent(jLblRow4)
                .addComponent(jLblRow5)
                .addComponent(jLblRow6)                
                .addComponent(jBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(20, 20, 20)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblHeadCol1)
                .addComponent(jTxtTask, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCboTaskType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jDateFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jDateLast, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLblCreatedBy)                
                .addComponent(jLblDateCreated)                 
                .addComponent(jBtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(20, 20, 20));

        jPanDataLayout.setVerticalGroup(
                jPanDataLayout.createSequentialGroup()
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLblHeadCol1))
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow1)
                .addComponent(jTxtTask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow2)
                .addComponent(jCboTaskType)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow3)
                .addComponent(jDateFirst)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow4)
                .addComponent(jDateLast)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow5)
                .addComponent(jLblCreatedBy)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow6)
                .addComponent(jLblDateCreated)
                )                
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSave)
                    .addComponent(jBtnCancel))
                );
      
        /*
         * main panel
         */

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jPanData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));
    }// </editor-fold>

    private void jBtnReportActionPerformed(java.awt.event.ActionEvent evt) {
        getResults(); //this loads teh data into teh variable (incase there has been any change)
        //jTxtNotes.setText(Data.calcDraftReport());
    }

    private void jBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        getResults();
        FinishUp();
    }

    private void jBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        Data = new TaskNew(); // return blank stuff       
        RetVar = false; // did not update
        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }

        
    // 
    
    //Variables declaration 
    private javax.swing.JLabel jLblHeadCol1;    
    
    private javax.swing.JLabel jLblRow1;
    private javax.swing.JLabel jLblRow2;
    private javax.swing.JLabel jLblRow3;
    private javax.swing.JLabel jLblRow4;
    private javax.swing.JLabel jLblRow5;
    private javax.swing.JLabel jLblRow6;
    
    private javax.swing.JTextField jTxtTask;    
    private javax.swing.JComboBox jCboTaskType;
    private com.toedter.calendar.JDateChooser jDateFirst;
    private com.toedter.calendar.JDateChooser jDateLast;              
    private javax.swing.JLabel jLblCreatedBy;
    private javax.swing.JLabel jLblDateCreated;       
        
    private javax.swing.JButton jBtnCancel;
    private javax.swing.JButton jBtnSave;    
       
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanReport;
    private javax.swing.JPanel jPanData;    
    
    TaskNew Data;
    boolean RetVar;
    Patients patient;
    
    // End of variables declaration
    
}
