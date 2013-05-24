

package inpatientlists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 22:37
 */
public class DatabaseInpatients {

    
    public static Connection conResp;
    private ResultSet rs;
    
    DatabaseInpatients(String strSQL){
        try {
            //Get a Statement object
            Statement stmtDB = conResp.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

            rs = stmtDB.executeQuery(strSQL);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseInpatients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    DatabaseInpatients(String strSQL, boolean ReadOnly){
        try {
            //Get a Statement object
            Statement stmtDB;
            if (ReadOnly) {
                stmtDB = conResp.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            }else{
                stmtDB = conResp.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            }
            
            rs = stmtDB.executeQuery(strSQL);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseInpatients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet getResultSet(){
        return rs;
    }
    
    

    public static boolean ConnectToDatabaseMSaccess() {

        //String urlResp = "jdbc:mysql://192.168.2.5:3306/respiratory";
        
        String urlResp = "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=E:/temp/Inpatients_data.mdb;";
        String UID = "";
        String PWD = "";
        Boolean boolRetVar = false;

        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
         }catch(Exception exp){
            JOptionPane.showMessageDialog(   null ,"An error occured with the Class (" + exp.getMessage() + ")"  );
            Logger.getLogger(DatabaseInpatients.class.getName()).log(Level.SEVERE, null, exp);
            System.err.println(exp);
         }

        
        try{    
            conResp = DriverManager.getConnection(urlResp,UID, PWD);



            // last thing - set return to true (I did it, I did it ...)
            boolRetVar = true;
        }
        catch (Exception err) {
            boolRetVar = false;
            JOptionPane.showMessageDialog(   null ,"An error occured connecting to the database (" + err.getMessage() + ")"  );            
        }
        return boolRetVar;
    }
    
    
    public static boolean ConnectToDatabase() {

        //String urlResp = "jdbc:mysql://192.168.2.5:3306/respiratory";
        
        String urlResp = "jdbc:mysql://localhost:3306/inpatient_list";
        String UID = "admin";
        String PWD = "admin";
        Boolean boolRetVar = false;

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
         }catch(Exception exp){
            JOptionPane.showMessageDialog(   null ,"An error occured with the Class (" + exp.getMessage() + ")"  );
            Logger.getLogger(DatabaseInpatients.class.getName()).log(Level.SEVERE, null, exp);
            System.err.println(exp);
         }

        
        try{    
            conResp = DriverManager.getConnection(urlResp,UID, PWD);



            // last thing - set return to true (I did it, I did it ...)
            boolRetVar = true;
        }
        catch (Exception err) {
            boolRetVar = false;
            JOptionPane.showMessageDialog(   null ,"An error occured connecting to the database (" + err.getMessage() + ")"  );            
        }
        return boolRetVar;
    }
    
    
}
