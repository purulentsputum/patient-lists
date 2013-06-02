

package inpatientlists;

/**
 *
 * @author ross sellars
 * @created 02/06/2013
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class zzzTempStuff {

    zzzTempStuff(){
    }
    
    public static void readCSV(){
        BufferedReader in = null;
        Admissions adm;
        String temp;
        int index;
        String part1;
        String part2;
       
        
        try {
            String fileName = "E:/temp/Tbl_009_PastAdmissions.csv";
            in = new BufferedReader(new FileReader(fileName));
            String line = null;
            
            line = in.readLine();
            while (line !=null){
                System.out.println(line);
                
                index = line.indexOf(",");
                part1 = line.substring(0, index);
                                               
                line = line.substring(index+1);
                index = line.indexOf(",");                
                part2= line.substring(0, index);
                
                adm = new Admissions(part2);
                adm.setAdmDate(MyDates.ConvertStringToDate(part1,"dd/MM/yyyy"));
                adm.setAdmNo(part2);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setAdmTime(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setAdmType(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setAdmUnit(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setAdmWard(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setBed(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setBedDays(Utilities.convertStringToInt(temp));
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setBedHours(Utilities.convertStringToInt(temp));
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDateLastMoved(MyDates.ConvertStringToDate(temp,"dd/mm/yyyy"));
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDestination(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDischargeDate(MyDates.ConvertStringToDate(temp,"dd/mm/yyyy"));
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDischargeStatus(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDischargeTime(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDischargeUnit(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDischargeWard(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDrAdmitting(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDischargeDate(MyDates.ConvertStringToDate(temp,"dd/mm/yyyy"));
                
               // line = line.substring(index+1);
               // index = line.indexOf(",");                
               // temp = line.substring(0, index);
               // adm.setDrReferringAddress(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDrReferringName(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDrReferringPostCode(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDrReferringSuburb(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setDrTreating(temp);
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setEpisodes(Utilities.convertStringToInt(temp));
                
                line = line.substring(index+1);
                index = line.indexOf(",");                
                temp = line.substring(0, index);
                adm.setURN(temp);
                
                adm.saveData();
                
                line=in.readLine();
            }
            

            
            /*
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                Scanner scanner = new Scanner(line);
             String sep = "";
             while (scanner.hasNext()) {
              System.out.println(sep + scanner.next());
              sep = "";
              }}
              */
              
          
        } catch (FileNotFoundException ex) {
            Logger.getLogger(zzzTempStuff.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(zzzTempStuff.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(zzzTempStuff.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
