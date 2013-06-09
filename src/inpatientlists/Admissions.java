
package inpatientlists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 20:40
 * @edited 09/06/2013 added defensive copy and loadLatestAdmission()
 */
public class Admissions {
    private Date admDate;
    private String admNo;
    private String admTime;
    private String admType;
    private String admUnit;
    private String admWard;
    private String bed;
    private Integer bedDays;
    private Integer bedHours;
    private Date dateLastMoved;
    private String destination;
    private Date dischargeDate;
    private String dischargeStatus;
    private String dischargeTime;
    private String dischargeUnit;
    private String dischargeWard;
    private String drAdmitting;
    private String drReferringAddress;
    private String drReferringName;
    private String drReferringPostCode;
    private String drReferringSuburb;
    private String drTreating;
    private Integer episodes;
    private String urn;
    
    
    Admissions(){
        setDefaults();
    }
    Admissions(String admNo){
        loadData(admNo);
    }
    Admissions (Admissions adm){
        //defensive copy
        admDate= new Date(adm.getAdmDate().getTime());
        admNo = adm.getAdmNo();
        admTime = adm.getAdmTime();
        admType = adm.getAdmType();
        admUnit = adm.getAdmUnit();
        admWard = adm.getAdmWard();
        bed = adm.getBed();
        bedDays=adm.getBedDays();
        bedHours = adm.getBedHours();
        dateLastMoved = new Date(adm.getDateLastMoved().getTime());
        destination = adm.getDestination();
        if(adm.getDischargeDate()!=null){
            dischargeDate = new Date(adm.getDischargeDate().getTime());
        }else{
            dischargeDate = null;
        }        
        dischargeStatus = adm.getDischargeStatus();
        dischargeTime = adm.getDischargeTime();
        dischargeUnit = adm.getDischargeUnit();
        dischargeWard = adm.getDischargeWard();
        drAdmitting = adm.getDrAdmitting();
        drReferringAddress = adm.getDrReferringAddress();
        drReferringName = adm.getDrReferringName();
        drReferringPostCode = adm.getDrReferringPostCode();
        drReferringSuburb = adm.getDrReferringSuburb();
        drTreating = adm.getDrTreating();
        episodes = adm.getEpisodes();
        urn = adm.getUrn();
    }
    private void setDefaults(){
        admDate= null;
        admNo = "";
        admTime = "";
        admType = "";
        admUnit = "";
        admWard = "";
        bed = "";
        bedDays=0;
        bedHours = 0;
        dateLastMoved = null;
        destination = "";
        dischargeDate = null;
        dischargeStatus = "";
        dischargeTime = "";
        dischargeUnit = "";
        dischargeWard = "";
        drAdmitting = "";
        drReferringAddress = "";
        drReferringName = "";
        drReferringPostCode = "";
        drReferringSuburb = "";
        drTreating = "";
        episodes = 0;
        urn = "";
    }
    //getters
    public Date getAdmDate(){
        return admDate;
    }
    public String getAdmNo(){
        return admNo;
    }    
    public String getAdmTime(){
        return admTime;
    }
    public String getAdmType(){
        return admType;
    }
    public String getAdmUnit(){
        return admUnit;
    }
    public String getAdmWard(){
        return admUnit;
    }
    public String getBed(){
        return bed;
    }
    public Integer getBedDays(){
        return bedDays;
    }
    public Integer getBedHours(){
        return bedHours;
    }
    public Date getDateLastMoved(){
        return dateLastMoved;
    }
    public String getDestination(){
        return destination;
    }
    public Date getDischargeDate(){
        return dischargeDate;
    }
    public String getDischargeStatus(){
        return dischargeStatus;
    }
    public String getDischargeTime(){
        return dischargeTime;
    }
    public String getDischargeUnit(){
        return dischargeUnit;
    }
    public String getDischargeWard(){
        return dischargeWard;
    }
    public String getDrAdmitting(){
        return drAdmitting;
    }
    public String getDrReferringAddress(){
        return drReferringAddress;
    }
    public String getDrReferringName(){
        return drReferringName;
    }
    public String getDrReferringPostCode(){
        return drReferringPostCode;
    }
    public String getDrReferringSuburb(){
        return drReferringSuburb;
    }
    public String getDrTreating(){
        return drTreating;
    }
    public String getReferingDoctorOneLine(){
        return drReferringName + ", " + drReferringAddress + ", " + drReferringSuburb + "  " + drReferringPostCode; 
    }
    public Integer getEpisodes(){
        return episodes;
    }
    public String getUrn(){
        return urn;
    }
    public String getAdmissionOneLine(){
        String admDesc = new AdmissionType(admType).getAdmTypeDesc();
        return "Adm: " + MyDates.ConvertDateToString(admDate) + " ("+bedDays+" days) | " + dischargeWard + " / " + bed +" | " + dischargeUnit
                + " | "+ drTreating +" | " + admDesc;
    }
    public String getWardBed(){
        String retVar;
        if(this.dischargeStatus.length()>0){
            retVar = this.dischargeWard + " / " + bed;
        }else{
            retVar = "D/C";
        }
        return retVar;
    }
    
