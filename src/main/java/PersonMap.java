import java.util.*;
import java.util.Map;
public class PersonMap {
    private final TreeMap<Person, ArrayList<String>> people;
    /* Constructs a TreeMap of people that is sorted according to the comparator specified

     */
    // We can use this method to instantiate a new HashMap every time we need to resort.
    public PersonMap(){
        people = new TreeMap<>();
    }
    public PersonMap(Comparator<? super Person> comp) {
        people = new TreeMap<>(comp);
    }
    public void put(Person key, ArrayList<String> values){
        people.put(key, values);
    }
    public Set<Map.Entry<Person, ArrayList<String>>> entrySet(){
        Set<Map.Entry<Person, ArrayList<String>>> asSet = new HashSet<>();
        for (Map.Entry<Person, ArrayList<String>> entry : people.entrySet()){
            asSet.add(entry);
        }
        return asSet;
    }
    public void clear(){
        people.clear();
    }
    public boolean containsKey(String key){
        return people.containsKey(key);
    }
    public boolean containsValue(ArrayList<String> arr){
        return people.containsValue(arr);
    }
    public Set<Person> keySet(){
        Set<Person> setOfPeople = new HashSet<>();
        for (Person key : people.keySet()){
            setOfPeople.add(key);
        }
        return setOfPeople;
    }
    public ArrayList<String> remove(Person key){
        return people.remove(key);
    }
    public ArrayList<String> get(Person key){
        final ArrayList<String> listOfTimes = new ArrayList();
        for (String str: people.get(key)){
            listOfTimes.add(str);
        }
        return listOfTimes;
    }
    public int size(){
        return people.size();
    }
}
