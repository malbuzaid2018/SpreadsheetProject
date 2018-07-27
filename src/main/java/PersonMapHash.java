import java.util.*;
import java.util.Map;

public class PersonMapHash {
    private HashMap<String, Person> people = new HashMap<>();

    public void put(String key, Person value) {
        people.put(key, value);
    }

    public Set<Map.Entry<String, Person>> entrySet() {
        return people.entrySet();
    }

    public void clear() {
        people.clear();
    }

    public boolean containsKey(String personID) {
        return people.containsKey(personID);
    }

    public boolean containsValue(Person person) {
        return people.containsValue(person);
    }

    public Set<String> keySet() {
        Set<String> setOfPeople = new HashSet<>();
        for (String key : people.keySet()) {
            setOfPeople.add(key);
        }
        return setOfPeople;
    }

    public Person remove(String key) {
        return people.remove(key);
    }

    public Person get(String key) {
        return people.get(key);
    }

    public int size() {
        return people.size();
    }

    public boolean updateToNewPersonObj(String person, Person human) {
        if (people.containsKey(person)) {
            people.put(person, human);
            return true;
        }
        return false;
    }
}
