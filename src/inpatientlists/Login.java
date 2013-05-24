
package inpatientlists;

import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author ross sellars
 * @created 24/05/2013 11:24
 */
public class Login {
    private  String UID;
    private  String PWD;
    private  String novContext; // Novell context
    private  String novTree;    // Novell tree
    private  boolean DoWindows; // user requests windows login



    public  void setUID(String nUID){
        UID = nUID;
    }
    public  void setPWD( char[] nPWD){
        PWD = new String(nPWD);
        // zero out array, just to be one the safe side
        Arrays.fill(nPWD,' ');
    }
    public  void setContext(String nContext) {
        novContext = nContext;
    }
    public  void setTree(String nTree) {
        novTree = nTree;
    }
    public  void setWindowsLogin(boolean WinLog) {
        DoWindows = WinLog;
    }
    private  boolean validateWindows() {
        /*
         * validates credentials against windows
         */
        boolean RetVar = false;



        RetVar = true; // just for the moment!  sshh don't tell anyone
        //JOptionPane.showMessageDialog(  null, "windows");
        return RetVar;
    }
    private  boolean validateNovell() {
        boolean RetVar = false;

//JOptionPane.showMessageDialog(  null, "novell");
        return RetVar;
    }



    public  boolean doLogin () {

        User.CurrentUser = new User(UID);
        boolean Validated = false;
        boolean retVar = false;
        if (DoWindows) {
            Validated = validateWindows();
        } else {
            Validated = validateNovell();
        }
       
        /*
         * ok, so now we know if the used has a valid UID/PWD combination
         * but do they have access to the system too?
        */
        if (Validated) {
            // login OK, now check if they exist in system user access list
                        
            if( User.CurrentUser.isNewUser()) {
                // no existing user
                retVar = false;
                /*
                 * as this is only called after a valid win/Novell authentication
                 * request if user wants to be added to ACL
                 * they will be limited to view only, but available to be unlocked
                 * this makes it easier for me to add new user later...
                 */
                String strTemp = "Do you want to save your login details to allow access to this system.  ";
                     strTemp = strTemp + "Once created, your account will be limited to view only." ;
                int n = JOptionPane.showConfirmDialog(  null ,strTemp,"New User Found",JOptionPane.YES_NO_OPTION );
                 //0 = yes, 1 = No
                if (n==0) {
                    // yes selected
                    UserDialog giveItToMe = new UserDialog( User.CurrentUser);
                    boolean howDidItGo = giveItToMe.ReturnCompleted();
                    if (howDidItGo) {
                        JOptionPane.showMessageDialog(  null, "User successfully added to system access list.  Please log in.");
                    }
                }
        
            } else {
                
                // user exists
                // now check if locked

                if (User.CurrentUser.isLocked()){
                    // user exists but is locked out
                    // flag bad login and reset user to null
                    retVar = false;
                    User.CurrentUser.incrementNumberInvalidLogins();
                    User.CurrentUser = new User();
                    JOptionPane.showMessageDialog(  null ,"Your account is currently locked.","Login error",JOptionPane.WARNING_MESSAGE );
                }else{
                    //user exists and is not locked
                    User.CurrentUser.incrementNumberValidLogins();
                    retVar = true;
                }
        
            }        
  
        } else {
            /*
             *  invalid login
             *  if in user access list then flag as bad login
             *  if not on list, do nothing as they will NEVER get in
             *  this may result in information leak with repeated attempts
             * as they can tell who has access and who does not by wether they get locked out
             * but this probably doesnt matter here - could add a class variable with invalid users to stop this...(someday)
             */
                   
            retVar = false;
            if( User.CurrentUser.isNewUser()) {
                // new user, invalid login, just ignore it
                //maybe laater we could check for this to prevent brute force password attacks
            }else{
                // existing user, bad login
                User.CurrentUser.incrementNumberInvalidLogins();
                User.CurrentUser = new User();
            }
            JOptionPane.showMessageDialog(  null ,"Incorrect login credentials.","Login error",JOptionPane.WARNING_MESSAGE );
        }

        // clear password variable - just for good measure
        PWD = "";

        return retVar;

    }

}
