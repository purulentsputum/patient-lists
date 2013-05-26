

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ross sellars
 * @created 24/05/2013 12:08
 */
public class UserDialog extends JDialog{
    public UserDialog(Frame aFrame) {
        /*
         * creates new user
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
       
        Data = new User();
        StartUp();              
    }

    public UserDialog(Frame aFrame, User nUser) {
        /*
         * edits existing user
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
        
        Data = nUser;
        StartUp();
        setResults();
        setCalculatedValues();
    }
    
    public UserDialog( User nUser) {
        /*
         * edits existing user, called without an existing frame.
         */
        super(null, JDialog.ModalityType.APPLICATION_MODAL);
        
        Data = nUser;
        StartUp();
        setResults();
        setCalculatedValues();
    }

    public User ReturnData() {
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
        PopulateWardCombo();
        PopulateUnitCombo();
        PopulateConsultantCombo();
        pack();
        setLocationRelativeTo(null);
        if(User.CurrentUser.isAdmin()){
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
        RetVar = true;

        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }
    private void setResults(){
        /*
         * loads data from memory (Retvar) into appropriate text/lable etc locations
         */
        //User pre
                       
        jTxtName.setText(Data.getName() );    
        jCboWard.setSelectedItem(Data.getWard());
        jCboUnit.setSelectedItem(Data.getUnit());
        
        for (int i = 0;i<consultantArray.length;i++){
            if(User.CurrentUser.getConsultantCode().equals(consultantArray[i].getCode())){
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
        //User
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
    
    private void PopulateConsultantCombo(){
        
        jCboConsultant.removeAllItems();
        // generate list
        int defaultIndex = -1; 
        int tempCount=0;
        
        consultantArray = Doctors.loadDoctors();
        
        for (int i = 0;i<consultantArray.length;i++){
            tempCount=i;
            jCboConsultant.insertItemAt(consultantArray[i].getName(), i);
            if(User.CurrentUser.getConsultantCode().equals(consultantArray[i].getCode())){
                defaultIndex = i;
            }
        }
        jCboConsultant.insertItemAt("none",tempCount+1);
        
        // mark default as selected
        if(defaultIndex>-1){
            //user has a valid default setting
            this.jCboConsultant.setSelectedIndex(defaultIndex);
        }else{
            this.jCboConsultant.setSelectedIndex(tempCount+1);
        }
                
    }
    
    private void PopulateUnitCombo(){
        
        jCboUnit.removeAllItems();
        // generate list
        int defaultIndex = -1; 
        int tempCount=0;
        
        String[] tempArray = Utilities.loadUnits();
        
        for (int i = 0;i<tempArray.length;i++){
            tempCount=i;
            jCboUnit.insertItemAt(tempArray[i], i);
            if(User.CurrentUser.getUnit().equals(tempArray[i])){
                defaultIndex = i;
            }
        }
        jCboUnit.insertItemAt("none",tempCount+1);
        
        // mark default as selected
        if(defaultIndex>-1){
            //user has a valid default setting
            this.jCboUnit.setSelectedIndex(defaultIndex);
        }else{
            this.jCboUnit.setSelectedIndex(tempCount+1);
        }
                
    }
    
    private void PopulateWardCombo(){
        
        jCboWard.removeAllItems();
        // generate list
        int defaultIndex = -1; 
        int tempCount=0;
        
        String[] tempArray = Utilities.loadWards();
        
        for (int i = 0;i<tempArray.length;i++){
            tempCount=i;
            jCboWard.insertItemAt(tempArray[i], i);
            if(User.CurrentUser.getWard().equals(tempArray[i])){
                defaultIndex = i;
            }
        }
        jCboWard.insertItemAt("none",tempCount+1);
        
        // mark default as selected
        if(defaultIndex>-1){
            //user has a valid default setting
            this.jCboWard.setSelectedIndex(defaultIndex);
        }else{
            this.jCboWard.setSelectedIndex(tempCount+1);
        }
                
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
        jLblRow7 = new javax.swing.JLabel();
        jLblRow8 = new javax.swing.JLabel();
        jLblRow9 = new javax.swing.JLabel();
        jLblRowA = new javax.swing.JLabel();
        jLblRowB = new javax.swing.JLabel();
        jLblRowC = new javax.swing.JLabel();
        jLblRowD = new javax.swing.JLabel();
        
        jTxtName = new javax.swing.JTextField();    
        jCboWard = new javax.swing.JComboBox();
        jCboUnit = new javax.swing.JComboBox();
        jCboConsultant = new javax.swing.JComboBox();              
        jLblUID = new javax.swing.JLabel();
        jLblDateCreated = new javax.swing.JLabel();
        jLblDateLastValidLogin = new javax.swing.JLabel();
        jLblDateLastInvalidLogin = new javax.swing.JLabel();
        jLblNumberValidLogins = new javax.swing.JLabel();
        jLblNumberInvalidLogins = new javax.swing.JLabel();
        jChkAdmin = new javax.swing.JCheckBox();
        jChkLocked = new javax.swing.JCheckBox();
        jChkLimited = new javax.swing.JCheckBox();

        jPanData.setBorder(javax.swing.BorderFactory.createTitledBorder("Current User Profile"));

        jLblRow1.setText("Name:");
        jLblRow2.setText("Default Ward:");
        jLblRow3.setText("Default Unit:");
        jLblRow4.setText("Default Consultant:");
        jLblRow5.setText("User ID:");
        jLblRow6.setText("Date created:");
        jLblRow7.setText("Date last valid login:");
        jLblRow8.setText("Number of valid logins:");
        jLblRow9.setText("Date last invalid login:");
        jLblRowA.setText("Number of invalid logins:");
        jLblRowB.setText("Administrator:");
        jLblRowC.setText("Access locked:");
        jLblRowD.setText("View only access:");
        
          
        

        jTxtName.setText(" ");
        jTxtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtName.selectAll();
            }            
        });

        
        jCboWard.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        jCboUnit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));
        jCboConsultant.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2" }));              
        jLblUID.setText(" ");
        jLblDateCreated.setText(" ");
        jLblDateLastValidLogin.setText(" ");
        jLblDateLastInvalidLogin.setText(" ");
        jLblNumberValidLogins.setText(" ");
        jLblNumberInvalidLogins.setText(" ");
        //jChkAdmin 
        //jChkLocked 
        //jChkLimited 
        

