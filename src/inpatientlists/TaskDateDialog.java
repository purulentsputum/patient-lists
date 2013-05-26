

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author ross sellars
 * @created 26/05/2013 19:15
 */
public class TaskDateDialog extends JDialog{
    public TaskDateDialog(Frame aFrame) {
        /*
         * creates new user
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
       
        task = new Task();
        StartUp();              
    }

   

    
   public boolean ReturnCompleted() {
        setVisible(true);
        return retVar;
    }
   

    private void StartUp() {
        // the general initiation for frame

        initComponents();
        PopulateWardCombo();
        PopulateUnitCombo();
        PopulateConsultantCombo();
        pack();
        setLocationRelativeTo(null);
        if(TaskDate.CurrentTaskDate.isAdmin()){
            jChkAdmin.setEnabled(true);
            jChkLimited.setEnabled(true);
            jChkLocked.setEnabled(true);
        }else{
            jChkAdmin.setEnabled(false);
            jChkLimited.setEnabled(false);
            jChkLocked.setEnabled(false);
        }
    }

    private void FinishUp() {
        getResults();
        Data.saveData();
        retVar = true;

        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }
    private void setResults(){
        /*
         * loads data from memory (Retvar) into appropriate text/lable etc locations
         */
        //TaskDate pre
                       
        jTxtName.setText(Data.getName() );    
        jCboWard.setSelectedItem(Data.getWard());
        jCboUnit.setSelectedItem(Data.getUnit());
        
        for (int i = 0;i<consultantArray.length;i++){
            if(TaskDate.CurrentTaskDate.getConsultantCode().equals(consultantArray[i].getCode())){
                jCboConsultant.setSelectedIndex(i);
            }
        }
                         
        jLblUID.setText(Data.getUID() );
        jLblDateCreated.setText(MyDates.ConvertDateTimeToString(Data.getDateCreated()));
        jLblDateLastValidLogin.setText(MyDates.ConvertDateTimeToString(Data.getDateLastLogin()) );
        jLblDateLastInvalidLogin.setText(MyDates.ConvertDateTimeToString(Data.getDateLastInvalidLogin()) );
        jLblNumberValidLogins.setText(Integer.toString(Data.getNumberValidLogins()) );
        jLblNumberInvalidLogins.setText(Integer.toString(Data.getNumberInvalidLogins()) );
        jChkAdmin.setSelected(Data.isAdmin()); 
        jChkLocked.setSelected(Data.isLocked());
        jChkLimited.setSelected(Data.isLimited());
                        
    }
    private void getResults(){
        /*
         * loads data into Data 
         */
        //TaskDate
        Data.setName((jTxtName.getText()));
        Data.setAdmin(jChkAdmin.isSelected());
        Data.setLocked(jChkLocked.isSelected());
        Data.setLimited(jChkLimited.isSelected());
        
       
        
    }
    private void setCalculatedValues(){
        //reload data into the variables (incase there has been any change)
        //used mainly for calculated values like percent predicteds etc
        getResults(); 
        // calculate labels
        
        // nothing to be done here
         
    }
    
    
    private void initComponents() {

        jLblTaskType = new javax.swing.JLabel();
        jChkIncCompleted = new javax.swing.JCheckBox();
        jLblCompleted = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblTasks = new javax.swing.JTable();
        jBtnClose = new javax.swing.JButton();
        jBtnEdit = new javax.swing.JButton();
        jCboTaskType = new javax.swing.JComboBox();
        jLblDate = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jLblTaskType.setText("Task type:");

        jChkIncCompleted.setText("jCheckBox2");

        jLblCompleted.setText("Include completed tasks:");

        jTblTasks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTblTasks);

        jBtnClose.setText("Close");

        jBtnEdit.setText("Edit Selected");

        jCboTaskType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLblDate.setText("Date:");

        jTextField1.setText("jTxtDate");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(131, 131, 131)
                        .addComponent(jBtnEdit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLblCompleted, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLblTaskType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jChkIncCompleted)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jCboTaskType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblDate)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblTaskType)
                    .addComponent(jCboTaskType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jChkIncCompleted)
                    .addComponent(jLblCompleted))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnClose)
                    .addComponent(jBtnEdit))
                .addContainerGap(13, Short.MAX_VALUE))
        );
    }//                       


    // Variables declaration                    
    private javax.swing.JButton jBtnClose;
    private javax.swing.JButton jBtnEdit;
    private javax.swing.JComboBox jCboTaskType;
    private javax.swing.JCheckBox jChkIncCompleted;
    private javax.swing.JLabel jLblCompleted;
    private javax.swing.JLabel jLblDate;
    private javax.swing.JLabel jLblTaskType;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblTasks;
    private javax.swing.JTextField jTextField1;
    private Task task;
    private Boolean retVar;
    
}
