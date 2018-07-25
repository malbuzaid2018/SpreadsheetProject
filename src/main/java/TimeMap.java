import java.util.Map;
import java.util.*;

public class TimeMap {
    private TreeMap<String, ArrayList<String>> theTimeMap = new TreeMap<>();

    public void put(String key, ArrayList<String> values){
        theTimeMap.put(key, values);
    }
    public void addToListOfAvailabilites(String key, String person){
        theTimeMap.get(key).add(person);
    }
    public void removeFromListOfAvailable(String key, String person){
        theTimeMap.get(key).remove(person);
    }

}
