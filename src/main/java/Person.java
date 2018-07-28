import java.util.*;
/* E specifies the type of objects held in the ArrayList. We will probably use Strings to represent times. But with this we could easily change it to be timeSlots
or any new implementation
 */
public class Person extends Conflictable{
    private int capacity;
    private int numberInitiallyAvailable = 1;
    private int numberScheduled;
    private String name;
    private final ArrayList<String> timesFree = new ArrayList();  // This is where we could use a skip list. We would be adding and removing AND possibly searching this.
    private final ArrayList<String> timesWorking = new ArrayList(); // Skip list?


    public Person(){
        this.name = "";
        capacity = 30;
        numberScheduled = 0;
        numberInitiallyAvailable = 1;
    }
    public Person(String name){
        this.name = name;
        capacity = 30;
        numberScheduled = 0;
        numberInitiallyAvailable = 1;
    }
    public Person(String name, int capacity){
        this.name = name;
        capacity = capacity;
        numberScheduled = 0;
        numberInitiallyAvailable = 1;
    }
    public ArrayList<String> getTimes(){
        ArrayList<String> arrayToReturn = new ArrayList<>(timesFree.size());
        for (String str: timesFree){
            arrayToReturn.add(str);
        }
        return arrayToReturn;
    }
    public void addTimeFree(String time){
            timesFree.add(time);
        }
    public void removeTimeFree(String time){
        timesFree.remove(time); //must reimplement this method so that it is more efficient.
    }
    public boolean atCapacity(){
        return numberScheduled == capacity;
    }
    public int getNumberScheduled() {
        return numberScheduled;
    }
    public int timeSlotsLeft(){
        return capacity - numberScheduled;
    }
    public int getNumberInitiallyAvailable(){
        return numberInitiallyAvailable;
    }
    public String getName(){
        return name;
    }
    public void incrementIntiallyAvailable(){
        numberInitiallyAvailable++;
    }
    public void incrementNumberScheduled(){
        numberScheduled++;
    }
    public void setName(String newName){

        this.name = newName;
    }
    /* We can change this as we need to for more advanced features that is why it is a duplicate for now
      */
    public boolean isEligibleToAddToATime(){
        return this.atCapacity();
    }
    public boolean addConflictMarker(Character character, int i){
        boolean added = false;
        ConflictMarker conflictMarker = new ConflictMarker(character, i);
        System.out.println("Attempting to add a ConflictMarker object " + conflictMarker.hashCode() + " to person " + this.getName());
        added = this.addConflictMarkerToInstance(conflictMarker);
        if (added = true){
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
}
