
package inpatientlists;

import java.util.Date;

/**
 *
 * @author ross sellars
 * @created 20/05/2013 20:40
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
    public Integer getEpisodes(){
        return episodes;
    }
    public String getUrn(){
        return urn;
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
    public void setAdmWard(String admUnit){
        this.admUnit=admUnit;
    }
    public void setBed(String bed){
        this.bed=bed;
    }
    public void setBedDays(Integer bedDays){
        this.bedDays=bedDays;
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
    public void setUrn(String urn){
        this.urn=urn;
    }
    //load and save
    public void loadData(String admNum){
        
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
