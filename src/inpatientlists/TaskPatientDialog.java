

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ross sellars
 * @created 26/05/2013 19:05
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
        this.jChkIncCompleted.setSelected(false);
        this.jChkIncludePast.setSelected(false);
        
        this.jLblPatient.setText(patient.getOneLineDetails());
        this.jBtnEdit.setEnabled(false);
        
        
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
         * loads data from memory (Retvar) into appropriate text/lable etc locations
         */
        //TaskPatient pre
                       
       
                        
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
        
        jCboTaskType.removeAllItems();
        // generate list
        int defaultIndex = -1; 
        int tempCount=0;
        
        String[] tempArray = Utilities.loadUnits();
        
        for (int i = 0;i<tempArray.length;i++){
            tempCount=i;
            jCboTaskType.insertItemAt(tempArray[i], i);            
        }
        jCboTaskType.insertItemAt("all",tempCount+1);
        
        // mark all as selected        
        this.jCboTaskType.setSelectedIndex(tempCount+1);
              
    }
    class MyTableModel extends DefaultTableModel {
        /*
         * this is just to override the ability to select cells
         */
        @Override
        public boolean isCellEditable(int row, int col) {
            // makes cells not editable, default is true in DefaultTableModel
               return false;
        }
    }
    
    private void initComponents() {

        jLblTaskType = new javax.swing.JLabel();
        jChkIncludePast = new javax.swing.JCheckBox();
        jLblPast = new javax.swing.JLabel();
        jChkIncCompleted = new javax.swing.JCheckBox();
        jLblCompleted = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblTasks = new javax.swing.JTable();
        jBtnClose = new javax.swing.JButton();
        jBtnEdit = new javax.swing.JButton();
        jLblPatient = new javax.swing.JLabel();
        jCboTaskType = new javax.swing.JComboBox();
        jBtnNew = new javax.swing.JButton();

        jLblTaskType.setText("Task type:");

        jChkIncludePast.setText("");

        jLblPast.setText("Include past tasks:");

        jChkIncCompleted.setText("");

        jLblCompleted.setText("Include completed tasks:");

        jTblTasks.setModel(new MyTableModel());
        jTblTasks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jTblTasks);

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

        jLblPatient.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblPatient.setText("Patient");

        jCboTaskType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jBtnNew.setText("New Task");
        jBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .addComponent(jLblPatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLblCompleted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblTaskType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLblPast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jChkIncCompleted)
                            .addComponent(jChkIncludePast)
                            .addComponent(jCboTaskType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnEdit)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblPatient)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTaskType)
                    .addComponent(jCboTaskType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jChkIncludePast)
                    .addComponent(jLblPast))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jChkIncCompleted)
                    .addComponent(jLblCompleted))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnClose)
                    .addComponent(jBtnEdit)
                    .addComponent(jBtnNew))
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }//  
    
    private void jBtnNewActionPerformed(java.awt.event.ActionEvent evt) {                                                
        //
    }
    private void jBtnEditActionPerformed(java.awt.event.ActionEvent evt) {                                                
        //
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
    private javax.swing.JComboBox jCboTaskType;
    private javax.swing.JCheckBox jChkIncCompleted;
    private javax.swing.JCheckBox jChkIncludePast;
    private javax.swing.JLabel jLblCompleted;
    private javax.swing.JLabel jLblPast;
    private javax.swing.JLabel jLblPatient;
    private javax.swing.JLabel jLblTaskType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblTasks;
    private Task task;
    private boolean retVar;
    private Patients patient;
    // End of variables declaration                 
}
