import java.util.*;

public class Slot extends Conflictable {
    // TODO refactor so that there is a person map here. It would be a static class so it could be accessed via any slot.
    // That way when we add or remove people from times, the times are accurartely reflected in the person hashmap.
    private final ArrayList<Person> peopleAvailable = new ArrayList<>();
    private final ArrayList<Person> peopleWorking = new ArrayList<>();
    private int numberOfPeopleAvailable = 0;
    private int numberOfPeopleWorking = 0;
    private int minimumRequired;
    private int max; // We can use this with a priorityQueue so that we can continue to fill the slot to capacity.
    private int leftToMax = max - numberOfPeopleWorking;
    private String date;
    private String time;

    public Slot()
    {
        max = 6;
        minimumRequired = 5;
        date = "";
        time = "";
    }
    public Slot(int min, int max, String date, String time){
        if (min < 0 || max < 0 ){
            throw new IllegalArgumentException("Min and max both need to be at least zero");
        }
        this.minimumRequired = min;
        this.max = max;
        this.date = date;
        this.time = time;
    }
    public Slot(String date, String time){
        this(5, 6, date, time);
    }
    public boolean peopleLeftToFillSlot(){
        return peopleAvailable.size() != 0;
    }
    public int getLeftToMax(){
        return leftToMax;
    }
    public int getMinimumRequired(){
        return minimumRequired;
    }
    public int getNumberOfPeopleWorking(){
        return numberOfPeopleWorking;
    }

        public int getMax(){
            return max;
        }
    public String getDate(){
       return date;
    }
    public boolean atMax(){
        return numberOfPeopleWorking >= max;
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

    public ArrayList<String> getPeopleWorkingNames() {
        ArrayList<String> people = new ArrayList<>(peopleWorking.size());
        for (Person person : peopleWorking) {
            people.add(person.getName());
        }
        return people;
    }

    public boolean isFilledToMin() {
        return numberOfPeopleWorking >= minimumRequired;
    }

    public int getNumberOfPeople() {
        return numberOfPeopleWorking;
    }

    // We could do this so that the list of people is sorted and we run a binary search to see if the person is already in the list. We could then insert the item alphabetically. However,
    // we are using an ArrayList and adding the item would result in nearly the same time efficiency(since we would now be adding to the middle of the list rather than the end). Adding N items would make this ultimately a O(n^2) growth rate. Plus, there would be extra overhead in calling a method either way.
    public void addPersonToPeopleAvailable(Person person) {
        if (containsInAvailable(person)) {
            System.out.println("Person is already in available!");
            return;
        }
        peopleAvailable.add(person);
        person.addTimeFree(this.time + " " + this.date);
        person.incrementIntiallyAvailable();
        numberOfPeopleAvailable++;
    }

    public void addPersontoPeopleWorking(Person person) {
        if (containsInWorking(person)){
            System.out.println("Person is already working this shit!");
            return;
        }
        if (this.checkForConflicts(person) == true) {
            System.out.println("Warning there is a conflict! Please address this before adding!");
        } else {
            peopleWorking.add(person);
            person.incrementNumberScheduled();
            person.addTimeWorking(this.time + " " + this.date);
            numberOfPeopleWorking++;
        }
    }
    public boolean containsInWorking(Person person){
        boolean found = false;
        for (Person personItr : peopleWorking) {
            if (personItr.equals(person)){
                found = true;
            }
        }
        return found;
    }
    public boolean removePersonFromPeopleAvailable(Person person) {
        Boolean removed = peopleAvailable.remove(person);
        if (removed) {
            person.removeTimeFree(this.time + " " + this.date);
            numberOfPeopleAvailable--;
        }
        return removed;
    }
    public boolean removePersonFromPeopleFilling(Person person) {
        Boolean removed = peopleWorking.remove(person);
        if (removed) {
            numberOfPeopleWorking--;
            person.decrementNumberScheduled();
            person.removeTimeWorking(this.time + " " + this.date);
        }
        return removed;
    }

    public Person removeAndGetFirstPersonAvailable() {
        Person person = peopleAvailable.remove(0);
        if (person != null){
            numberOfPeopleAvailable--;
            person.removeTimeFree(this.time + " " + this.date);
        }
        return person;
    }

    public Person personNoLongerAvailable(Person person){
        Boolean removedFromAvail = this.removePersonFromPeopleAvailable(person);
        if (removedFromAvail){
            person.decrementNumberAvailable();
        }
        this.removePersonFromPeopleFilling(person);
        return person;
    }

    /**
     * Sorts the people available as determined by a Comparator.
     * @param comparator
     */
    public void sortPeopleAvailable(Comparator<Person> comparator) {
        Collections.sort(peopleAvailable, comparator);
    }
    public boolean isEmpty() {
        return numberOfPeopleWorking == 0;
    }

    public boolean containsInAvailable(Person person) {
        boolean found = false;
        for (Person personItr : peopleAvailable) {
            if (personItr.equals(person)){
                found = true;
            }
        }
        return found;
    }

    public boolean checkForConflicts(Conflictable otherConflictable) {
        return super.checkForPotentialConflicts(otherConflictable);
    }

    public boolean addConflictMarker(Character character, int i) {
        boolean added = false;
        ConflictMarker conflictMarkerToAdd = new ConflictMarker(character, i);
        System.out.println("Attempting to add a ConflictMarker object " + conflictMarkerToAdd.hashCode() + " to slot " + this.hashCode());
        added = super.addConflictMarkerToInstance(conflictMarkerToAdd);
        if (added = true) {
            System.out.println("Successfully added marker to slot " + this.hashCode());
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
    public void setMax(int i){
        if (i < 0){
            throw new IllegalArgumentException("Please enter a maximum value equal to or above zero");
        }
        max = i;
    }
    public void setMinimumRequired(int i){
        if (i < 0 ){
            throw new IllegalArgumentException("Please enter a minimum value equal to or above zero");
        }
        minimumRequired = i;
    }
    public String getTime(){
        return time;
    }
}