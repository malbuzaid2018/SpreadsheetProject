import java.util.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

public class PersonMap {
    private final TreeMap<String, Person> people;
    /* Constructs a TreeMap of people that is sorted according to the comparator specified

     */
    // We can use this method to instantiate a new HashMap every time we need to resort.
    public PersonMap(Comparator<? super String> comp) {
        people = new TreeMap<>(comp);
    }
    public void put(String key, Person value){
        people.put(key, value);
    }
    public Set<Map.Entry<String, Person>> entrySet(){
        return people.entrySet();
    }
    public void clear(){
        people.clear();
    }
    public boolean containsKey(String person){
        return people.containsKey(person);
    }
    public boolean containsValue(Person person){
        return people.containsValue(person);
    }
    public Set<String> keySet(){
        Set<String> setOfPeople = new HashSet<>();
        for (String key : people.keySet()){
            setOfPeople.add(key);
        }
        return setOfPeople;
    }
    public Person remove(String key){
        return people.remove(key);
    }
    public Person get(String key){
        return people.get(key);
    }
    public int size(){
        return people.size();
    }
    public void addAll(PersonMap map){
        people.putAll(map.people);
    }
    /*
    This method will be really useful for the algorithm. We simply subset off of who's available as a key)
     */
    public NavigableSet<String> returnSubsetOfKeys(Set<String> keys, ComparePerson comp) {
        NavigableSet<String> navigableSet = people.navigableKeySet();
        NavigableSet<String> iterationSet = new TreeSet<>(comp);
        iterationSet.addAll(navigableSet);
        iterationSet.retainAll(keys);
        return iterationSet;
    }
}
