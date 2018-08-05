import java.util.*;
/* E specifies the type of objects held in the ArrayList. We will probably use Strings to represent times. But with this we could easily change it to be timeSlots
or any new implementation
 */

public class Person extends Conflictable{
    private static int capacity = 11;
    private int numberInitiallyAvailable = 0;
    private int numberScheduled = 0;
    private String name;
    private final HashSet<String> timesFree = new HashSet<>();
    private final HashSet<String> timesWorking = new HashSet<>();
    private double load = (numberScheduled + 0.0)/capacity;


    public Person(){
        this.name = "";
        numberInitiallyAvailable = 0;
    }
    public Person(String name){
        this.name = name.toLowerCase();
        numberInitiallyAvailable = 0;
    }
    public ArrayList<String> getTimesFree(){
        ArrayList<String> arrayToReturn = new ArrayList<>(timesFree.size());
        arrayToReturn.addAll(timesFree);
        return arrayToReturn;
    }
    public ArrayList<String> getTimesWorking(){
        ArrayList<String> arrayToReturn = new ArrayList<>(timesFree.size());
        arrayToReturn.addAll(timesFree);
        return arrayToReturn;
    }
    public void addTimeFree(String timeDate){
            timesFree.add(timeDate);
            numberInitiallyAvailable++;
        }
    public void removeTimeFree(String timeDate){
        timesFree.remove(timeDate);
    }
    public void addTimeWorking(String timeDate){
        timesWorking.add(timeDate);
        numberScheduled++;
    }
    public boolean atCapacity(){
        return numberScheduled == capacity;
    }
    public int getNumberScheduled() {
        return numberScheduled;
    }

    public int getNumberInitiallyAvailable(){
        return numberInitiallyAvailable;
    }
    public String getName(){
        return name;
    }
    public void setName(String newName){
        this.name = newName.toLowerCase();
    }

    public boolean addConflictMarker(Character character, int i){
        boolean added = false;
        ConflictMarker conflictMarker = new ConflictMarker(character, i);
        System.out.println("Attempting to add a ConflictMarker object " + conflictMarker.hashCode() + " to person " + this.getName());
        added = this.addConflictMarkerToInstance(conflictMarker);
        if (added){
            System.out.println("Marker was successfully added");
        }
        else {
            System.out.println("Marker already present");
        }
        return added;
    }
    @Override
    public boolean removeConflictMarkerFromAllConflictMarkers(ConflictMarker conflictMarker){
        System.out.println("Unsupported operation for person. If you intended to remove this type of conflict from the master set of conflicts please use another class that supports this operation like slot.");
        return false;
    }
    @Override
    public void removeLinkedConflictsFromOtherConflictable(Conflictable conflictable){
        System.out.println("removeLinkedConflictsFromOtherConflictable is not supported by class Person");
    }
    public boolean removeConflictMarker(Character character, int i) {
        ConflictMarker conflictMarkerToRemove = new ConflictMarker(character, i);
        System.out.println("Attempting to remove a conflict marker " + conflictMarkerToRemove.hashCode() + "from person " + this.getName());
        boolean removed = this.removeConflictMarkerFromInstance(conflictMarkerToRemove);
        if (removed){
            System.out.println("Marker was successfully removed from person");
        }
        if (!removed){
            System.out.println("Marker was not found");
        }
        return removed;
    }
    public void decrementNumberAvailable(){
        if (numberInitiallyAvailable <= 0) {
            throw new IllegalStateException("Person cannot have a negative number of times available");
        }
        numberInitiallyAvailable--;
    }
    public void removeTimeWorking(String dateTime){
        timesWorking.remove(dateTime);
        numberScheduled--;
    }
    public static void setCapacity(int i){
        if (i < 1){
            throw new IllegalArgumentException("Person class capacity cannot be less than one.");
        }
        Person.capacity = i;
    }
    public static int getCapacity(){
        return capacity;
    }
    public double getLoad(){
        return load;
    }
    @Override
    public boolean equals(Object obj){
        if (obj == this){
            return true;
        }
        if (! (obj instanceof Person) ){
            return false;
        }
        Person person = (Person) obj;
        return person.getName().equalsIgnoreCase(this.getName());
    }
}
