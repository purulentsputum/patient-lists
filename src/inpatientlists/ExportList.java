

package inpatientlists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rosssellars
 * @created 07/06/2013 20:37
 */
public class ExportList {
    
    ExportList(){  }
    
     
    
    
  public static class PatientList{
      public static void XML(PatientAdmissions[] list, String sqlDesc){
        
     }
     public static void Word(PatientAdmissions[] list, String sqlDesc){
        HTMLorWord(list,sqlDesc,".doc");
    }
    public static void HTML(PatientAdmissions[] list, String sqlDesc){
        HTMLorWord(list, sqlDesc, ".HTML");
    }
    
    private static void HTMLorWord(PatientAdmissions[] list, String sqlDesc, String extention) {
        
        //String aFileName =System.getProperty("user.home") + System.getProperty("file.separator") + MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime(), "YYYY-MM-DD HHmm") + " PatientList" + extention;
        String aFileName =Utilities.selectDirectory() + System.getProperty("file.separator") + MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime(), "YYYY-MM-DD HHmm") + " PatientList" + extention;
        JOptionPane.showMessageDialog(null, aFileName);
        
        String strTemp ;
        String line1;
        String line2;
        String line3;
                
        Path path = Paths.get(aFileName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset());
            // headers
            strTemp = "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=Content-Type content=\"text/html; charset=windows-1252\">\n" +
                    "<meta name=Generator content=\"Microsoft Word 14 (filtered)\">\n" +
                    "<style>\n" +
                    "<!--\n" +
                    " /* Style Definitions */\n" +
                    " p.MsoNormal, li.MsoNormal, div.MsoNormal\n" +
                    "	{margin:0cm;\n" +
                    "	margin-bottom:.0001pt;\n" +
                    "	font-size:12.0pt;\n" +
                    "	font-family:\"Times New Roman\",\"serif\";}\n" +
                    "p.msochpdefault, li.msochpdefault, div.msochpdefault\n" +
                    "	{mso-style-name:msochpdefault;\n" +
                    "	margin-right:0cm;\n" +
                    "	margin-left:0cm;\n" +
                    "	font-size:10.0pt;\n" +
                    "	font-family:\"Times New Roman\",\"serif\";}\n" +
                    ".MsoChpDefault\n" +
                    "	{font-size:10.0pt;}\n" +
                    "@page WordSection1\n" +
                    "	{size:21.0cm 842.0pt;\n" +
                    "	margin:1.0cm 1.0cm 1.0cm 1.0cm;}\n" +
                    "div.WordSection1\n" +
                    "	{page:WordSection1;}\n" +
                    "-->\n" +
                    "</style>\n" +
                    "\n" +
                    "</head>\n"+
                    "\n" +
                    "<body lang=EN-AU>\n" +
                    "\n" +
                    "<div class=WordSection1>\n";            
            writer.write(strTemp);
            writer.newLine();
            
