

package inpatientlists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import com.northgateis.reality.realsql.RealSQLDriver;
import com.northgateis.reality.realsql.RealSQLResultSet;
import com.northgateis.reality.realsql.RealSQLConnection;




/**
 *
 * @author rosssellars
 * @created 11/06/2013 23:35
 */
public class DatabaseHBCIS {
    public static Connection connection;
    private ResultSet rs;
    
    DatabaseHBCIS( String strSQL){
        /**
         * establishes a connection to the HBCIS database via SQL-live
         * queries table based on the request         
         */ 
        
        try {
            //Get a Statement object
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

            rs = statement.executeQuery(strSQL);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseInpatients.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet getResultSet(){
        return rs;
    }
     public static boolean ConnectToDatabase() {

        //String urlResp = "jdbc:mysql://192.168.2.5:3306/respiratory";
        
        String urlResp = "jdbc:realsql://host/SYSMAN";
        String UID = "eicat";
        String PWD = "xxxxxx";
        Boolean boolRetVar = false;

        try{
            Class.forName("com.northgateis.reality.realsql.RealSQLDriver").newInstance();
         }catch(Exception exp){
            JOptionPane.showMessageDialog(   null ,"An error occured with the Class (" + exp.getMessage() + ")"  );
            Logger.getLogger(DatabaseInpatients.class.getName()).log(Level.SEVERE, null, exp);
            System.err.println(exp);
         }

        
        try{    
            connection = DriverManager.getConnection(urlResp,UID, PWD);



            // last thing - set return to true (I did it, I did it ...)
            boolRetVar = true;
        }
        catch (Exception err) {
            boolRetVar = false;
            JOptionPane.showMessageDialog(   null ,"An error occured connecting to the database (" + err.getMessage() + ")"  );            
        }
        return boolRetVar;
    }
     public static Doctors[] getDoctorList(){
         //SELECT DISTINCT SYSMAN_Doctors.DrCode, SYSMAN_Doctors.Title, SYSMAN_Doctors.GivenNames, SYSMAN_Doctors.Surname FROM SYSMAN_Doctors INNER JOIN InpatientList ON SYSMAN_Doctors.DrCode = InpatientList.DrTreating ORDER BY SYSMAN_Doctors.DrCode;
         Doctors[] retVar = new Doctors[0];
         
         
         return retVar;
     }
     public static String[] getWardList(){
         String[] retVar = new String[0];
         
         return retVar;
     }
     public static String[] getUnitList(){
        String[] retVar = new String[0];
         
         return retVar;    
     }
     public static PatientAdmissions[] getCurrentInpatients(){
         PatientAdmissions[] retVar = new PatientAdmissions[0];
         
         return retVar;
     }
     
     public static Admissions getAdmissionDetails(String admNo){
         String strSQL = "SELECT SYSMAN_Admissions.* FROM SYSMAN_Admissions "+
                 "WHERE ((SYSMAN_Admissions.AdmNo)='" + admNo + "');";
         Admissions retVar = new Admissions();
         
         try{
             Boolean isItOk = DatabaseHBCIS.ConnectToDatabase();
             if(isItOk){
                    DatabaseHBCIS hbcisData = new DatabaseHBCIS(strSQL);
                    ResultSet rs = hbcisData.getResultSet();
                    rs.beforeFirst();
                    if (rs.next()){
                        // record exists                        
                        retVar.setAdmDate(rs.getDate("AdmDate"));
                        retVar.setAdmNo(admNo);
                        retVar.setAdmTime(rs.getString("AdmTime"));
                        retVar.setAdmType(rs.getString("AdmType"));
                        retVar.setAdmUnit(rs.getString("AdmUnit"));
                        retVar.setAdmWard(rs.getString("AdmWard"));
                        retVar.setBed(rs.getString("Bed"));                               
                        retVar.setBedDays(rs.getInt("BedDays"));
                        retVar.setBedHours(rs.getInt("BedHours"));
                        retVar.setDateLastMoved(rs.getDate("DateLastMoved"));
                        retVar.setDestination(rs.getString("Destination"));
                        retVar.setDischargeDate(rs.getDate("DischargeDate"));
                        retVar.setDischargeStatus(rs.getString("DischargeStatus"));
                        retVar.setDischargeTime(rs.getString("DischargeTime"));
                        retVar.setDischargeUnit(rs.getString("DischargeUnit"));
                        retVar.setDischargeWard(rs.getString("DischargeWard"));
                        retVar.setDrAdmitting(rs.getString("DrAdmitting"));
                        retVar.setDrReferringAddress(rs.getString("DrReferringAddress"));
                        retVar.setDrReferringName(rs.getString("DrReferringName"));
                        retVar.setDrReferringPostCode(rs.getString("DrReferringPostCode"));
                        retVar.setDrReferringSuburb(rs.getString("DrReferringSuburb"));
                        retVar.setDrTreating(rs.getString("DrTreating"));
                        retVar.setEpisodes(rs.getInt("Episodes"));
                        int index = admNo.indexOf("-");// location of separator between URN and episode
                        retVar.setURN(admNo.substring(0, index+1));                        
                    }
                    rs.close();
                    
             }
         
         } catch (Exception ex) {
            Logger.getLogger("getPatientDetails").log(Level.SEVERE, null, ex);
         } 
         return retVar;
     }
     
     public static Patients getPatientDetails(String urn){
         String strSQL = "SELECT SYSMAN_Patients.* FROM SYSMAN_Patients "+
                 "WHERE ((SYSMAN_Patients.UrNo)='" + urn + "');";
         Patients retVar = new Patients();
         
         try{
             Boolean isItOk = DatabaseHBCIS.ConnectToDatabase();
             if(isItOk){
                    DatabaseHBCIS hbcisData = new DatabaseHBCIS(strSQL);
                    ResultSet rs = hbcisData.getResultSet();
                    rs.beforeFirst();
                    if (rs.next()){
                        // record exists
                        retVar.setFirstName(rs.getString("GivenNames"));
                        retVar.setLastName(rs.getString("Surname"));
                        retVar.setURN(rs.getString("UrNo"));
                        retVar.setSex(rs.getString("Gender"));
                        retVar.setDOB(rs.getDate("DateOfBirth"));
                    }
                    rs.close();
                    
             }
         
         } catch (Exception ex) {
            Logger.getLogger("getPatientDetails").log(Level.SEVERE, null, ex);
         } 
         return retVar;
     }
    
}
