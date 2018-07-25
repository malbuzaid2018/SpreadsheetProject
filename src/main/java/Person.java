public class Person {
    private int capacity;
    private int numberInitiallyAvailable;
    private int numberScheduled;
    private int timeSlotsLeft;


    public boolean atCapacity(){
        return numberScheduled == capacity;
    }
    public int getNumberScheduled(){
        return numberScheduled;
    }
    public int timeSlotsLeft(){
        return timeSlotsLeft;
    }
    public int getNumberInitiallyAvailable(){
        return numberInitiallyAvailable;
    }
}
