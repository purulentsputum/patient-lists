
package inpatientlists;

import java.awt.Frame;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ross sellars
 * @created 26/05/2013 19:15
 */
public class TaskDateDialog extends JDialog{
 
    /**
     * displays all tasks for a selected patient
     * 
     */
    public TaskDateDialog(Frame aFrame) {
        /*
         * creates new user
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
       
        
        StartUp();              
    }

   public boolean ReturnCompleted() {
        setVisible(true);
        return retVar;
    }
   

    private void StartUp() {
        // the general initiation for frame

        filterDate = MyDates.CurrentDate();
        task = new TaskPatient();
        initComponents();
        PopulateTaskTypeCombo();
        populateTable();
                                
        pack();
        setLocationRelativeTo(null);
        this.jBtnEdit.setEnabled(false);  //only allow edits when task is selected
        
        
    }

    private void FinishUp() {
        getResults();
        
        retVar = true;

        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }
    private void setResults(){
        /*
         * loads data from memory) into appropriate text/lable etc locations
         */                               
    }
    private void getResults(){
        /*
         * loads data into Data 
         */ 
    }
    private void setCalculatedValues(){
        //reload data into the variables (incase there has been any change)
        //used mainly for calculated values like percent predicteds etc
        getResults(); 
        // calculate labels
        
        // nothing to be done here
         
    }
    
    private void PopulateTaskTypeCombo(){
        jCboTaskType.setVisible(false);
        jCboTaskType.removeAllItems();
        // generate list
        int defaultIndex = -1; 
        int tempCount=-1;
               
        for (TaskType tt: TaskType.values() ){
            tempCount++;
            jCboTaskType.insertItemAt(tt.getDesc(), tempCount);            
        }
        jCboTaskType.insertItemAt("all",tempCount+1);
        
        // mark all as selected        
        this.jCboTaskType.setSelectedItem("all");
        this.jCboTaskType.setVisible(true);
        
    }
    
    private void populateTable(){
         // hide me while I repopulate so that the listener can ignore me
        jTblData.setVisible(false);
        
        int intRowCount = 0;

        MyTableModel tm = (MyTableModel)jTblData.getModel();

        // set listener for selecting rows
        RowListener listen = new RowListener();
        jTblData.getSelectionModel().addListSelectionListener(listen);


        // first clear the table
        int intTblRows = tm.getRowCount();
        for (int i = 0; i < intTblRows ;i++) {
            tm.removeRow(0);
        }
        // set headers
        String[] columnNames = {"Key","Patient","Ward","Type","Task"," "};
        tm.setColumnIdentifiers(columnNames);

        DataList = new TaskPatient[0];
        strSQLDesc = "";
        
        if (this.jCboTaskType.getSelectedIndex()>-1){
            if("all".equals(this.jCboTaskType.getSelectedItem())){
                // all task types
                DataList = TaskPatient.loadTasksOnADate(this.jDateTask.getDate());
                strSQLDesc = "All tasks on " + MyDates.ConvertDateToString(jDateTask.getDate());
            }else{
                //selected task type
                DataList = TaskPatient.loadTasksOnADate(jDateTask.getDate(), TaskType.getTaskTypeFromDesc((String)this.jCboTaskType.getSelectedItem()).getKey());
                strSQLDesc = TaskType.getTaskTypeFromDesc((String)this.jCboTaskType.getSelectedItem()).getDesc()+" tasks on " + MyDates.ConvertDateToString(jDateTask.getDate());
            }
        }                

        String[] rowData;
        for (TaskPatient data:DataList){
            rowData = new String[6];
            rowData[0] = data.getTask().getKey();
            rowData[1] = data.getPatient().getOneLineDetails();
            rowData[2] = data.getAdmission().getWardBed();
            rowData[3] = data.getTask().getTaskType().getDesc();
            rowData[4] = data.getTask().getTaskDesc();
            rowData[5] = data.getTask().getUsersOneLine(); 
            tm.addRow(rowData);
            // fixes issue with change is height not displaying the last row(s)
            jTblData.setPreferredSize(null);
            
        }
                
        //jLblCount.setText(Integer.toString(intRowCount) + " " +Utilities.PleuralEntries("entry",intRowCount));

        /*
         * not the best, i know, but it works for me
         * this hides the Key column but leaves it there so I can access its data
         */
        setColumnWidth("Key",0,true);
        setColumnWidth("Patient",150,false);
        setColumnWidth("Ward",50,true);
        setColumnWidth("Type",125,true);
        

        jTblData.setVisible(true);
        
    }
    private void setColumnWidth(String colName, int colWidth, Boolean setMax){
        jTblData.getColumn(colName).setWidth(colWidth);
        jTblData.getColumn(colName).setMinWidth(colWidth);
        if(setMax){
            jTblData.getColumn(colName).setMaxWidth(colWidth);
        }
    }
    
