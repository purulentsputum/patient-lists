

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ross sellars
 * @created 08/06/2013 08:10
 */
public class UserAdminDialog extends JDialog{
    /**
     * admin only dialog to review and edit users
     */
    public UserAdminDialog(Frame aFrame) {
        /*
         * views all users
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);       
        
        startUp();              
    }

   public boolean ReturnCompleted() {
        setVisible(true);
        return retVar;
    }
   
    
    private void startUp(){
        initComponents();
        
        pack();
        setLocationRelativeTo(null);
        user = new User();
        
    }
    private void finishUp() {
        
        
        retVar = true;

        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }
    
    private void populateTable(){
         // hide me while I repopulate so that the listener can ignore me
        jBtnEdit.setEnabled(false);
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
        String[] columnNames = {"UID","Name","Admin","Active","Locked","Limited","Created","Last Login","No logins","Invalid","No Invalid"};
        tm.setColumnIdentifiers(columnNames);

        DataList = new User[0];
        DataList = User.loadAllUsers( this.jChkFilterLocked.isSelected(),this.jChkFilterLimited.isSelected());
                    
        String[] rowData;
        for (User userList:DataList){
            rowData = new String[11];
            rowData[0] = userList.getUID();
            rowData[1] = userList.getName();   //MyDates.ConvertDateToString(user.getDate());
            rowData[2] = Boolean.toString(userList.isAdmin());
            rowData[3] = Boolean.toString(userList.isActive());
            rowData[4] = Boolean.toString(userList.isLocked());
            rowData[5] = Boolean.toString(userList.isLimited());
            rowData[6] = MyDates.ConvertDateTimeToString(userList.getDateCreated());
            rowData[7] = MyDates.ConvertDateTimeToString(userList.getDateLastLogin());
            rowData[8] = Integer.toString(userList.getNumberValidLogins());
            rowData[9] = MyDates.ConvertDateTimeToString(userList.getDateLastInvalidLogin());
            rowData[10] = Integer.toString(userList.getNumberInvalidLogins());
            tm.addRow(rowData);
            // fixes issue with change is height not displaying the last row(s)
            jTblData.setPreferredSize(null);
        }
        
        
        //jLblCount.setText(Integer.toString(intRowCount) + " " +Utilities.PleuralEntries("entry",intRowCount));

        /*
         * not the best, i know, but it works for me
         * this hides the Key column but leaves it there so I can access its data
         */
        setColumnWidth(columnNames[0],50,true);
        setColumnWidth(columnNames[1],125,false);
        setColumnWidth(columnNames[2],50,true);
        setColumnWidth(columnNames[3],50,true);
        setColumnWidth(columnNames[4],50,true);
        setColumnWidth(columnNames[5],50,true);
        setColumnWidth(columnNames[6],120,true);
        setColumnWidth(columnNames[7],120,false);
        setColumnWidth(columnNames[8],75,false);
        setColumnWidth(columnNames[9],120,true);
        setColumnWidth(columnNames[10],75,true);        

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
            user = new User(DataList[intDataIndex]);
                
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
        
        jChkFilterLocked = new javax.swing.JCheckBox();
        jChkFilterLimited = new javax.swing.JCheckBox();
        jLblFilterLocked = new javax.swing.JLabel();
        jLblFilterLimited = new javax.swing.JLabel();
                      
        jBtnClose = new javax.swing.JButton();
        jBtnEdit = new javax.swing.JButton();
        jBtnNew = new javax.swing.JButton();
        
        jTblData = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();

        jLblFilterLocked.setText("Select locked users:");
        jLblFilterLimited.setText("Select limited users:");
        jChkFilterLocked.setText("");
        jChkFilterLimited.setText("");
        jChkFilterLocked.setSelected(false);
        jChkFilterLimited.setSelected(false);
        jChkFilterLocked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkFilterLockedActionPerformed(evt);
            }
        });
        jChkFilterLimited.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkFilterLimitedActionPerformed(evt);
            }
        });                      
        
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

        jBtnNew.setText("New User");
        jBtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewActionPerformed(evt);
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
                .addComponent(jLblFilterLocked) 
                .addComponent(jLblFilterLimited)
                )
                .addGap(20, 20, 20)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jChkFilterLocked) 
                .addComponent(jChkFilterLimited)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addComponent(jPanButtons)
                )
                .addGap(20, 20, 20)
        );

        jPanDataLayout.setVerticalGroup(
                jPanDataLayout.createSequentialGroup()
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblFilterLocked)
                    .addComponent(jChkFilterLocked)
                )               
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblFilterLimited)
                    .addComponent(jChkFilterLimited)
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
    private void jChkFilterLockedActionPerformed(java.awt.event.ActionEvent evt) {                                           
        populateTable();
    }
    private void jChkFilterLimitedActionPerformed(java.awt.event.ActionEvent evt) {                                           
        populateTable();
    }
    
    private void jBtnNewActionPerformed(java.awt.event.ActionEvent evt) {                                                
        UserDialog giveItToMe = new UserDialog(new User());
        boolean howDidItGo = giveItToMe.ReturnCompleted();
        if (howDidItGo){
            // returned true - ie saved new task
            populateTable();
        }
    }
    private void jBtnEditActionPerformed(java.awt.event.ActionEvent evt) {                                                
        UserDialog giveItToMe = new UserDialog(user);
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
    
    private javax.swing.JButton jBtnClose;
    private javax.swing.JButton jBtnEdit;
    private javax.swing.JButton jBtnNew;
    
    private javax.swing.JTable jTblData;
    
    private javax.swing.JScrollPane jScrollPane1;
    
    private javax.swing.JCheckBox jChkFilterLocked;
    private javax.swing.JCheckBox jChkFilterLimited;
    private javax.swing.JLabel jLblFilterLocked;
    private javax.swing.JLabel jLblFilterLimited;
    
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanButtons;
    private javax.swing.JPanel jPanData;
    
    private User[] DataList;
    private boolean retVar;
    private User user;
}