            strTemp = "<p class=MsoNormal><span style='font-size:16.0pt;font-family:\"Arial\",\"sans-serif\"'>" +sqlDesc+ "</span></p>";
            writer.write(strTemp);
            writer.newLine();
            //table 
            strTemp = "<table class=MsoNormalTable border=0 cellspacing=0 cellpadding=0\n" +
                    " style='border-collapse:collapse'>";
            writer.write(strTemp);
            writer.newLine();
            //data elements in table
            for(PatientAdmissions data: list){
                writer.write("<tr>");
                writer.newLine();
                line1 = data.getPatient().getLastName() + ", " + data.getPatient().getFirstName();
                line2 = data.getAdmission().getAdmNo() + " " + MyDates.ConvertDateToString(data.getPatient().getDOB());
                line3 = data.getAdmission().getDischargeWard() + " / " + data.getAdmission().getBed();
                strTemp =   "<td width=189 valign=top style='width:142.0pt;border:solid windowtext 1.0pt;\n" +
                            "  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line1 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line2 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line3 +"</span></p>\n" +
                            "  </td>\n" ;
                writer.write(strTemp);
                writer.newLine();
                
                line1=data.getAdmission().getDrReferringName();
                line2=data.getAdmission().getDrReferringAddress();
                line3=data.getAdmission().getDrReferringSuburb() + "  " + data.getAdmission().getDrReferringPostCode();
                strTemp =   "<td width=174 valign=top style='width:130.65pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line1 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line2 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line3 +"</span></p>\n" +
                            "  </td>";
                writer.write(strTemp);
                writer.newLine();                
                
                line1=data.getAdmission().getDrTreating() + " " + data.getAdmission().getDischargeUnit();
                line2=MyDates.ConvertDateToString(data.getAdmission().getAdmDate()) + "  " + data.getAdmission().getAdmTime() + ", " + Integer.toString(data.getAdmission().getBedDays()) + " days";
                line3=AdmissionType.findAdmTypeDesc( data.getAdmission().getAdmType());
                strTemp =   "<td width=224 valign=top style='width:168.25pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line1 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line2 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line3 +"</span></p>\n" +
                            "  </td>";
                writer.write(strTemp);
                writer.newLine();
                
                line1="notes   ";
                line2="and tasks  ";
                line3="go here  ";
                strTemp =   "<td width=129 valign=top style='width:96.65pt;border-top:none;border-left:\n" +
                            "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                            "  padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line1 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line2 +"</span></p>\n" +
                            "  <p class=MsoNormal><span style='font-family:\"Arial\",\"sans-serif\"'>" + line3 +"</span></p>\n" +
                            "  </td>";
                writer.write(strTemp);
                writer.newLine();                
                
                writer.write(" </tr>");
                writer.newLine();
                
            }// end of for(PatientAdmissions data: list) loop
            
            // finish table and document
            strTemp = "</table>\n" +
                "\n" +
                "<p class=MsoNormal>Printed " + MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime()) + " by " + User.CurrentUser.getName() +" </p>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
            writer.write(strTemp);
            writer.newLine(); 
            writer.close();
            