    //setters
    public void setAdmDate(Date admDate){
        this.admDate=admDate;
    }
    public void setAdmNo(String admNo){
        this.admNo=admNo;
    }    
    public void setAdmTime(String admTime){
        this.admTime=admTime;
    }
    public void setAdmType(String admType){
        this.admType=admType;
    }
    public void setAdmUnit(String admUnit){
        this.admUnit=admUnit;
    }
    public void setAdmWard(String admWard){
        this.admWard=admWard;
    }
    public void setBed(String bed){
        this.bed=bed;
    }
    public void setBedDays(Integer bedDays){
        this.bedDays=bedDays;
    }
    public void setBedHours(int bedHours){
        this.bedHours=bedHours;
    }
    public void setDateLastMoved(Date dateLastMoved){
        this.dateLastMoved=dateLastMoved;
    }
    public void setDestination(String destination){
        this.destination=destination;
    }
    public void setDischargeDate(Date dischargeDate){
        this.dischargeDate=dischargeDate;
    }
    public void setDischargeStatus(String dischargeStatus){
        this.dischargeStatus=dischargeStatus;
    }
    public void setDischargeTime(String dischargeTime){
        this.dischargeTime=dischargeTime;
    }
    public void setDischargeUnit(String dischargeUnit){
        this.dischargeUnit=dischargeUnit;
    }
    public void setDischargeWard(String dischargeWard){
        this.dischargeWard=dischargeWard;
    }
    public void setDrAdmitting(String drAdmitting){
        this.drAdmitting=drAdmitting;
    }
    public void setDrReferringAddress(String drReferringAddress){
        this.drReferringAddress=drReferringAddress;
    }
    public void setDrReferringName(String drReferringName){
        this.drReferringName=drReferringName;
    }
    public void setDrReferringPostCode(String drReferringPostCode){
        this.drReferringPostCode=drReferringPostCode;
    }
    public void setDrReferringSuburb(String drReferringSuburb){
        this.drReferringSuburb=drReferringSuburb;
    }
    public void setDrTreating(String drTreating){
        this.drTreating=drTreating;
    }
    public void setEpisodes(Integer episodes){
        this.episodes=episodes;
    }
    public void setURN(String urn){
        this.urn=urn;
    }
    //load and save
    public void loadData(String admNum){
        /**
         * loads individual data
         *  @param admNo patients UR Number
         */
        
        
        String strSQL = "SELECT tbl_002_admissions.* " +
                "FROM tbl_002_admissions  " +
                "WHERE(tbl_002_admissions.AdmNo = '" + admNo + "');";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                                
                admDate=rs.getDate("AdmDate");
                admNo=rs.getString("AdmNo");
                admTime=rs.getString("AdmTime");
                admType=rs.getString("AdmType");
                admUnit=rs.getString("AdmUnit");
                admWard=rs.getString("AdmWard");
                bed=rs.getString("Bed");                               
                bedDays=rs.getInt("BedDays");
                bedHours=rs.getInt("BedHours");
                dateLastMoved=rs.getDate("DateLastMoved");
                destination=rs.getString("Destination");
                dischargeDate=rs.getDate("DischargeDate");
                dischargeStatus=rs.getString("DischargeStatus");
                dischargeTime=rs.getString("DischargeTime");
                dischargeUnit=rs.getString("DischargeUnit");
                dischargeWard=rs.getString("DischargeWard");
                drAdmitting=rs.getString("DrAdmitting");
                drReferringAddress=rs.getString("DrReferringAddress");
                drReferringName=rs.getString("DrReferringName");
                drReferringPostCode=rs.getString("DrReferringPostCode");
                drReferringSuburb=rs.getString("DrReferringSuburb");
                drTreating=rs.getString("DrTreating");
                episodes=rs.getInt("Episodes");
                urn=rs.getString("URN");
                               
            }else{
                // new record
                setDefaults();                
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadAdmission").log(Level.SEVERE, null, ex);
        }
     
    }
        public void saveData(){
        /**
         * saves current data 
         * 
         */
        
        String strSQL = "SELECT tbl_002_admissions.* " +
                "FROM tbl_002_admissions  " +
                "WHERE(tbl_002_admissions.AdmNo = '" + admNo + "');";
        
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,false);
            ResultSet rs = dbData.getResultSet(); 
            rs.beforeFirst();
            if (rs.next()){
                // record exists
                rs.updateDate("AdmDate", MyDates.JavaDateToSQLasDate(admDate));
                rs.updateString("AdmNo",admNo);
                rs.updateString("AdmTime",admTime);
                rs.updateString("AdmType",admType);
                rs.updateString("AdmUnit",admUnit);
                rs.updateString("AdmWard",admWard);
                // do not save blank bed - will allow for manual entry of bed and not override it on update
                if((bed !=null)&&(bed.length()>0)){
                    rs.updateString("Bed",bed); 
                }                                              
                rs.updateInt("BedDays",bedDays);
                rs.updateInt("BedHours",bedHours);
                rs.updateDate("DateLastMoved",MyDates.JavaDateToSQLasDate(dateLastMoved));
                rs.updateString("Destination",destination);
                rs.updateDate("DischargeDate",MyDates.JavaDateToSQLasDate(dischargeDate));
                rs.updateString("DischargeStatus",dischargeStatus);
                rs.updateString("DischargeTime",dischargeTime);
                rs.updateString("DischargeUnit",dischargeUnit);
                rs.updateString("DischargeWard",dischargeWard);
                rs.updateString("DrAdmitting",drAdmitting);
                rs.updateString("DrReferringAddress",drReferringAddress);
                rs.updateString("DrReferringName",drReferringName);
                rs.updateString("DrReferringPostCode",drReferringPostCode);
                rs.updateString("DrReferringSuburb",drReferringSuburb);
                rs.updateString("DrTreating",drTreating);
                rs.updateInt("Episodes",episodes);
                rs.updateString("URN",urn);

                             
                rs.updateRow();
            }else{
                // new record
                
                rs.moveToInsertRow();
                
                rs.updateDate("AdmDate", MyDates.JavaDateToSQLasDate(admDate));
                rs.updateString("AdmNo",admNo);
                rs.updateString("AdmTime",admTime);
                rs.updateString("AdmType",admType);
                rs.updateString("AdmUnit",admUnit);
                rs.updateString("AdmWard",admWard);
                rs.updateString("Bed",bed);                               
                rs.updateInt("BedDays",bedDays);
                rs.updateInt("BedHours",bedHours);
                rs.updateDate("DateLastMoved",MyDates.JavaDateToSQLasDate(dateLastMoved));
                rs.updateString("Destination",destination);
                rs.updateDate("DischargeDate",MyDates.JavaDateToSQLasDate(dischargeDate));
                rs.updateString("DischargeStatus",dischargeStatus);
                rs.updateString("DischargeTime",dischargeTime);
                rs.updateString("DischargeUnit",dischargeUnit);
                rs.updateString("DischargeWard",dischargeWard);
                rs.updateString("DrAdmitting",drAdmitting);
                rs.updateString("DrReferringAddress",drReferringAddress);
                rs.updateString("DrReferringName",drReferringName);
                rs.updateString("DrReferringPostCode",drReferringPostCode);
                rs.updateString("DrReferringSuburb",drReferringSuburb);
                rs.updateString("DrTreating",drTreating);
                rs.updateInt("Episodes",episodes);
                rs.updateString("URN",urn);
                
                rs.insertRow();
            }
            rs.close();
            //PracticeLocality.LoadLocality(PracLocalityKey);
            
        } catch (SQLException ex) {
            Logger.getLogger("saveAdmission").log(Level.SEVERE, null, ex);
        }
    }
        
    public static Admissions[] loadPatientAdmissions(String urn){
        /**
         * loads individual data
         *  @param urn patients UR Number
         */
        
          Admissions[] retVar = new  Admissions[0];
        String strSQL = "SELECT tbl_002_admissions.* " +
                "FROM tbl_002_admissions  " +
                "WHERE(tbl_002_admissions.URN = '" + urn + "') " + 
                "ORDER BY tbl_002_admissions.AdmDate DESC;";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
             // set counter
            rs.last();
            int intRows = rs.getRow();
            
            if(intRows>0){
                retVar = new  Admissions[intRows];

                rs.beforeFirst(); // because rs.next() in while statement advances position
                int LoopVar = -1;
            
                rs.beforeFirst();
                while (rs.next()){
                    LoopVar++;
                    retVar[LoopVar] = new  Admissions();
                               
                    retVar[LoopVar].admDate=rs.getDate("AdmDate");
                    retVar[LoopVar].admNo=rs.getString("AdmNo");
                    retVar[LoopVar].admTime=rs.getString("AdmTime");
                    retVar[LoopVar].admType=rs.getString("AdmType");
                    retVar[LoopVar].admUnit=rs.getString("AdmUnit");
                    retVar[LoopVar].admWard=rs.getString("AdmWard");
                    retVar[LoopVar].bed=rs.getString("Bed");                               
                    retVar[LoopVar].bedDays=rs.getInt("BedDays");
                    retVar[LoopVar].bedHours=rs.getInt("BedHours");
                    retVar[LoopVar].dateLastMoved=rs.getDate("DateLastMoved");
                    retVar[LoopVar].destination=rs.getString("Destination");
                    retVar[LoopVar].dischargeDate=rs.getDate("DischargeDate");
                    retVar[LoopVar].dischargeStatus=rs.getString("DischargeStatus");
                    retVar[LoopVar].dischargeTime=rs.getString("DischargeTime");
                    retVar[LoopVar].dischargeUnit=rs.getString("DischargeUnit");
                    retVar[LoopVar].dischargeWard=rs.getString("DischargeWard");
                    retVar[LoopVar].drAdmitting=rs.getString("DrAdmitting");
                    retVar[LoopVar].drReferringAddress=rs.getString("DrReferringAddress");
                    retVar[LoopVar].drReferringName=rs.getString("DrReferringName");
                    retVar[LoopVar].drReferringPostCode=rs.getString("DrReferringPostCode");
                    retVar[LoopVar].drReferringSuburb=rs.getString("DrReferringSuburb");
                    retVar[LoopVar].drTreating=rs.getString("DrTreating");
                    retVar[LoopVar].episodes=rs.getInt("Episodes");
                    retVar[LoopVar].urn=rs.getString("URN");
                               
                }
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadAdmissionList").log(Level.SEVERE, null, ex);
        }
        
        return retVar;
     
    }
     
    public static Admissions loadLatestPatientAdmissions(String urn){
        /**
         * loads individual data
         *  @param urn patients UR Number
         */
        
        Admissions retVar = new  Admissions();
        String strSQL = "SELECT tbl_002_admissions.* " +
                "FROM tbl_002_admissions  " +
                "WHERE(tbl_002_admissions.URN = '" + urn + "') " + 
                "ORDER BY tbl_002_admissions.AdmDate DESC;";
        try {
            // get resultset
            DatabaseInpatients dbData = new DatabaseInpatients(strSQL,true);
            ResultSet rs = dbData.getResultSet(); 
            
            rs.beforeFirst();
            
            if(rs.next()){                               
                               
                    retVar.admDate=rs.getDate("AdmDate");
                    retVar.admNo=rs.getString("AdmNo");
                    retVar.admTime=rs.getString("AdmTime");
                    retVar.admType=rs.getString("AdmType");
                    retVar.admUnit=rs.getString("AdmUnit");
                    retVar.admWard=rs.getString("AdmWard");
                    retVar.bed=rs.getString("Bed");                               
                    retVar.bedDays=rs.getInt("BedDays");
                    retVar.bedHours=rs.getInt("BedHours");
                    retVar.dateLastMoved=rs.getDate("DateLastMoved");
                    retVar.destination=rs.getString("Destination");
                    retVar.dischargeDate=rs.getDate("DischargeDate");
                    retVar.dischargeStatus=rs.getString("DischargeStatus");
                    retVar.dischargeTime=rs.getString("DischargeTime");
                    retVar.dischargeUnit=rs.getString("DischargeUnit");
                    retVar.dischargeWard=rs.getString("DischargeWard");
                    retVar.drAdmitting=rs.getString("DrAdmitting");
                    retVar.drReferringAddress=rs.getString("DrReferringAddress");
                    retVar.drReferringName=rs.getString("DrReferringName");
                    retVar.drReferringPostCode=rs.getString("DrReferringPostCode");
                    retVar.drReferringSuburb=rs.getString("DrReferringSuburb");
                    retVar.drTreating=rs.getString("DrTreating");
                    retVar.episodes=rs.getInt("Episodes");
                    retVar.urn=rs.getString("URN");                               
                
            }
            rs.close();            
            
        } catch (SQLException ex) {
            Logger.getLogger("LoadLatestAdmission").log(Level.SEVERE, null, ex);
        }
        
        return retVar;
     
    }
        
        
}

/*
AdmDate
AdmNo
AdmTime
AdmType
AdmUnit
AdmWard
Bed
BedDays
BedHours
DateLastMoved
Destination
DischargeDate
DischargeStatus
DischargeTime
DischargeUnit
DischargeWard
DrAdmitting
DrReferringAddress
DrReferringName
DrReferringPostCode
DrReferringSuburb
DrTreating
Episodes
URN
Issues
Plan
Results
Updated
*/
