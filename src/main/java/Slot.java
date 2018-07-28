import java.util.*;

public class Slot extends Conflictable {
    private final ArrayList<Person> peopleAvailable = new ArrayList<>();
    private final ArrayList<Person> peopleWorking = new ArrayList<>();
    int numberOfPeopleWorking = 0;
    int minimumRequired;
    private int max; // We can use this with a priorityQueue so that we can continue to fill the slot to capacity.
    private String date;
    private String time;

    public Slot() {
        minimumRequired = 5;
    }

    public Slot(int min) {
        this.minimumRequired = min;
    }
    public Slot(String date){
        this.minimumRequired = 5;
        this.date = date;
    }
    public String getDate(){
       return (date);
    }
    public ArrayList<Person> getPeopleAvailable() {
        ArrayList<Person> people = new ArrayList<>(peopleAvailable.size());
        for (int i = 0; i < peopleAvailable.size(); i++) {
            people.add(peopleAvailable.get(i));
        }
        return people;
    }

    public ArrayList<String> getPeopleAvailableNamems() {
        ArrayList<String> people = new ArrayList<>(peopleAvailable.size());
        for (int i = 0; i < peopleAvailable.size(); i++) {
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

    public void addPersontoPeopleFilling(Person person) {
        if (this.checkForConflicts(person) == true) {
            System.out.println("Warning there is a conflict! Please address this before adding!");
        } else {
            peopleWorking.add(person);
        }
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
            if (personItr == person) {
                found = true;
            }
        }
        return found;
    }

    public boolean checkForConflicts(Conflictable otherConflictable) {
        return super.checkForPotentialConflicts(otherConflictable);
    }
    /* The conflict marker should not have a character value of ~ while the int value is also 0./*

     */

    public boolean addConflictMarker(Character character, int i) {
        boolean added = false;
        ConflictMarker conflictMarkerToAdd = new ConflictMarker(character, i);
        System.out.println("Attempting to add a ConflictMarker object " + conflictMarkerToAdd.hashCode() + " to slot " + this.hashCode());
        added = super.addConflictMarkerToInstance(conflictMarkerToAdd);
        if (added = true) {
            System.out.println("Successfully added marker to slot" + this.hashCode());
        } else {
            System.out.println("Marker already present in slot " + hashCode());
        }
        return added;
    }
    public boolean removeAllConflictMarkerRelatedToSlotFromAll(Character character, int i) {
        ConflictMarker conflictMarkerToRemove = new ConflictMarker(character, i);
        System.out.println("Attempting to remove conflict marker " + conflictMarkerToRemove.hashCode() + " from slot " + hashCode() + " and master set");
        for (Person person : peopleAvailable) {
            // if (person.containsMarker) // TODO
            person.removeConflictMarker(character, i);
        }
        for (Person person : peopleWorking) {
            // if (person.containsMarker) // TODO
            person.removeConflictMarker(character, i);
        }
        return super.removeConflictMarkerFromInstance(conflictMarkerToRemove);
    }

    public void sortPeopleWorking(){
        Collections.sort(peopleWorking, new ComparePerson());
    }
}