    private void ListenerActivated(int intDataIndex){                   
            
       task = new TaskPatient(DataList[intDataIndex]);
       this.jBtnEdit.setEnabled(true);
                
    }
    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {                 
            if (!event.getValueIsAdjusting()) {
                if (jTblData.isVisible()) {
                    if (jTblData.getSelectedRowCount()>0) {                        
                        // row has been selected                        
                        ListenerActivated(jTblData.getSelectedRow());                       
                    }
                }
            }
        }
    }//end rowlistener
    
    class MyTableModel extends DefaultTableModel {
        /*
         * this is just to override the ability to edit select cells
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            // makes cells not editable, default is true in DefaultTableModel
               return false;
        }
    }
    
    private void initComponents() {

        jPanMain = new javax.swing.JPanel();
        jPanButtons = new javax.swing.JPanel();
        jPanData = new javax.swing.JPanel();
        
        jLblTaskType = new javax.swing.JLabel(); 
        jLblTaskDate = new javax.swing.JLabel();
        jBtnClose = new javax.swing.JButton();
        jBtnEdit = new javax.swing.JButton();
        jBtnExport = new javax.swing.JButton();
        jDateTask = new com.toedter.calendar.JDateChooser();
        jCboTaskType = new javax.swing.JComboBox();
        jTblData = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();

        jLblTaskDate.setText("Task Date:");
        jLblTaskType.setText("Task type:");
        
        jTblData.setModel(new MyTableModel());
        jTblData.setEditingColumn(0);
        jTblData.setEditingRow(0);
        //jTblData.setMaximumSize(new java.awt.Dimension(225, 355));
        jTblData.setMinimumSize(new java.awt.Dimension(225, 32));
        jTblData.setPreferredSize(new java.awt.Dimension(225, 355));
        jTblData.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTblData);

        jBtnClose.setText("Close");
        jBtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCloseActionPerformed(evt);
            }
        });

        jBtnEdit.setText("Edit Selected");
        jBtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditActionPerformed(evt);
            }
        });        

        jBtnExport.setText("Export List");
        jBtnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExportActionPerformed(evt);
            }
        });
        
        jDateTask.setDate(MyDates.CurrentDate());
        jDateTask.setDateFormatString(MyDates.DefaultDateFormat);
        
        jDateTask.getDateEditor().addPropertyChangeListener(new java.beans.PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                jDateTaskTextChanged();
            }            
            }                
        );
        
        jCboTaskType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2"}));
        jCboTaskType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboTaskTypeActionPerformed(evt);
            }
        });
        
        javax.swing.GroupLayout jPanButtonsLayout = new javax.swing.GroupLayout(jPanButtons);
        jPanButtons.setLayout(jPanButtonsLayout);
        
        jPanButtonsLayout.setHorizontalGroup(
                jPanButtonsLayout.createSequentialGroup()                
                //.addGroup(jPanButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnClose) 
                    .addGap(20,20,20)    
                    .addComponent(jBtnEdit)
                    .addGap(20,20,20)
                    .addComponent(jBtnExport)
                //)
                .addGap(20, 20, 20)
        );

        jPanButtonsLayout.setVerticalGroup(
                jPanButtonsLayout.createSequentialGroup()
                .addGroup(jPanButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnClose)                     
                    .addComponent(jBtnEdit)                    
                    .addComponent(jBtnExport)
                )               
                         
        );
        
        javax.swing.GroupLayout jPanDataLayout = new javax.swing.GroupLayout(jPanData);
        jPanData.setLayout(jPanDataLayout);
        
        jPanDataLayout.setHorizontalGroup(
                jPanDataLayout.createSequentialGroup()
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblTaskType)                
                )
                .addGap(20, 20, 20)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jDateTask) 
                .addComponent(jCboTaskType)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addComponent(jPanButtons)
                )
                .addGap(20, 20, 20)
        );

        jPanDataLayout.setVerticalGroup(
                jPanDataLayout.createSequentialGroup()
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jDateTask)
                )               
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblTaskType)
                    .addComponent(jCboTaskType)
                )
                .addGap(6,6,6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, Short.MAX_VALUE)
                ) 
                .addGap(6,6,6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPanButtons)
                ) 
        );
        
      
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                .addComponent(jPanData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 20)
                )
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
        
                   
        
    }//  
    
    private void jDateTaskTextChanged(){
        
        if((jDateTask.getDate()!=null)&&(!filterDate.equals(jDateTask.getDate()))){
           //new date entered
            filterDate = new java.util.Date (jDateTask.getDate().getTime());
            populateTable();
        }
        
    }
    
    private void jCboTaskTypeActionPerformed(java.awt.event.ActionEvent evt) {                                                
        if(this.jCboTaskType.isVisible()){
            // cbo is not visible when refreshing contents - prevents multiple refreshes
            populateTable();
        }
        
    }
    private void jBtnExportActionPerformed(java.awt.event.ActionEvent evt) {                                                
        
        Object[] options = {"Word", "XML", "HTML"};
        String retVar = (String)JOptionPane.showInputDialog(
                null,
                "Would you like to save your list as:",
                "Export Data",
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the fields fot the combo
                "Word"); //default 
        if ((retVar != null)&&(retVar.length()>0)){
            switch (retVar){
                case "Word":
                    ExportList.TaskList.Word(DataList, strSQLDesc);
                    break;
                case "XML":
                    ExportList.TaskList.XML(DataList, strSQLDesc);
                    break;
                case "HTML":
                    ExportList.TaskList.HTML(DataList, strSQLDesc);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "You selected " + retVar + " - but I haven't got around to this yet");
                    break;
            }
            
        }
        
        
    }
    private void jBtnEditActionPerformed(java.awt.event.ActionEvent evt) {                                                
        TaskDialogEdit giveItToMe = new TaskDialogEdit(null,task.getTask());
        boolean howDidItGo = giveItToMe.ReturnCompleted();
        if (howDidItGo){
            // returned true - ie saved new task
            populateTable();
        }
    }
    private void jBtnCloseActionPerformed(java.awt.event.ActionEvent evt) {                                                
        retVar = false; // did not update
        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }

    // Variables declaration                      
    private javax.swing.JButton jBtnClose;
    private javax.swing.JButton jBtnEdit;
    private javax.swing.JButton jBtnExport;
    
    private javax.swing.JTable jTblData;
    private javax.swing.JComboBox jCboTaskType;
    
    private javax.swing.JScrollPane jScrollPane1;
    
    private com.toedter.calendar.JDateChooser jDateTask;
    private javax.swing.JLabel jLblTaskDate;
    private javax.swing.JLabel jLblTaskType;
    
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanButtons;
    private javax.swing.JPanel jPanData;
    
    private String strSQLDesc;
    private TaskPatient[] DataList;
    private TaskPatient task;
    private boolean retVar;
    private Patients patient;
    private java.util.Date filterDate; //used to compare for change
    // End of variables declaration  
}
