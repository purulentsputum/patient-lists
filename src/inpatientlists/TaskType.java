
package inpatientlists;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ross sellars
 * @created 09/06/2013 14:57
 */
public enum TaskType {
    
    CatA ("A","Consultant review"),
    CatB ("B","Registrar review"),
    CatC ("C","Resident review"),
    CatD ("D","No routine review"),
    CatH ("H","Handover"),
    CatT ("T","To do");
    
    private String key;
    private String desc;
    
    
    /**
     * A mapping between the integer code and its corresponding Status to facilitate lookup by code.
     */
    private static Map<String, TaskType> keyToTaskTypeMapping;
    private static Map<String, TaskType> descToTaskTypeMapping;
    
    private TaskType(String key,String desc){
        this.key = key;
        this.desc = desc;
    }
    public static TaskType getTaskTypeFromKey(String key){
        if (keyToTaskTypeMapping == null) {
            initMappingKey();
        }
        return keyToTaskTypeMapping.get(key);
    }
    private static void initMappingKey() {
        keyToTaskTypeMapping = new HashMap<String, TaskType>();
        for (TaskType s : values()) {
            keyToTaskTypeMapping.put(s.key, s);
        }
    }
    public static TaskType getTaskTypeFromDesc(String desc){
        if (descToTaskTypeMapping == null) {
            initMappingDesc();
        }
        return descToTaskTypeMapping.get(desc);
    }
    private static void initMappingDesc() {
        descToTaskTypeMapping = new HashMap<String, TaskType>();
        for (TaskType s : values()) {
            descToTaskTypeMapping.put(s.desc, s);
        }
    }
    public String getDesc(){return desc;}
    public String getKey(){return key;}
    public static TaskType getDefault(){
        return TaskType.CatD;
    }
}