            //ProcessBuilder pb = new ProcessBuilder(aFileName);
            //Process p = pb.start();
            Process p = new ProcessBuilder("explorer.exe", "/select,"+aFileName).start();
    
        } catch (IOException ex) {
            Logger.getLogger(ExportList.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
    }    
  }  
    
    public static class TaskList{
        
        public static void XML(TaskPatient[] list, String sqlDesc){
            
        }
        public static void Word(TaskPatient[] list, String sqlDesc){
            HTMLorWord(list,sqlDesc,".doc");
        }
        public static void HTML(TaskPatient[] list, String sqlDesc){
            HTMLorWord(list, sqlDesc, ".HTML");
        }
        public static void HTMLorWord(TaskPatient[] list, String sqlDesc, String extention){
            String aFileName =Utilities.selectDirectory() + System.getProperty("file.separator") + MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime(), "yyyy-MM-dd HHmm") + " TaskList" + extention;
        //JOptionPane.showMessageDialog(null, aFileName);
        
        String strTemp ;
        String line1;
        String line2;
        String line3;
        String line3a;
        String line4;
                
        Path path = Paths.get(aFileName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset());
            // headers
            strTemp = "<html>\n" +
                    "<head>\n" +
                    "<meta http-equiv=Content-Type content=\"text/html; charset=windows-1252\">\n" +
                    "<meta name=Generator content=\"Microsoft Word 14 (filtered)\">\n" +
                    "<style>\n" +
                    "<!--\n" +
                    " /* Style Definitions */\n" +
                    " p.MsoNormal, li.MsoNormal, div.MsoNormal\n" +
                    "	{margin:0cm;\n" +
                    "	margin-bottom:.0001pt;\n" +
                    "	font-size:12.0pt;\n" +
                    "	font-family:\"Times New Roman\",\"serif\";}\n" +
                    "p.msochpdefault, li.msochpdefault, div.msochpdefault\n" +
                    "	{mso-style-name:msochpdefault;\n" +
                    "	margin-right:0cm;\n" +
                    "	margin-left:0cm;\n" +
                    "	font-size:10.0pt;\n" +
                    "	font-family:\"Times New Roman\",\"serif\";}\n" +
                    ".MsoChpDefault\n" +
                    "	{font-size:10.0pt;}\n" +
                    "@page WordSection1\n" +
                    "	{size:21.0cm 842.0pt;\n" +
                    "	margin:1.0cm 1.0cm 1.0cm 1.0cm;}\n" +
                    "div.WordSection1\n" +
                    "	{page:WordSection1;}\n" +
                    "-->\n" +
                    "</style>\n" +
                    "\n" +
                    "</head>\n"+
                    "\n" +
                    "<body lang=EN-AU>\n" +
                    "\n" +
                    "<div class=WordSection1>\n";            
            writer.write(strTemp);
            writer.newLine();
            
            strTemp = "<p class=MsoNormal><span style='font-size:16.0pt'>" +sqlDesc+ "</span></p>";
            writer.write(strTemp);
            writer.newLine();
            //line 
            strTemp = "<p class=MsoNormal>&nbsp;</p>";
            writer.write(strTemp);
            writer.newLine();
            //each data element set in a separate table
            for(TaskPatient data: list){
                line1=data.getPatient().getOneLineDetails();
                line2=data.getAdmission().getAdmissionOneLine();
                line3=data.getTask().getTaskDesc();
                line3a=data.getTask().getUsersOneLine();
                line4= new Note(data.getPatient().getURN()).getNote();
                
                strTemp =
                    "<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0\n" +
                    " style='border-collapse:collapse;border:none'>\n" +
                    " <tr>\n" +
                    "  <td width=73 valign=top style='width:55.05pt;border:solid windowtext 1.0pt;\n" +
                    "  padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>Patient:</p>\n" +
                    "  </td>\n" +
                    "  <td width=659 valign=top style='width:494.4pt;border:solid windowtext 1.0pt;\n" +
                    "  border-left:none;padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>" + line1 + "</p>\n" +
                    "  </td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "  <td width=73 valign=top style='width:55.05pt;border:solid windowtext 1.0pt;\n" +
                    "  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>Admission:</p>\n" +
                    "  </td>\n" +
                    "  <td width=659 valign=top style='width:494.4pt;border-top:none;border-left:\n" +
                    "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                    "  padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>" + line2 + "</p>\n" +
                    "  </td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "  <td width=73 valign=top style='width:55.05pt;border:solid windowtext 1.0pt;\n" +
                    "  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>Task:</p>\n" +
                    "  </td>\n" +
                    "  <td width=659 valign=top style='width:494.4pt;border-top:none;border-left:\n" +
                    "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                    "  padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>" + line3 + "</p>\n" +
                    "  <p class=MsoNormal>" + line3a + "</p>\n" +    
                    "  </td>\n" +
                    " </tr>\n" +
                    " <tr>\n" +
                    "  <td width=73 valign=top style='width:55.05pt;border:solid windowtext 1.0pt;\n" +
                    "  border-top:none;padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>Notes:</p>\n" +
                    "  </td>\n" +
                    "  <td width=659 valign=top style='width:494.4pt;border-top:none;border-left:\n" +
                    "  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;\n" +
                    "  padding:0cm 5.4pt 0cm 5.4pt'>\n" +
                    "  <p class=MsoNormal>" + line4 + "</p>\n" +
                    "  </td>\n" +
                    " </tr>\n" +
                    "</table>\n" +
                    "\n" +
                    "<p class=MsoNormal>&nbsp;</p>" ;
                writer.write(strTemp);
                writer.newLine();
                
            }// end of for(TaskPatient data: list) loop
            
            // finish table and document
            strTemp = "<p class=MsoNormal>Printed " + MyDates.ConvertDateTimeToString(MyDates.CurrentDateTime()) + " by " + User.CurrentUser.getName() +" </p>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
            writer.write(strTemp);
            writer.newLine(); 
            writer.close();
            
            //ProcessBuilder pb = new ProcessBuilder(aFileName);
            //Process p = pb.start();
            Process p = new ProcessBuilder("explorer.exe", " /select,"+aFileName).start();
    
        } catch (IOException ex) {
            Logger.getLogger(ExportList.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        
        
        }
        
    }
}




