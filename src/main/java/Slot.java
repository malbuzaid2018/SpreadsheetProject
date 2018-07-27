import java.util.*;

public class Slot {
    private final ArrayList<Person> peopleAvailable = new ArrayList<>();
    private final ArrayList<Person> peopleWorking = new ArrayList<>();
    private final ConflictManager managerOfConflict = new ConflictManager();
    int numberOfPeopleWorking = 0;
    final int minimumRequired;
    private int max; // We can use this in a later implementation with more features
    private String date;

    public Slot(int min) {
        this.minimumRequired = min;
    }

    public ArrayList<Person> getPeopleAvailable() {
        ArrayList<Person> people = new ArrayList<>(peopleAvailable.size());
        for (int i = 0; i < peopleAvailable.size(); i++) {
            people.add(peopleAvailable.get(i));
        }
        return people;
    }
    public ArrayList<String> getPeopleAvailableNamems(){
        ArrayList<String> people = new ArrayList<>(peopleAvailable.size());
        for (int i = 0; i<peopleAvailable.size(); i++){
            people.add(peopleAvailable.get(i).getName());
        }
        return people;
    }

    public ArrayList<Person> getPeopleWorkingNames() {
        ArrayList<Person> people = new ArrayList<>(peopleAvailable.size());
        for (Person person : peopleAvailable) {
            people.add(person);
        }
        return people;
    }

    public boolean isFilledToMin() {
        return numberOfPeopleWorking >= minimumRequired;
    }

    public int getNumberOfPeople() {
        return numberOfPeopleWorking;
    }

    //TODO//  implement this with the proper compareTo or Comparator
    public void addPersonToPeopleAvailable(Person person) {
        peopleAvailable.add(person);
    }

    public void addPersontoPeopleFilling(String person) {

    }

    public void removePersonFromPeopleAvailable(String person) {

    }

    public void removePersonFromPeopleFilling(String person) {

    }

    public Person removeAndGetFirstPersonAvailable() {
        return peopleAvailable.remove(0);
    }

    public void sortPeopleAvailable() {
    }

    public boolean isEmpty() {
        return numberOfPeopleWorking == 0;
    }
    // We are checking for the same exact object. So the == method is good enough. We could use getName but we
    public boolean containsInAvailable(Person person) {
        boolean found = false;
        for (Person personItr : peopleAvailable) {
            if (personItr == person){
                found = true;
            }
        }
        return found;
    }
    public void addConflictMarkerToConflictManager(Character character, int i){
        managerOfConflict.addConflictMarkerToInstance(character, i);
    }
}

