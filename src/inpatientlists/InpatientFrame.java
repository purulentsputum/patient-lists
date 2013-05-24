
package inpatientlists;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 19:01
 */
public class InpatientFrame extends javax.swing.JFrame {

    /**
     * Creates new form InpatientFrame
     */
    public InpatientFrame() {
        initComponents();
        populateWards();
        populateUnits();
        populateConsultants();
        populatePatientList(getSQL());
        
    }
    private void populateWards(){
        int defaultIndex = -1;
        // generate list
        String[] tempArray = Utilities.loadWards();
        
        for (int i=0;i<tempArray.length;i++ ){
            if(User.CurrentUser.getWard().equals(tempArray[i])){
                defaultIndex = i;
            }
        }        
       
        this.jLstWard.setListData(tempArray);
        if(defaultIndex>-1){
            //user has a valid default setting
            this.jLstWard.setSelectedIndex(defaultIndex);
        }
    }
    private void populateUnits(){
        int defaultIndex = -1;
        // generate list
        String[] tempArray = Utilities.loadUnits();
        
        for (int i=0;i<tempArray.length;i++ ){
            if(User.CurrentUser.getUnit().equals(tempArray[i])){
                defaultIndex = i;
            }
        }        
       
        this.jLstUnit.setListData(tempArray);
        if(defaultIndex>-1){
            //user has a valid default setting
            this.jLstUnit.setSelectedIndex(defaultIndex);
        }
    }
    private void populateConsultants(){
        int defaultIndex = -1;
        // generate list
        consultantArray = Doctors.loadDoctors();
        // array for loading into list
        String[] tempArray = new String [consultantArray.length];
        for (int i=0;i<consultantArray.length;i++ ){
            tempArray[i]=consultantArray[i].getName();
            if(User.CurrentUser.getConsultant().equals(consultantArray[i].getCode())){
                defaultIndex = i;
            }
        }        
       
        this.jLstCons.setListData(tempArray);
        if(defaultIndex>-1){
            //user has a valid default setting
            this.jLstCons.setSelectedIndex(defaultIndex);
        }
    }
    
    private String getWardSQLpart(){
        String retVar="";
        for(int i = 0; i < this.jLstWard.getModel().getSize(); i++){
            if(this.jLstWard.isSelectedIndex(i)){
                // is selected, next test if first element, and if not use AND
                if (retVar.length()>1){ retVar = retVar + " OR ";}
                retVar = retVar + "((tbl_002_Admissions.DischargeWard) = '" + (String)this.jLstWard.getModel().getElementAt(i) + "')";
            }
        }
        return retVar;
    }
    private String getUnitSQLpart(){
        String retVar="";
        for(int i = 0; i < this.jLstUnit.getModel().getSize(); i++){
            if(this.jLstUnit.isSelectedIndex(i)){
                // is selected, next test if first element, and if not use AND
                if (retVar.length()>1){ retVar = retVar + " OR ";}
                retVar = retVar + "((tbl_002_Admissions.DischargeUnit) = '" + (String)this.jLstUnit.getModel().getElementAt(i) + "')";
            }
        }
        return retVar;
    }
    private String getConsultantSQLpart(){
        String retVar="";
        for(int i = 0; i < this.jLstCons.getModel().getSize(); i++){
            if(this.jLstCons.isSelectedIndex(i)){
                // is selected, next test if first element, and if not use AND
                if (retVar.length()>1){ retVar = retVar + " OR ";}
                retVar = retVar + "((tbl_002_Admissions.DrTreating) = '" + consultantArray[i].getCode() + "')";
            }
        }
        return retVar;
    }
    private String getSQL(){
        String retVar;
        String ward = getWardSQLpart();
        String unit = getUnitSQLpart();
        String cons = getConsultantSQLpart();
        
        String tempSQL = new String(ward);
        if ((tempSQL.length()>1)&&(unit.length())>1) {tempSQL = tempSQL + " AND ";}
       tempSQL = tempSQL + unit;
        if ((tempSQL.length()>1)&&(cons.length()>1)) {tempSQL = tempSQL + " AND ";}
        tempSQL = tempSQL + cons;
               
        if (tempSQL.length()>1) {
            retVar = "SELECT Tbl_002_Admissions.*, Tbl_001_Patients.* " +
                    "FROM Tbl_001_Patients " +
                    "INNER JOIN Tbl_002_Admissions ON Tbl_001_Patients.URN = Tbl_002_Admissions.URN " +
                    "WHERE (" + tempSQL + ") " + 
                    "ORDER BY Tbl_002_Admissions.DischargeWard, Tbl_002_Admissions.Bed;";

        }else{
            // no filter inplace
            retVar = "SELECT Tbl_002_Admissions.*, Tbl_001_Patients.* " +
                "FROM Tbl_001_Patients " + 
                "INNER JOIN Tbl_002_Admissions ON Tbl_001_Patients.URN = Tbl_002_Admissions.URN " +
                "ORDER BY Tbl_002_Admissions.DischargeWard, Tbl_002_Admissions.Bed;";
        }
        
        return retVar;
        
    }
    
