

package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author ross sellars
 * @created 21/05/2013 19:26
 * @edited 22/05/2013 18:44 added loadData()
 */
public class PatientAdmissions {

    private Patients patient;
    private Admissions admission;
    
    PatientAdmissions(){
        patient = new Patients();
        admission = new Admissions();
    }
    PatientAdmissions(String admNo){
        loadData(admNo);
    }
    PatientAdmissions (PatientAdmissions patientAdmissions){
        //defensive copy
        
    }
    
    public Patients getPatient(){
        return patient;
    }
    public Admissions getAdmission(){
        return admission;
    }
    
    private void loadData(String admNo){
         String strSQL = "SELECT Tbl_001_Patients.*, Tbl_002_Admissions.* " +
                "FROM Tbl_001_Patients " +
                "INNER JOIN Tbl_002_Admissions ON Tbl_001_Patients.URN = Tbl_002_Admissions.URN " +
                "WHERE (((Tbl_002_Admissions.AdmNo)='" + admNo +"'));" ;
         PatientAdmissions[] patientData =  loadCurrentInpatientsFiltered(strSQL);
         
    }
    
    public static PatientAdmissions[] loadAllPatientAdmissions(String urn){
        String strSQL = "SELECT Tbl_001_Patients.*, Tbl_002_Admissions.* " +
                "FROM Tbl_001_Patients " +
                "INNER JOIN Tbl_002_Admissions ON Tbl_001_Patients.URN = Tbl_002_Admissions.URN " +
                "WHERE (((Tbl_001_Patients.URN)='" + urn +"'));" ;
         return loadCurrentInpatientsFiltered(strSQL);
    }
    public static PatientAdmissions[] loadCurrentInpatients(){
        String strSQL = "SELECT Tbl_001_Patients.*, Tbl_002_Admissions.* " +
            "FROM Tbl_001_Patients " +
            "INNER JOIN Tbl_002_Admissions ON Tbl_001_Patients.URN = Tbl_002_Admissions.URN " +
            "WHERE (((Tbl_002_Admissions.DischargeStatus)=''));";
        return loadCurrentInpatientsFiltered(strSQL);
    }
    public static PatientAdmissions[] loadCurrentInpatientsFiltered(String strSQL){
         /**
         * loads individual user
         *  @param strSQL SQL statement 
         */
        
         PatientAdmissions[] retVar = new PatientAdmissions[0];
                               
         try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new PatientAdmissions[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                    //patients
                    retVar[LoopVar] = new PatientAdmissions();
                    retVar[LoopVar].getPatient().setFirstName(rs.getString("GivenNames")); 
                    retVar[LoopVar].getPatient().setLastName(rs.getString("Surname"));
                    retVar[LoopVar].getPatient().setDOB(rs.getDate("DateOfBirth"));
                    retVar[LoopVar].getPatient().setSex(rs.getString("Gender"));
                    retVar[LoopVar].getPatient().setURN(rs.getString("URN"));
                    //admissions
                    retVar[LoopVar].getAdmission().setAdmDate(rs.getDate("AdmDate"));
                    retVar[LoopVar].getAdmission().setAdmNo(rs.getString("AdmNo"));
                    retVar[LoopVar].getAdmission().setAdmTime(rs.getString("AdmTime"));
                    retVar[LoopVar].getAdmission().setAdmType(rs.getString("AdmType")); 
                    retVar[LoopVar].getAdmission().setAdmUnit(rs.getString("AdmUnit"));
                    retVar[LoopVar].getAdmission().setAdmWard(rs.getString("AdmWard"));
                    retVar[LoopVar].getAdmission().setBed(rs.getString("Bed"));
                    retVar[LoopVar].getAdmission().setBedDays(rs.getInt("BedDays"));
                    retVar[LoopVar].getAdmission().setDateLastMoved(rs.getDate("DateLastMoved"));
                    retVar[LoopVar].getAdmission().setDestination(rs.getString("Destination"));
                    retVar[LoopVar].getAdmission().setDischargeDate(rs.getDate("DischargeDate"));
                    retVar[LoopVar].getAdmission().setDischargeStatus(rs.getString("DischargeStatus"));
                    retVar[LoopVar].getAdmission().setDischargeTime(rs.getString("DischargeTime"));
                    retVar[LoopVar].getAdmission().setDischargeUnit(rs.getString("DischargeUnit"));
                    retVar[LoopVar].getAdmission().setDischargeWard(rs.getString("DischargeWard"));
                    retVar[LoopVar].getAdmission().setDrAdmitting(rs.getString("DrAdmitting"));
                    retVar[LoopVar].getAdmission().setDrReferringAddress(rs.getString("DrReferringAddress"));
                    retVar[LoopVar].getAdmission().setDrReferringName(rs.getString("DrReferringName"));
                    retVar[LoopVar].getAdmission().setDrReferringPostCode(rs.getString("DrReferringPostCode"));
                    retVar[LoopVar].getAdmission().setDrReferringSuburb(rs.getString("DrReferringSuburb"));
                    retVar[LoopVar].getAdmission().setDrTreating(rs.getString("DrTreating"));
                    retVar[LoopVar].getAdmission().setEpisodes(rs.getInt("Episodes"));
                    retVar[LoopVar].getAdmission().setURN(rs.getString("URN"));
                                                 
                }
            }
            
            rs.close();

            } catch (SQLException ex) {
            Logger.getLogger("LoadAllPatientAdmissions").log(Level.SEVERE, null, ex);
            }
            return retVar;
    }
            
}
