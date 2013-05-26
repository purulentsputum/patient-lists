

package inpatientlists;

/**
 *
 * @author ross sellars
 * @created 25/05/2013 23:11
 */
public class TaskTypes {
    private String key;
    private String desc;
    
    private static TaskTypes[] typeArray;
    
    TaskTypes(){
        loadData();
        key = TaskTypes.typeArray[5].getKey();
        desc = TaskTypes.typeArray[5].getDesc();
    }
    TaskTypes (String key){
        loadData();
        for(int i=0; i<(TaskTypes.typeArray.length);i++){
            if(TaskTypes.typeArray[i].getKey().equals(key)){
                key = TaskTypes.typeArray[i].getKey();
                desc = TaskTypes.typeArray[i].getDesc();
            }
        }
       
    }
    TaskTypes(TaskTypes taskType){
        //defensive copy
        key = new String (taskType.getKey());
        desc = new String (taskType.getDesc());
    }
    
    public String getKey(){
        return key;
    }
    public String getDesc(){
        return desc;
    } 
    
    public static TaskTypes[] getArray(){
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
        typeArray = new TaskTypes[6];
        typeArray[0].key="A";
        typeArray[0].desc ="Consultant review";
        typeArray[1].key="B";
        typeArray[1].desc ="Registrar review";
        typeArray[2].key="C";
        typeArray[2].desc = "Resident review";
        typeArray[3].key ="D";
        typeArray[3].desc="No routine review";
        typeArray[4].key ="H";
        typeArray[4].desc="Handover";
        typeArray[5].key ="T";
        typeArray[5].desc="To do";
        
    }
}
