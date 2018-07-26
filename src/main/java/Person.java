import java.util.*;
public class Person {
    private int capacity;
    private int numberInitiallyAvailable = 1;
    private int numberScheduled;
    private String name;
    private final ArrayList<String> timesFree = new ArrayList();
    private final ArrayList<String> timesWorking = new ArrayList();


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
}
