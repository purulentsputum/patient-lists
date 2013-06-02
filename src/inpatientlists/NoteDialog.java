
package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;

/**
 *
 * @author ross sellars
 * @created 01 Jun 2013 20:17
 */
public class NoteDialog extends JDialog{
 public NoteDialog(Frame aFrame) {
        /*
         * creates new user
         */
     
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
       
        Data = new Note();
        StartUp();              
    }

    public NoteDialog(Frame aFrame, Note nNote) {
        /*
         * edits existing note
         */
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
        
        Data = new Note(nNote);
        StartUp();
        setResults();
        setCalculatedValues();
    }
    
    public NoteDialog( Note nNote) {
        /*
         * edits existing note, called without an existing frame.
         */
        super(null, JDialog.ModalityType.APPLICATION_MODAL);
        
        Data = new Note(nNote);
        StartUp();
        setResults();
        setCalculatedValues();
    }
    public NoteDialog(Frame aFrame,Patients patient){
        super(aFrame, JDialog.ModalityType.APPLICATION_MODAL);
        
        Data = new Note(patient);
        StartUp();
        setResults();
        setCalculatedValues();
    }

    public Note ReturnData() {
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
        //Note pre
                       
        jLblPatient.setText(Data.getPatient().getOneLineDetails());    
        jTxtNote.setText(Data.getNote());
        jLblCreatedBy.setText(Data.getCreatedBy().getName());
        jLblCreatedDate.setText(MyDates.ConvertDateTimeToString(Data.getCreatedDate()));         
        jLblEditedBy.setText(Data.getEditedBy().getName());
        jLblEditedDate.setText(MyDates.ConvertDateTimeToString(Data.getEditedDate()));
       
                        
    }
    private void getResults(){
        /*
         * loads data into Data 
         */
        //Note
        Data.setNote((jTxtNote.getText()));
        Data.setEditedBy(User.CurrentUser);
                       
    }
    private void setCalculatedValues(){
        //reload data into the variables (incase there has been any change)
        //used mainly for calculated values like percent predicteds etc
        getResults(); 
        // calculate labels
        
        // nothing to be done here
         
    }
    
    // <editor-fold defaultstate="collapsed" desc="Initialization Code">
    private void initComponents() {

        jPanMain = new javax.swing.JPanel();
        jPanData = new javax.swing.JPanel();
        jPanReport = new javax.swing.JPanel();
        
        jBtnSave = new javax.swing.JButton();
        jBtnCancel = new javax.swing.JButton();
        
        jLblHeadCol1 = new javax.swing.JLabel();
                         
        jLblTitlePatient = new javax.swing.JLabel();
        jLblTitleNote = new javax.swing.JLabel();
        jLblTitleCreatedBy = new javax.swing.JLabel();
        jLblTitleCreatedDate = new javax.swing.JLabel();
        jLblTitleEditedBy = new javax.swing.JLabel();
        jLblTitleEditedDate = new javax.swing.JLabel();
                
        jLblPatient = new javax.swing.JLabel();    
        jTxtNote = new javax.swing.JTextArea();
        jLblCreatedBy = new javax.swing.JLabel();
        jLblCreatedDate = new javax.swing.JLabel();              
        jLblEditedBy = new javax.swing.JLabel();
        jLblEditedDate = new javax.swing.JLabel();
        jScrNote = new javax.swing.JScrollPane();
        
        jPanData.setBorder(javax.swing.BorderFactory.createTitledBorder("Patient Notes"));

        jLblTitlePatient.setText("Patient:");
        jLblTitleNote.setText("Note:");
        jLblTitleCreatedBy.setText("Created by:");
        jLblTitleCreatedDate.setText("Date created:");
        jLblTitleEditedBy.setText("Edited by:");
        jLblTitleEditedDate.setText("Date edited:");
        
        jLblPatient.setText(" ");
        jTxtNote.setColumns(20);
        jTxtNote.setRows(5);
        jTxtNote.setText(" ");
        jTxtNote.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtNote.selectAll();
            }            
        });
        jScrNote.setViewportView(jTxtNote);

        jLblCreatedBy.setText(" ");
        jLblCreatedDate.setText(" ");              
        jLblEditedBy.setText(" ");
        jLblEditedDate.setText(" ");
        
        

        jLblHeadCol1.setText("Patient Notes:");

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
                .addComponent(jLblTitlePatient)
                .addComponent(jLblTitleNote)
                .addComponent(jLblTitleCreatedBy)
                .addComponent(jLblTitleCreatedDate)
                .addComponent(jLblTitleEditedBy)
                .addComponent(jLblTitleEditedDate)                
                .addComponent(jBtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(20, 20, 20)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblHeadCol1)
                .addComponent(jLblPatient, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jScrNote, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addComponent(jLblCreatedBy)
                .addComponent(jLblCreatedDate)
                .addComponent(jLblEditedBy)                
                .addComponent(jLblEditedDate)                
                .addComponent(jBtnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(20, 20, 20));

        jPanDataLayout.setVerticalGroup(
                jPanDataLayout.createSequentialGroup()
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLblHeadCol1))
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblTitlePatient)
                .addComponent(jLblPatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblTitleNote)
                .addComponent(jScrNote, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblTitleCreatedBy)
                .addComponent(jLblCreatedBy)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblTitleCreatedDate)
                .addComponent(jLblCreatedDate)//, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblTitleEditedBy)
                .addComponent(jLblEditedBy)
                )
                .addGap(6, 6, 6)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLblTitleEditedDate)
                .addComponent(jLblEditedDate)
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
        Data = new Note(); // return blank stuff
        Data.setNote("Cancelled");
        RetVar = false; // did not update
        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }

        
    // 
    
    //Variables declaration 
    private javax.swing.JLabel jLblHeadCol1;
    
    
    private javax.swing.JLabel jLblTitlePatient;
    private javax.swing.JLabel jLblTitleNote;
    private javax.swing.JLabel jLblTitleCreatedBy;
    private javax.swing.JLabel jLblTitleCreatedDate;
    private javax.swing.JLabel jLblTitleEditedBy;
    private javax.swing.JLabel jLblTitleEditedDate;
        
    private javax.swing.JLabel jLblPatient;    
    private javax.swing.JTextArea jTxtNote;
    private javax.swing.JScrollPane jScrNote;
    private javax.swing.JLabel jLblCreatedBy;
    private javax.swing.JLabel jLblCreatedDate;              
    private javax.swing.JLabel jLblEditedBy;
    private javax.swing.JLabel jLblEditedDate;
        
    private javax.swing.JButton jBtnCancel;
    private javax.swing.JButton jBtnSave;
    
       
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanReport;
    private javax.swing.JPanel jPanData;
    
    
    Note Data;
    boolean RetVar;
   
    // End of variables declaration
    
}