    private  void populatePatientList(String strSQL){
        //get the list
        patientList = PatientAdmissions.loadCurrentInpatientsFiltered(strSQL);
        
         // initilize jTree
            DefaultTreeModel myTreeModel = (DefaultTreeModel)jTreePatients.getModel();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Patients");
            DefaultMutableTreeNode patient;
            DefaultMutableTreeNode patientDetail;
            
            for (int intLoopVar=0 ; intLoopVar < patientList.length ; intLoopVar++) {

                patient = new DefaultMutableTreeNode(patientList[intLoopVar].getPatient().getOneLineDetails());
                root.add (patient);

                // child nodes, add only if addDetails flags <= 10 records
                
                    patient.add(new DefaultMutableTreeNode("Admissions"));

                    patient.add(new DefaultMutableTreeNode("Diagnoses"));
                    // Diagnoses
                    patientDetail=new DefaultMutableTreeNode("Notes");

               //         DiagnosisList = new String[1][5];
               //         //JOptionPane.showMessageDialog(  null ,strPatientKey );
//
               //         DiagnosisList = DiagnosisForPatient.getPatientDiagnosisArray(strPatientList[intLoopVar][0]);
                //        for (int i=0;i<DiagnosisList.length;i++ ){
               //            patientDetail.add(new DefaultMutableTreeNode( DiagnosisList[i][1] + " "+DiagnosisList[i][2] ));
                //        }
                        patient.add(patientDetail);
                    // visits
                    patient.add(new DefaultMutableTreeNode("Weekend categories"));
                    patient.add(new DefaultMutableTreeNode("Results"));
                    // etc etc
                

            }
            
            myTreeModel.setRoot(root);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLstWard = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLstUnit = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jLstCons = new javax.swing.JList();
        jLblWard = new javax.swing.JLabel();
        jLblUnit = new javax.swing.JLabel();
        jLblCons = new javax.swing.JLabel();
        jBtnClrWard = new javax.swing.JButton();
        jBtnClrUnit = new javax.swing.JButton();
        jBtnClrCons = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jBtnClose = new javax.swing.JButton();
        jBtnSettings = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTreePatients = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLstWard.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jLstWard.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jLstWardValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jLstWard);

        jLstUnit.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jLstUnit.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jLstUnitValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jLstUnit);

        jLstCons.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jLstCons.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jLstConsValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jLstCons);

        jLblWard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblWard.setLabelFor(jLblWard);
        jLblWard.setText("Ward");

        jLblUnit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblUnit.setLabelFor(jLstUnit);
        jLblUnit.setText("Unit");

        jLblCons.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblCons.setLabelFor(jLstCons);
        jLblCons.setText("Consultant");

        jBtnClrWard.setText("Clear");
        jBtnClrWard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnClrWardActionPerformed(evt);
            }
        });

        jBtnClrUnit.setText("Clear");
        jBtnClrUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnClrUnitActionPerformed(evt);
            }
        });

        jBtnClrCons.setText("Clear");
        jBtnClrCons.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnClrConsActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBtnClose.setText("Close");
        jBtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCloseActionPerformed(evt);
            }
        });

        jBtnSettings.setText("Settings");

        jButton1.setText("Weekend List");

        jButton2.setText("Export List");

        jButton3.setText("Admin");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnSettings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnClose, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSettings)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnClose)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jBtnClrWard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLblWard, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtnClrUnit, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jLblUnit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jLblCons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnClrCons, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblWard)
                    .addComponent(jLblUnit)
                    .addComponent(jLblCons))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnClrWard)
                    .addComponent(jBtnClrUnit)
                    .addComponent(jBtnClrCons))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jTreePatients);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLstWardValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jLstWardValueChanged
         //if(jLstWard.getSelectedIndex()>-1) {}
         populatePatientList(getSQL());
         
    }//GEN-LAST:event_jLstWardValueChanged

    private void jBtnClrWardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnClrWardActionPerformed
        this.jLstWard.clearSelection();
    }//GEN-LAST:event_jBtnClrWardActionPerformed

    private void jBtnClrUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnClrUnitActionPerformed
        this.jLstUnit.clearSelection();
    }//GEN-LAST:event_jBtnClrUnitActionPerformed

    private void jBtnClrConsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnClrConsActionPerformed
        this.jLstCons.clearSelection();
    }//GEN-LAST:event_jBtnClrConsActionPerformed

    private void jLstUnitValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jLstUnitValueChanged
        populatePatientList(getSQL());
    }//GEN-LAST:event_jLstUnitValueChanged

    private void jLstConsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jLstConsValueChanged
        populatePatientList(getSQL());
    }//GEN-LAST:event_jLstConsValueChanged

    private void jBtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCloseActionPerformed
        /**
         * this handles all the processes necessary for a safe exit strategy
         *
         */
        int n = JOptionPane.showConfirmDialog(null,
            "Are you sure that you want to close the program and exit?",
            "Exit Selected",
            JOptionPane.YES_NO_OPTION);
        //0 = yes, 1 = No
        if (n==0) {
            // Yes selected
            System.exit(0);
        }else {
            // No selected
            // do nothing and ignore it (just like being a manager)
        }
    }//GEN-LAST:event_jBtnCloseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InpatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InpatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InpatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InpatientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InpatientFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnClose;
    private javax.swing.JButton jBtnClrCons;
    private javax.swing.JButton jBtnClrUnit;
    private javax.swing.JButton jBtnClrWard;
    private javax.swing.JButton jBtnSettings;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLblCons;
    private javax.swing.JLabel jLblUnit;
    private javax.swing.JLabel jLblWard;
    private javax.swing.JList jLstCons;
    private javax.swing.JList jLstUnit;
    private javax.swing.JList jLstWard;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTree jTreePatients;
    // End of variables declaration//GEN-END:variables
    private Doctors[] consultantArray; 
    private PatientAdmissions[] patientList;
}
