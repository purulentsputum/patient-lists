

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author rosssellars
 * @created 07/06/2013 10:46
 */
public class TaskDialogEdit extends JDialog{
    /**
     * data edit/entry for tasks
     */
    
    public TaskDialogEdit(Frame aFrame, Task task) {
        /*
         * new task for given patient
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
        
        Data = new Task(task);
        patient = new Patients(Data.getURN());
        StartUp();
        
        
    }    

    public Task ReturnData() {
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
        setResults();
        
        
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
        jLblHeadCol1.setText(patient.getOneLineDetails());
        jTxtTask.setText(Data.getTaskDesc());
        jCboTaskType.setSelectedItem(Data.getTaskType().getDesc());
        jLblTaskDate.setText(MyDates.ConvertDateToString(Data.getDate()));              
        jLblCreatedBy.setText(Data.getCreatedBy().getName());
        jLblDateCreated.setText(MyDates.ConvertDateTimeToString(Data.getDateCreated(),MyDates.DefaultDateTimeFormat));
        jLblEditedBy.setText(Data.getEditedBy().getName());
        jLblDateEdited.setText(MyDates.ConvertDateTimeToString(Data.getDateEdited(),MyDates.DefaultDateTimeFormat));
        jLblCompletedBy.setText(Data.getCompletedBy().getName());
        jLblDateCompleted.setText(MyDates.ConvertDateTimeToString(Data.getDateCompleted(),MyDates.DefaultDateTimeFormat));
        jChkCompleted.setSelected(Data.isCompleted());
        if(Data.isCompleted()){
            // allow no further editing
            this.jTxtTask.setEnabled(false);
            this.jCboTaskType.setEnabled(false);
            this.jBtnComplete.setEnabled(false);
            this.jBtnSave.setEnabled(false);
        }
        if(MyDates.incrementDate(Data.getDate(),1,1).before(MyDates.CurrentDate())){
            //no editing if past due date -give 1 day leeway 
            this.jTxtTask.setEnabled(false);
            this.jCboTaskType.setEnabled(false);
            this.jBtnSave.setEnabled(false);
        }
        
                        
    }
    private void getResults(){
        /*
         * loads data into Data 
         */        
               
        ;
        Data.setTaskDesc(this.jTxtTask.getText());
        Data.setTaskType(TaskType.getTaskTypeFromDesc((String)jCboTaskType.getSelectedItem()));
        Data.setDateEdited(MyDates.CurrentDateTime());
        Data.setEditedBy(User.CurrentUser);

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
        jBtnComplete = new javax.swing.JButton();
        
        jLblHeadCol1 = new javax.swing.JLabel();
                         
        jLblRow1 = new javax.swing.JLabel();
        jLblRow2 = new javax.swing.JLabel();
        jLblRow3 = new javax.swing.JLabel();
        jLblRow4 = new javax.swing.JLabel();
        jLblRow5 = new javax.swing.JLabel();
        jLblRow6 = new javax.swing.JLabel();
        jLblRow7 = new javax.swing.JLabel();
        jLblRow8 = new javax.swing.JLabel();
        jLblRow9 = new javax.swing.JLabel();
        jLblRowA = new javax.swing.JLabel();
        
        
        jTxtTask = new javax.swing.JTextField();    
        jCboTaskType = new javax.swing.JComboBox();
        jLblTaskDate = new javax.swing.JLabel();                     
        jLblCreatedBy = new javax.swing.JLabel();
        jLblDateCreated = new javax.swing.JLabel();
        jLblEditedBy = new javax.swing.JLabel();
        jLblDateEdited = new javax.swing.JLabel();
        jLblCompletedBy = new javax.swing.JLabel();
        jLblDateCompleted = new javax.swing.JLabel();
        jChkCompleted = new javax.swing.JCheckBox();
        
        jPanData.setBorder(javax.swing.BorderFactory.createTitledBorder("Edit Task"));

        jLblRow1.setText("Task Note:");
        jLblRow2.setText("Task Type:");
        jLblRow3.setText("Task Date:");
        jLblRow4.setText("Created by:");
        jLblRow5.setText("Date created:");
        jLblRow6.setText("Created by:");
        jLblRow7.setText("Date created:");
        jLblRow8.setText("Created by:");
        jLblRow9.setText("Date created:");
        jLblRowA.setText("Completed:");
          
        

        jTxtTask.setText(" ");
        jTxtTask.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTask.selectAll();
            }            
        });

        
        jCboTaskType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        jLblTaskDate.setText("");              
        jLblCreatedBy.setText(" ");
        jLblDateCreated.setText(MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime(),MyDates.DefaultDateTimeFormat));
        jLblEditedBy.setText(" ");
        jLblDateEdited.setText(MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime(),MyDates.DefaultDateTimeFormat));
        jLblCompletedBy.setText(" ");
        jLblDateCompleted.setText(MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime(),MyDates.DefaultDateTimeFormat));
        jChkCompleted.setText("");
        
                
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
        
        jBtnComplete.setText("Complete");
        jBtnComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCompleteActionPerformed(evt);
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
                .addComponent(jLblRow7)
                .addComponent(jLblRow8)
                .addComponent(jLblRow9)
                .addComponent(jLblRowA)
                .addComponent(jBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(20, 20, 20)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblHeadCol1)
                .addComponent(jTxtTask, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCboTaskType, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLblTaskDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLblCreatedBy)                
                .addComponent(jLblDateCreated) 
                .addComponent(jLblEditedBy)                
                .addComponent(jLblDateEdited)
                .addComponent(jLblCompletedBy)                
                .addComponent(jLblDateCompleted)
                .addComponent(jChkCompleted)
                .addComponent(jBtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(20, 20, 20)
                .addComponent(jBtnComplete, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                );

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
                .addComponent(jLblTaskDate)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow4)
                .addComponent(jLblCreatedBy)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow5)
                .addComponent(jLblDateCreated)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow6)
                .addComponent(jLblEditedBy)
                ) 
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow7)
                .addComponent(jLblDateEdited)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow8)
                .addComponent(jLblCompletedBy)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow9)
                .addComponent(jLblDateCompleted)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRowA)
                .addComponent(jChkCompleted)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSave)
                    .addComponent(jBtnCancel)
                    .addComponent(jBtnComplete))
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

    

    private void jBtnSaveActionPerformed(java.awt.event.ActionEvent evt) {
        FinishUp();
    }
    private void jBtnCompleteActionPerformed(java.awt.event.ActionEvent evt) {
        String strTemp = "This will mark the task as completed.  Any oher changes will not be saved.  Do you wish to continue?";
        int n = JOptionPane.showConfirmDialog(  null ,strTemp,"Complete Task",JOptionPane.YES_NO_OPTION );
                 //0 = yes, 1 = No
                if (n==0) {
                    // yes selected
                    Data.setCompleted(true);
                    Data.setCompletedBy(User.CurrentUser);
                    Data.setDateCompleted(MyDates.CurrentDateTime());
                    Data.saveData();
                    RetVar = true; //  updated
                    setVisible(false); // returns control to initialting method (ReturnValue)
                    dispose();
                }                
    }

    private void jBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        Data = new Task(); // return blank stuff       
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
    private javax.swing.JLabel jLblRow7;
    private javax.swing.JLabel jLblRow8;
    private javax.swing.JLabel jLblRow9;
    private javax.swing.JLabel jLblRowA;
    
    private javax.swing.JTextField jTxtTask;    
    private javax.swing.JComboBox jCboTaskType;
    private javax.swing.JLabel jLblTaskDate;            
    private javax.swing.JLabel jLblCreatedBy;
    private javax.swing.JLabel jLblDateCreated;
    private javax.swing.JLabel jLblEditedBy;
    private javax.swing.JLabel jLblDateEdited; 
    private javax.swing.JLabel jLblCompletedBy;
    private javax.swing.JLabel jLblDateCompleted;
    private javax.swing.JCheckBox jChkCompleted;
        
    private javax.swing.JButton jBtnCancel;
    private javax.swing.JButton jBtnSave; 
    private javax.swing.JButton jBtnComplete;
       
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanReport;
    private javax.swing.JPanel jPanData;    
    
    Task Data;
    boolean RetVar;
    Patients patient;
    
    // End of variables declaration
}
