

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
        
    }
    
    
    private javax.swing.JButton jBtnClose;
    private javax.swing.JButton jBtnEdit;
    private javax.swing.JButton jBtnNew;
    
    private javax.swing.JTable jTblData;
    
    private javax.swing.JScrollPane jScrollPane1;
    
    private javax.swing.JPanel jPanMain;
    private javax.swing.JPanel jPanButtons;
    private javax.swing.JPanel jPanData;
    
    private User[] DataList;
    private boolean retVar;
}
