
package inpatientlists;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 18:57
 */
public class InpatientLists {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // connect to the database
         if (DatabaseInpatients.ConnectToDatabase()) {
            // call login dialog box
            User.CurrentUser = new User();
             //if valid login start inpatientList frame
            java.awt.EventQueue.invokeLater(new Runnable() {

                public void run() {
                    new InpatientFrame().setVisible(true);
                }
            });
    }
    }
}
