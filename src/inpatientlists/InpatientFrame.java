
package inpatientlists;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 19:01
 * @edited 02/06/2013 added counter to patients, 
 * @edited 07/06/2013 fixed filters and added surname
 * @edited 11/06/2013 added admin button method
 */
public class InpatientFrame extends javax.swing.JFrame {

    /**
     * Creates new form InpatientFrame
     */
    public InpatientFrame() {
       startUp();
    }
    private void startUp(){
        
        initComponents();
        
        jLstCons.setVisible(false);
        jLstWard.setVisible(false);
        jLstUnit.setVisible(false);
        populateWards();
        populateUnits();
        populateConsultants();
        jLstCons.setVisible(true);
        jLstWard.setVisible(true);
        jLstUnit.setVisible(true);
        hideFilters(true);
        populateCboFilter1();        
        populateCboFilter2();        
        populateCboFilter3();
        hideFilters(false);
        jBtnPtNotes.setVisible(false);
        jBtnPtTask.setVisible(false);
        if(User.CurrentUser.isLimited()){
            this.jBtnAllTasks.setVisible(false);
            this.jBtnExport.setVisible(false);
        }
        setUserDefaults();
        this.jTreePatients.setPreferredSize(null); //required to allow scroll bar to appear - crazy stuff!
        populatePatientList(getSQL());
        
        pack();
        setLocationRelativeTo(null);
        
    }
    private void setUserDefaults(){
        //sets defaults on startup and after change to user data
        
        jLstWard.clearSelection();
        jLstWard.setSelectedValue(User.CurrentUser.getWard(), true);
        jLstUnit.clearSelection();
        jLstUnit.setSelectedValue(User.CurrentUser.getUnit(),true);
        jLstCons.clearSelection();
        Doctors tempVar = new Doctors(User.CurrentUser.getConsultantCode());
        jLstCons.setSelectedValue(tempVar.getName(), true);
        
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
    }
    
    private void populateConsultants(){
        int defaultIndex = -1;
        // generate list
        consultantArray = Doctors.loadDoctors();
        // array for loading into list
        String[] tempArray = new String [consultantArray.length];
        for (int i=0;i<consultantArray.length;i++ ){
            tempArray[i]=consultantArray[i].getName();
            if(User.CurrentUser.getConsultantCode().equals(consultantArray[i].getCode())){
                defaultIndex = i;
            }
        }               
        this.jLstCons.setListData(tempArray);        
    }
    
