

package inpatientlists;

/**
 *
 * @author ross sellars
 * @created 25/05/2013 23:11
 */
public class WeekendReviewTypes {
    private String key;
    private String desc;
    
    private static WeekendReviewTypes[] typeArray;
    
    WeekendReviewTypes(){
        loadData();
    }
    
    public String getKey(){
        return key;
    }
    public String getDesc(){
        return desc;
    } 
    
    public static WeekendReviewTypes[] getArray(){
        return typeArray;
    }
    public static String getDesc(String key){
        String retVar="";
        for (int i=0;i<typeArray.length;i++){
            if(typeArray[i].getKey().equals(key)){
                retVar = typeArray[i].getDesc();
            }
        }
        return retVar;
    }
    
    private static void loadData(){
        typeArray = new WeekendReviewTypes[4];
        typeArray[0].key="A";
        typeArray[0].desc ="Consultant review";
        typeArray[1].key="B";
        typeArray[1].desc ="Registrar review";
        typeArray[2].key="C";
        typeArray[2].desc = "Resident review";
        typeArray[3].key ="D";
        typeArray[3].desc="No routine review";
        
    }
}
