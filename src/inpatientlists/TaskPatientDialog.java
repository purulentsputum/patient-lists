

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ross sellars
 * @created 26/05/2013 19:05
 * @edited 09/06/2013 fixed initial cut and paste code
 */
public class TaskPatientDialog extends JDialog{

    /**
     * displays all tasks for a selected patient
     * 
     */
    public TaskPatientDialog(Frame aFrame, String urn) {
        /*
         * creates new user
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
       
        
        StartUp(urn);              
    }

   public boolean ReturnCompleted() {
        setVisible(true);
        return retVar;
    }
   

    private void StartUp(String urn) {
        // the general initiation for frame

        task = new Task();
        patient = new Patients(urn);
        initComponents();
        PopulateTaskTypeCombo();
        populateTable();
                
        jLblPatient.setText(patient.getOneLineDetails());
        jBtnEdit.setEnabled(false);
                
        pack();
        setLocationRelativeTo(null);
        
        
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
        String[] columnNames = {"Key","Date","Type","Task"," "};
        tm.setColumnIdentifiers(columnNames);

        DataList = new Task[0];
        if (this.jCboTaskType.getSelectedIndex()>-1){
            if("all".equals(this.jCboTaskType.getSelectedItem())){
                // all task types
                DataList = Task.loadAllPatientTasks(patient.getURN(), true);
            }else{
                //selected task type
                DataList = Task.loadSelectedPatientTasks(patient.getURN(), TaskType.getTaskTypeFromDesc((String)this.jCboTaskType.getSelectedItem()).getKey(), true);
            }
        }                

        String[] rowData;
        for (int LoopVar = 0; LoopVar < DataList.length;LoopVar++){
            rowData = new String[5];
            rowData[0] = DataList[LoopVar].getKey();
            rowData[1] = MyDates.ConvertDateToString(DataList[LoopVar].getDate());
            rowData[2] = DataList[LoopVar].getTaskType().getDesc();
            rowData[3] = DataList[LoopVar].getTaskDesc();
            rowData[4] = DataList[LoopVar].getUsersOneLine();                 

            tm.addRow(rowData);
            intRowCount = LoopVar+1;
            // fixes issue with change is height not displaying the last row(s)
            jTblData.setPreferredSize(null);
        }
        
        //jLblCount.setText(Integer.toString(intRowCount) + " " +Utilities.PleuralEntries("entry",intRowCount));

        /*
         * not the best, i know, but it works for me
         * this hides the Key column but leaves it there so I can access its data
         */
        setColumnWidth("Key",0,true);
        setColumnWidth("Date",75,true);
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
                   
            jBtnEdit.setEnabled(true);
            task = new Task(DataList[intDataIndex]);
                
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
        jBtnClose = new javax.swing.JButton();
        jBtnEdit = new javax.swing.JButton();
        jBtnNew = new javax.swing.JButton();
        jLblPatient = new javax.swing.JLabel();
        jCboTaskType = new javax.swing.JComboBox();
        jTblData = new javax.swing.JTable();
         jScrollPane1 = new javax.swing.JScrollPane();

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

        jBtnNew.setText("New Task");
        jBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewActionPerformed(evt);
            }
        });
        
        jLblPatient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblPatient.setText("Patient");

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
                    .addComponent(jBtnNew)
                //)
                .addGap(20, 20, 20)
        );

        jPanButtonsLayout.setVerticalGroup(
                jPanButtonsLayout.createSequentialGroup()
                .addGroup(jPanButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnClose)                     
                    .addComponent(jBtnEdit)                    
                    .addComponent(jBtnNew)
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
                .addComponent(jLblPatient) 
                .addComponent(jCboTaskType)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addComponent(jPanButtons)
                )
                .addGap(20, 20, 20)
        );

        jPanDataLayout.setVerticalGroup(
                jPanDataLayout.createSequentialGroup()
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblPatient)
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
    private void jCboTaskTypeActionPerformed(java.awt.event.ActionEvent evt) {                                                
        if(this.jCboTaskType.isVisible()){
            // cbo is not visible when refreshing contents - prevents multiple refreshes
            populateTable();
        }
        
    }
    private void jBtnNewActionPerformed(java.awt.event.ActionEvent evt) {                                                
        TaskDialogNew giveItToMe = new TaskDialogNew(null,patient);
        boolean howDidItGo = giveItToMe.ReturnCompleted();
        if (howDidItGo){
            // returned true - ie saved new task
            populateTable();
        }
    }
    private void jBtnEditActionPerformed(java.awt.event.ActionEvent evt) {                                                
        TaskDialogEdit giveItToMe = new TaskDialogEdit(null,task);
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
    private javax.swing.JButton jBtnNew;
    
    private javax.swing.JTable jTblData;
    private javax.swing.JComboBox jCboTaskType;
    
    private javax.swing.JScrollPane jScrollPane1;
    
    private javax.swing.JLabel jLblPatient;
    private javax.swing.JLabel jLblTaskType;
    
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanButtons;
    private javax.swing.JPanel jPanData;
    
    private Task[] DataList;
    private Task task;
    private boolean retVar;
    private Patients patient;
    // End of variables declaration                 
}