    private void populateCboFilter1(){
       
       String filterprev = (String)jCboFilter1.getSelectedItem();
       jCboFilter1.removeAllItems();
       int i=0;
       jCboFilter1.insertItemAt(ward, i++);
       jCboFilter1.insertItemAt(unit, i++);
       jCboFilter1.insertItemAt(consultant, i++);
       jCboFilter1.insertItemAt(surname, i++);  
       
       if((filterprev.equals(unit))||(filterprev.equals(consultant))){
           jCboFilter1.setSelectedItem(filterprev);
       }else{
           jCboFilter1.setSelectedItem(ward);
       }
                     
    }
    private void populateCboFilter2(){
       
       String filterprev = (String)jCboFilter2.getSelectedItem(); 
       jCboFilter2.removeAllItems();
       
       String filter1=Utilities.noNulls((String)jCboFilter1.getSelectedItem());
       
       int i=0;
       if(!filter1.equals(ward)){
           jCboFilter2.insertItemAt(ward, i++);
           
       }
       if(!filter1.equals(unit))jCboFilter2.insertItemAt(unit, i++);
       if(!filter1.equals(consultant))jCboFilter2.insertItemAt(consultant, i++);
       if(!filter1.equals(surname))jCboFilter2.insertItemAt(surname, i++);
       jCboFilter2.insertItemAt(none, i);
       
       if((filterprev.equals(filter1))||(filter1.equals(none))){
           jCboFilter2.setSelectedItem(none);
       }else{
           if((filterprev.equals(ward))||(filterprev.equals(unit))||(filterprev.equals(consultant))){
                jCboFilter2.setSelectedItem(filterprev);
           }else{
                jCboFilter2.setSelectedItem(none);
           }
       }
    }
    private void populateCboFilter3(){
       
       String filterprev = (String)jCboFilter3.getSelectedItem(); 
       
       jCboFilter3.removeAllItems();
       
       String filter1=Utilities.noNulls((String)jCboFilter1.getSelectedItem());
       String filter2=Utilities.noNulls((String)jCboFilter2.getSelectedItem());
       if (filter2.equals(none)){
           jCboFilter3.insertItemAt(none, 0);
           jCboFilter3.setSelectedItem(none);
       }  else{     
            //JOptionPane.showMessageDialog(  null ,filter1 +"-"+ filter2);
            int i=0;


            if(!((filter1.equals(ward))||(filter2.equals(ward))))jCboFilter3.insertItemAt(ward, i++);
            if(!((filter1.equals(unit))||(filter2.equals(unit))))jCboFilter3.insertItemAt(unit, i++);
            if(!((filter1.equals(consultant))||(filter2.equals(consultant))))jCboFilter3.insertItemAt(consultant, i++);
            jCboFilter3.insertItemAt(none, i);

            if((filterprev.equals(filter1))||(filterprev.equals(filter2))){
                jCboFilter3.setSelectedItem(none);
            }else{
                if((filterprev.equals(ward))||(filterprev.equals(unit))||(filterprev.equals(consultant))){
                     jCboFilter3.setSelectedItem(filterprev);
                }else{
                     jCboFilter3.setSelectedItem(none);
                }
            }
       }
    }
    private void hideFilters(Boolean hide){
        this.jCboFilter1.setVisible(!hide);
        this.jCboFilter2.setVisible(!hide);
        this.jCboFilter3.setVisible(!hide);
    }
    private String getWardSQLpart(){
        String retVar="";
        String tempVar;
        for(int i = 0; i < this.jLstWard.getModel().getSize(); i++){
            if(this.jLstWard.isSelectedIndex(i)){
                // is selected, next test if first element, and if not use AND
                tempVar = (String)this.jLstWard.getModel().getElementAt(i);
                if (retVar.length()>1){ retVar = retVar + " OR ";}
                retVar = retVar + "((tbl_002_Admissions.DischargeWard) = '" + tempVar + "')";
                if (sqlDesc.length()>1){ sqlDesc = sqlDesc + ", "; }
                sqlDesc = sqlDesc + tempVar;
            }            
        }
        return retVar;
    }
    private String getUnitSQLpart(){
        String retVar="";
        String tempVar;
        for(int i = 0; i < this.jLstUnit.getModel().getSize(); i++){
            if(this.jLstUnit.isSelectedIndex(i)){
                // is selected, next test if first element, and if not use AND
                tempVar = (String)this.jLstUnit.getModel().getElementAt(i);
                if (retVar.length()>1){ retVar = retVar + " OR ";}
                retVar = retVar + "((tbl_002_Admissions.DischargeUnit) = '" + tempVar + "')";
                if (sqlDesc.length()>1){ sqlDesc = sqlDesc + ", "; }
                sqlDesc = sqlDesc + tempVar;
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
                if (sqlDesc.length()>1){ sqlDesc = sqlDesc + ", "; }
                sqlDesc = sqlDesc + consultantArray[i].getName() ;
            }
        }
        return retVar;
    }
    private String getSortSQLpart(){
        String retVar="";
        String tempVar="";
        
        tempVar = getSortSingleFilter(Utilities.noNulls((String)jCboFilter1.getSelectedItem()));
        if ((retVar.length()>1)&&(tempVar.length()>1) ) retVar = retVar + ", ";
        retVar = retVar + tempVar;
        
        tempVar = getSortSingleFilter(Utilities.noNulls((String)jCboFilter2.getSelectedItem()));
        if ((retVar.length()>1)&&(tempVar.length()>1) ) retVar = retVar + ", ";
        retVar = retVar + tempVar;
        
        tempVar = getSortSingleFilter(Utilities.noNulls((String)jCboFilter3.getSelectedItem()));
        if ((retVar.length()>1)&&(tempVar.length()>1) ) retVar = retVar + ", ";
        retVar = retVar + tempVar;
        
        if (retVar.length()>1){
            retVar = " ORDER BY " + retVar;
        }
        return retVar;
        
    }
    private String getSortSingleFilter(String item){
        String retVar="";
        switch (item){
            case none:
                break;
            case ward:                
                retVar = retVar + "tbl_002_Admissions.DischargeWard, Tbl_002_Admissions.Bed";
                break;
            case unit:
                retVar = retVar + "tbl_002_Admissions.DischargeUnit";
                break;
            case consultant:
                retVar = retVar + "tbl_002_Admissions.DrTreating";
                break;
            case surname:
                retVar = retVar + "tbl_001_Patients.Surname";
            default:
                break;
        }
        
        return retVar;
    }
    private String getSQL(){
        String retVar;
        sqlDesc = ""; //plain text of query
        String ward = getWardSQLpart();
        String unit = getUnitSQLpart();
        String cons = getConsultantSQLpart();
        if (sqlDesc.length()>0){sqlDesc = " (" + sqlDesc + ")";}
        sqlDesc = "Inpatient List" + sqlDesc;
                
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
                    getSortSQLpart() + ";";

        }else{
            // no filter inplace
            retVar = "SELECT Tbl_002_Admissions.*, Tbl_001_Patients.* " +
                "FROM Tbl_001_Patients " + 
                "INNER JOIN Tbl_002_Admissions ON Tbl_001_Patients.URN = Tbl_002_Admissions.URN " +
                getSortSQLpart() + ";";
        }
        
