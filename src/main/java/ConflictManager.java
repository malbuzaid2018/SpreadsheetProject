import java.util.*;

/* By convention, the letter in the conflict marker is meant to specify that a certain time will conflict with another certain time that has the same letter IF the integer value is also the same. This is
to allow us to remain modular, enforce a format to follow for the strings, and most importantly to ensure that if we mark some times like 9:30 as conflicting with 10:30 one day we do not see a conflict if someone wants to add 10:30 another day.
* A ~0 value for the string is an error value and the hashCode is 3954 which is why we check for it*/
public class ConflictManager {
    private boolean isConflict;
    private HashSet<ConflictMarker> listOfPotentialConflicts = new HashSet();
    private static HashSet<ConflictMarker> allPotentialConflicts = new HashSet<>(); // this can be used later on to see if there is already a specified type of conflict. We would have to update this if we know we know we remove theoretically remove it from the timeSlot itself since we do not really need to worry about people having conflicts with each other.

    public ConflictManager() {
        isConflict = false;
    }

    public void checkForConflicts(ConflictManager otherConflictManager) {
        if (this.listOfPotentialConflicts.isEmpty() || otherConflictManager.listOfPotentialConflicts.isEmpty()) {
            isConflict = false;
        } else {
            Iterator<ConflictMarker> itr = listOfPotentialConflicts.iterator();
            while (itr.hasNext()) {
                if (otherConflictManager.listOfPotentialConflicts.contains(itr.next())) {
                    isConflict = true;
                }
            }
        }
    }
    public void addConflictMarkerToInstance(Character character, int integer) {
        ConflictMarker conflictMarker = new ConflictMarker(character, integer);
        if (conflictMarker.hashCode()==3954){
            return;
        }
        listOfPotentialConflicts.add(conflictMarker);
        addToAllPotentialConflicts(conflictMarker);
    }

    public void removeConflictMarkerFromInstance(ConflictMarker conflictMarker) {
        listOfPotentialConflicts.remove(conflictMarker);
    }

    private void addToAllPotentialConflicts(ConflictMarker conflictMarker) {
        if (conflictMarker.hashCode()==3954){
            return;
        }
        allPotentialConflicts.add(conflictMarker);
    }

    //This is only done to help enforce that unwanted strings do not end up in the hashset and that the code means something.
    private class ConflictMarker{
        private int integerValue; //allows us to use the same character for different days
        private Character character; // specifies that a TIME is possibly conflicting with ANOTHER time
        private String thingToHash = character + Integer.toString(integerValue);
        public ConflictMarker(){
            integerValue = 0;
            character = '~';
        }
        public ConflictMarker(Character character, int integer){
            integerValue = integer;
            this.character = character;
        }
        private boolean equals(ConflictMarker conflictMarker){
            return this.thingToHash.equals(conflictMarker.thingToHash);
        }
        public int hashCode(){
            return thingToHash.hashCode();
        }
    }

}
