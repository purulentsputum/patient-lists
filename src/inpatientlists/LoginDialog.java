

package inpatientlists;

import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Window;

/**
 *
 * @author ross sellars
 * @created 24/05/2013 20:21
 */
public class LoginDialog extends JDialog{
   
    
    public LoginDialog( ) {
        /*
         * edits existing user, called without an existing frame.
         */
        super( null,    JDialog.ModalityType.APPLICATION_MODAL);
        
        StartUp();
        setResults();
        
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
        //called if successful
        
        RetVar = true;

        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }
    private void setResults(){
        /*
         * loads defaults
         */
        //User pre
                       
        
        
    }
    private void getResults(){
        /*
         * loads data into Data 
         */
        //Login
        Data = new Login();
        //send variables to login class
        Data.setUID(jTxtUID.getText());
        Data.setPWD(jPwd.getPassword());
        Data.setContext(jTxtContext.getText());
        Data.setTree(jTxtTree.getText());
        Data.setWindowsLogin(jChkWindows.isSelected());
        
       
        
    }
    
    
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Initialization Code">
    private void initComponents() {

        jPanData = new javax.swing.JPanel();
        jPanData.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBtnLogin = new javax.swing.JButton();
        jBtnAdvanced = new javax.swing.JButton();
        jBtnCancel = new javax.swing.JButton();
        lblContext = new javax.swing.JLabel();
        lblTree = new javax.swing.JLabel();
        jTxtUID = new javax.swing.JTextField();
        jPwd = new javax.swing.JPasswordField();
        jTxtContext = new javax.swing.JTextField();
        jTxtTree = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jChkWindows = new javax.swing.JCheckBox();

        jLabel1.setText("Username:");

        jLabel2.setText("Password:");

        jBtnLogin.setText("Login");
        jBtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnLoginActionPerformed(evt);
            }
        });

        jBtnAdvanced.setText("Advanced");
        jBtnAdvanced.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAdvancedActionPerformed(evt);
            }
        });

        jBtnCancel.setText("Cancel");
        jBtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelActionPerformed(evt);
            }
        });

        lblContext.setText("Context:");

        lblTree.setText("Tree:");

        jTxtUID.setText(System.getenv("USERNAME"));
        jTxtUID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtUIDFocusGained(evt);
            }
        });

        jPwd.setText(" ");
        jPwd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPwdFocusGained(evt);
            }
        });

        jTxtContext.setText("THS.TWB.SWQ.HEALTH");
        jTxtContext.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtContextFocusGained(evt);
            }
        });

        jTxtTree.setText("QH");
        jTxtTree.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtTreeFocusGained(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Please login using your Novell login.");

        jChkWindows.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jChkWindows.setText("Workstation only");

        javax.swing.GroupLayout jPanDataLayout = new javax.swing.GroupLayout(jPanData);
        jPanData.setLayout(jPanDataLayout);
        
        jPanDataLayout.setHorizontalGroup(
            jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanDataLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanDataLayout.createSequentialGroup()
                        .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnLogin)
                            .addComponent(jLabel2)
                            .addComponent(lblContext)
                            .addComponent(lblTree)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanDataLayout.createSequentialGroup()
                                .addComponent(jBtnAdvanced)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBtnCancel))
                            .addComponent(jTxtTree)
                            .addComponent(jTxtContext)
                            .addComponent(jTxtUID)
                            .addComponent(jPwd)))
                    .addGroup(jPanDataLayout.createSequentialGroup()
                        .addComponent(jChkWindows, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        
        jPanDataLayout.setVerticalGroup(
            jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(12, 12, 12)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtUID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContext)
                    .addComponent(jTxtContext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTree)
                    .addComponent(jTxtTree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnLogin)
                    .addComponent(jBtnAdvanced)
                    .addComponent(jBtnCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jChkWindows)
                .addContainerGap(11, Short.MAX_VALUE))
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

    private void jBtnLoginActionPerformed(java.awt.event.ActionEvent evt) {                                          
        //
        boolean ValidLogin = false;
        // clear user & login
        User.CurrentUser = new User();
        getResults();
        // now call the login code
        if (Data.doLogin()) {
            // true
            FinishUp();

        } else {
            // invalid login
            // warnings already sent so clear password field and wait for next attempt
            jPwd.setText("");

        }

    }                                         

            
    private void jBtnAdvancedActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // toggle visibility of context and tree, and title of button
        if (evt.getActionCommand().equals("Advanced")){

            // Advanced clicked - so hide stuff
            jBtnAdvanced.setActionCommand("Simple");
            jBtnAdvanced.setText("Simple");
            lblContext.setVisible(true);
            lblTree.setVisible(true);
            jTxtTree.setVisible(true);
            jTxtContext.setVisible(true);
        } else {
            // Simple clicked - so show stuff
            jBtnAdvanced.setActionCommand("Advanced");
            jBtnAdvanced.setText("Advanced");
            lblContext.setVisible(false);
            lblTree.setVisible(false);
            jTxtTree.setVisible(false);
            jTxtContext.setVisible(false);
        }
    }                                            

    private void jPwdFocusGained(java.awt.event.FocusEvent evt) {                                 
        jPwd.selectAll();
    }                                

    private void jTxtUIDFocusGained(java.awt.event.FocusEvent evt) {                                    
        jTxtUID.selectAll();
    }                                   

    private void jTxtContextFocusGained(java.awt.event.FocusEvent evt) {                                        
        jTxtContext.selectAll();
    }                                       

    private void jTxtTreeFocusGained(java.awt.event.FocusEvent evt) {                                     
        jTxtTree.selectAll();
    }                                    
    
    
    private void jBtnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        Data = new Login(); // return blank stuff
        Data.setUID("Cancelled");
        RetVar = false; // did not update
        setVisible(false); // returns control to initialting method (ReturnValue)
        dispose();
    }

        
    // 
    
    //Variables declaration 
    private javax.swing.JButton jBtnAdvanced;
    private javax.swing.JButton jBtnCancel;
    private javax.swing.JButton jBtnLogin;
    private javax.swing.JCheckBox jChkWindows;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField jPwd;
    private javax.swing.JTextField jTxtContext;
    private javax.swing.JTextField jTxtTree;
    private javax.swing.JTextField jTxtUID;
    private javax.swing.JLabel lblContext;
    private javax.swing.JLabel lblTree;
       
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanData;
        
    Login Data;
    boolean RetVar;

    // End of variables declaration
}