        return retVar;
        
    }
    
    private  void populatePatientList(String strSQL){
        //get the list
        patientList = PatientAdmissions.loadCurrentInpatientsFiltered(strSQL);
        
        Admissions[] admList;
        Task[] taskList;
        Note note;
        String tempVar;
                
         // initilize jTree
            DefaultTreeModel myTreeModel = (DefaultTreeModel)jTreePatients.getModel();
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("Patients");
            DefaultMutableTreeNode patient;
            DefaultMutableTreeNode patientDetail;
            DefaultMutableTreeNode patientSubDetail;
            
            for (int intLoopVar=0 ; intLoopVar < patientList.length ; intLoopVar++) {
                tempVar = Integer.toString(intLoopVar+1) + ": " + patientList[intLoopVar].getPatient().getOneLineDetails();
                patient = new DefaultMutableTreeNode(tempVar);
                root.add (patient);

                    patientDetail=new DefaultMutableTreeNode("Admissions");

                        admList = new Admissions[0];
                        admList = Admissions.loadPatientAdmissions( patientList[intLoopVar].getPatient().getURN());
                        for(int i=0;i<admList.length;i++ ){
                            patientDetail.add(new DefaultMutableTreeNode( admList[i].getAdmissionOneLine() ));
                        }    
                    patient.add(patientDetail);
                    // Notes                    
                    note = new Note(patientList[intLoopVar].getPatient()); 
                    tempVar = note.getNote();
                    if (tempVar.length()>5){
                        patientDetail=new DefaultMutableTreeNode("Notes");
                        patientDetail.add(new DefaultMutableTreeNode( note.getNote() ));
                        patientDetail.add(new DefaultMutableTreeNode( "edited by: " + note.getEditedBy().getName() +" on "+ MyDates.ConvertDateTimeToString(note.getEditedDate()) ));
                        patient.add(patientDetail);
                    }
                    
                    
                    //Tasks
                    patientDetail=new DefaultMutableTreeNode("Tasks");
                        taskList = new Task[0];
                        taskList = Task.loadAllPatientTasks( patientList[intLoopVar].getPatient().getURN(),true);
                        for (Task task:taskList){
                            patientSubDetail = new DefaultMutableTreeNode( MyDates.ConvertDateToString(task.getDate()) );
                            patientSubDetail.add(new DefaultMutableTreeNode( task.getTaskDesc() ));
                            patientSubDetail.add(new DefaultMutableTreeNode( task.getUsersOneLine() ));
                            patientDetail.add(patientSubDetail);
                        }    
                    patient.add(patientDetail);
                    //GP
                    patientDetail=new DefaultMutableTreeNode("Refering Doctor");
                    patientDetail.add(new DefaultMutableTreeNode(patientList[intLoopVar].getAdmission().getReferingDoctorOneLine() ));
                    patient.add(patientDetail);
                    // etc etc
                    //patient.add(new DefaultMutableTreeNode("Tasks"));
                

            }
            
            myTreeModel.setRoot(root);
    }
    
     private int getNodeIndex() {

        // get the full path eg [Patients, 1:Bloggs,joe]
        String strNodePath = jTreePatients.getAnchorSelectionPath().toString();
        // remove the root node [Patients,
        strNodePath = strNodePath.substring(10);
        // find the  colon : separator
        int RetVar = 0;
        int index = strNodePath.indexOf(":");
        // if a colon is present
        if (index >1) {
            String strItem = strNodePath.substring (0,index).trim();
            try {
                RetVar = Integer.parseInt(strItem);
            }catch (Exception e) {
                RetVar = 0;
            }
        }
        return RetVar;
    }
    //JOptionPane.showMessageDialog(  null ,strPatientKey );

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
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
        jBtnAllTasks = new javax.swing.JButton();
        jBtnExport = new javax.swing.JButton();
        jBtnAdmin = new javax.swing.JButton();
        jBtnPtNotes = new javax.swing.JButton();
        jBtnPtTask = new javax.swing.JButton();
        jBtnAbout = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jCboFilter1 = new javax.swing.JComboBox();
        jCboFilter2 = new javax.swing.JComboBox();
        jCboFilter3 = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTreePatients = new javax.swing.JTree();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

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
        jPanel2.setMinimumSize(new java.awt.Dimension(327, 116));
        jPanel2.setPreferredSize(new java.awt.Dimension(327, 116));

        jBtnClose.setText("Close");
        jBtnClose.setMaximumSize(new java.awt.Dimension(97, 23));
        jBtnClose.setMinimumSize(new java.awt.Dimension(97, 23));
        jBtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCloseActionPerformed(evt);
            }
        });

        jBtnSettings.setText("Settings");
        jBtnSettings.setMaximumSize(new java.awt.Dimension(97, 23));
        jBtnSettings.setMinimumSize(new java.awt.Dimension(97, 23));
        jBtnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSettingsActionPerformed(evt);
            }
        });

        jBtnAllTasks.setText("Task List");
        jBtnAllTasks.setMaximumSize(new java.awt.Dimension(97, 23));
        jBtnAllTasks.setMinimumSize(new java.awt.Dimension(97, 23));
        jBtnAllTasks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAllTasksActionPerformed(evt);
            }
        });

        jBtnExport.setText("Export List");
        jBtnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExportActionPerformed(evt);
            }
        });

        jBtnAdmin.setText("Admin");
        jBtnAdmin.setMaximumSize(new java.awt.Dimension(97, 23));
        jBtnAdmin.setMinimumSize(new java.awt.Dimension(97, 23));
        jBtnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAdminActionPerformed(evt);
            }
        });

        jBtnPtNotes.setText("Patient Notes");
        jBtnPtNotes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPtNotesActionPerformed(evt);
            }
        });

        jBtnPtTask.setText("Patient Tasks");
        jBtnPtTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPtTaskActionPerformed(evt);
            }
        });

        jBtnAbout.setText("About");
        jBtnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAboutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnClose, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(jBtnAbout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jBtnExport, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(jBtnAllTasks, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnSettings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnPtNotes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnPtTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAllTasks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPtNotes)
                    .addComponent(jBtnAbout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnExport)
                    .addComponent(jBtnPtTask))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Sort Order"));
        jPanel4.setMinimumSize(new java.awt.Dimension(327, 43));

        jCboFilter1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ward", "Unit", "Consultant", "none" }));
        jCboFilter1.setSelectedIndex(3);
        jCboFilter1.setToolTipText("");
        jCboFilter1.setMinimumSize(new java.awt.Dimension(97, 20));
        jCboFilter1.setPreferredSize(new java.awt.Dimension(97, 20));
        jCboFilter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboFilter1ActionPerformed(evt);
            }
        });

        jCboFilter2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ward", "Unit", "Consultant", "none" }));
        jCboFilter2.setSelectedIndex(3);
        jCboFilter2.setMinimumSize(new java.awt.Dimension(97, 20));
        jCboFilter2.setPreferredSize(new java.awt.Dimension(97, 20));
        jCboFilter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboFilter2ActionPerformed(evt);
            }
        });

        jCboFilter3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ward", "Unit", "Consultant", "none" }));
        jCboFilter3.setSelectedIndex(3);
        jCboFilter3.setToolTipText("");
        jCboFilter3.setMinimumSize(new java.awt.Dimension(97, 20));
        jCboFilter3.setPreferredSize(new java.awt.Dimension(97, 20));
        jCboFilter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCboFilter3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jCboFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCboFilter2, 0, 95, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCboFilter3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jCboFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCboFilter2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jCboFilter3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(jBtnClrCons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 26, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        jTreePatients.setRootVisible(false);
        jTreePatients.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreePatientsValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jTreePatients);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLstWardValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jLstWardValueChanged
         //if(jLstWard.getSelectedIndex()>-1) {}
        if(jLstWard.isVisible()){
            populatePatientList(getSQL());
        } 
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
        if(jLstUnit.isVisible()){
            populatePatientList(getSQL());
        }
    }//GEN-LAST:event_jLstUnitValueChanged

    private void jLstConsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jLstConsValueChanged
        if(jLstCons.isVisible()){
            populatePatientList(getSQL());
        }
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

    private void jBtnSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSettingsActionPerformed
        UserDialog dialog = new UserDialog((JFrame)this,User.CurrentUser);
        boolean dialogResult = dialog.ReturnCompleted();
        if (dialogResult) {
            //refresh view
            setUserDefaults();
            populatePatientList(getSQL());
        }
        //JOptionPane.showMessageDialog(  null, local);
    }//GEN-LAST:event_jBtnSettingsActionPerformed

    private void jTreePatientsValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreePatientsValueChanged
        //make patient buttons visible
        if(User.CurrentUser.isLimited()){
            this.jBtnPtNotes.setVisible(false);
            this.jBtnPtTask.setVisible(false);
        }else{
            this.jBtnPtNotes.setVisible(true);
            this.jBtnPtTask.setVisible(true);
        }
        
    }//GEN-LAST:event_jTreePatientsValueChanged

    private void jBtnPtNotesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPtNotesActionPerformed
        int node = getNodeIndex();
        if (node>0){
            //patient selected
            
            NoteDialog dialog = new NoteDialog((JFrame)this, patientList[node-1].getPatient());
            boolean dialogResult = dialog.ReturnCompleted();
        }
        //JOptionPane.showMessageDialog(  null ,node );
        
    }//GEN-LAST:event_jBtnPtNotesActionPerformed

    private void jBtnPtTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPtTaskActionPerformed
        int node = getNodeIndex();
        if (node>0){
            //patient selected
            TaskPatientDialog dialog = new TaskPatientDialog((JFrame)this, patientList[node-1].getPatient().getURN());            
            boolean dialogResult = dialog.ReturnCompleted();
            if (dialogResult) {
                //refresh view            
                populatePatientList(getSQL());
            }
        }                      
    }//GEN-LAST:event_jBtnPtTaskActionPerformed

    private void jBtnAllTasksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAllTasksActionPerformed
        
            TaskDateDialog dialog = new TaskDateDialog((JFrame)this);            
            boolean dialogResult = dialog.ReturnCompleted();
            if (dialogResult) {
                //refresh view            
                populatePatientList(getSQL());
            }
          
    }//GEN-LAST:event_jBtnAllTasksActionPerformed

    private void jBtnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExportActionPerformed
        
        Object[] options = {"Word", "XML", "HTML"};
        String retVar = (String)JOptionPane.showInputDialog(
                (JFrame)this,
                "Would you like to save your list as:",
                "Export Data",
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                options,  //the fields fot the combo
                "Word"); //default 
        if ((retVar != null)&&(retVar.length()>0)){
            switch (retVar){
                case "Word":
                    ExportList.PatientList.Word(patientList, sqlDesc);
                    break;
                case "XML":
                    ExportList.PatientList.XML(patientList, sqlDesc);
                    break;
                case "HTML":
                    ExportList.PatientList.HTML(patientList, sqlDesc);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "You selected " + retVar + " - but I haven't got around to this yet");
                    break;
            }
            
        }
    }//GEN-LAST:event_jBtnExportActionPerformed

    private void jBtnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAdminActionPerformed
        if(User.CurrentUser.isAdmin()){
            String totp = JOptionPane.showInputDialog(null, "Enter your One Time Password (TOTP):");
            Boolean result = TOTP.validateGoogleAuthenticatorCode(User.CurrentUser.getTOTPKey(), Utilities.convertStringToInt(totp));
            if((result)&&(User.CurrentUser.isAdmin())){                                
                UserAdminDialog giveItToMe = new UserAdminDialog((JFrame)this);
                boolean howDidItGo = giveItToMe.ReturnCompleted();
                // return value not acted upon - no need
            }else{
                JOptionPane.showMessageDialog((JFrame)this, "Invalid one-time password", "Inpatient List Program", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
    }//GEN-LAST:event_jBtnAdminActionPerformed

    private void jCboFilter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboFilter2ActionPerformed
         if (jCboFilter2.isVisible()){
            hideFilters(true);
            populateCboFilter3();
            hideFilters(false);
            populatePatientList(getSQL());
         }
        
    }//GEN-LAST:event_jCboFilter2ActionPerformed

    private void jCboFilter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboFilter3ActionPerformed
       if (jCboFilter3.isVisible()){
            populatePatientList(getSQL());
       }
        
    }//GEN-LAST:event_jCboFilter3ActionPerformed

    private void jCboFilter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCboFilter1ActionPerformed
        if (jCboFilter1.isVisible()){
            hideFilters(true);
            populateCboFilter2();  
            populateCboFilter3();
            hideFilters(false);
            populatePatientList(getSQL());             
        }
        
    }//GEN-LAST:event_jCboFilter1ActionPerformed

    private void jBtnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAboutActionPerformed
        JOptionPane.showMessageDialog((JFrame)this, "Another program brought to you by....", "Inpatient List Program", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jBtnAboutActionPerformed

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
    private javax.swing.JButton jBtnAbout;
    private javax.swing.JButton jBtnAdmin;
    private javax.swing.JButton jBtnAllTasks;
    private javax.swing.JButton jBtnClose;
    private javax.swing.JButton jBtnClrCons;
    private javax.swing.JButton jBtnClrUnit;
    private javax.swing.JButton jBtnClrWard;
    private javax.swing.JButton jBtnExport;
    private javax.swing.JButton jBtnPtNotes;
    private javax.swing.JButton jBtnPtTask;
    private javax.swing.JButton jBtnSettings;
    private javax.swing.JComboBox jCboFilter1;
    private javax.swing.JComboBox jCboFilter2;
    private javax.swing.JComboBox jCboFilter3;
    private javax.swing.JLabel jLblCons;
    private javax.swing.JLabel jLblUnit;
    private javax.swing.JLabel jLblWard;
    private javax.swing.JList jLstCons;
    private javax.swing.JList jLstUnit;
    private javax.swing.JList jLstWard;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTree jTreePatients;
    // End of variables declaration//GEN-END:variables
    private Doctors[] consultantArray; 
    private PatientAdmissions[] patientList;
    private String sqlDesc="";
    private final String ward = "Ward";
    private final String unit = "Unit";
    private final String consultant = "Consultant";
    private final String surname ="Surname";
    private final String none = "none";
}