        jLblHeadCol1.setText("User Details:");

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
                .addComponent(jLblRow7)
                .addComponent(jLblRow8)
                .addComponent(jLblRow9)
                .addComponent(jLblRowA)
                .addComponent(jLblRowB)
                .addComponent(jLblRowC)
                .addComponent(jLblRowD) 
                .addComponent(jBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(20, 20, 20)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblHeadCol1)
                .addComponent(jTxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCboWard, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCboConsultant, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLblUID)                
                .addComponent(jLblDateCreated)
                .addComponent(jLblDateLastValidLogin)
                .addComponent(jLblDateLastInvalidLogin)
                .addComponent(jLblNumberValidLogins)
                .addComponent(jLblNumberInvalidLogins)
                .addComponent(jChkAdmin)
                .addComponent(jChkLocked)
                .addComponent(jChkLimited) 
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
                .addComponent(jTxtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow2)
                .addComponent(jCboWard)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow3)
                .addComponent(jCboUnit)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow4)
                .addComponent(jCboConsultant)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow5)
                .addComponent(jLblUID)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow6)
                .addComponent(jLblDateCreated)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow7)
                .addComponent(jLblDateLastValidLogin)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow8)
                .addComponent(jLblDateLastInvalidLogin)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRow9)
                .addComponent(jLblNumberValidLogins)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRowA)
                .addComponent(jLblNumberInvalidLogins)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRowB)
                .addComponent(jChkAdmin)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRowC)
                .addComponent(jChkLocked)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblRowD)
                .addComponent(jChkLimited)
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
        FinishUp();
    }

    private void jBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        Data = new User(); // return blank stuff
        Data.setName("Cancelled");
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
    private javax.swing.JLabel jLblRowB;
    private javax.swing.JLabel jLblRowC;
    private javax.swing.JLabel jLblRowD;
    
    private javax.swing.JTextField jTxtName;    
    private javax.swing.JComboBox jCboWard;
    private javax.swing.JComboBox jCboUnit;
    private javax.swing.JComboBox jCboConsultant;              
    private javax.swing.JLabel jLblUID;
    private javax.swing.JLabel jLblDateCreated;
    private javax.swing.JLabel jLblDateLastValidLogin;
    private javax.swing.JLabel jLblDateLastInvalidLogin;
    private javax.swing.JLabel jLblNumberValidLogins;
    private javax.swing.JLabel jLblNumberInvalidLogins;
    private javax.swing.JCheckBox jChkAdmin;
    private javax.swing.JCheckBox jChkLocked;
    private javax.swing.JCheckBox jChkLimited;
    
        
    private javax.swing.JButton jBtnCancel;
    private javax.swing.JButton jBtnSave;
    
       
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanReport;
    private javax.swing.JPanel jPanData;
    
    
    User Data;
    boolean RetVar;
    Doctors[] consultantArray;
    // End of variables declaration
}
