import java.util.HashSet;
import java.util.Iterator;

public abstract class Conflictable {
    private HashSet<ConflictMarker> setOfPotentialConflicts = new HashSet<>();
    private boolean isConflict = false;
    private static HashSet<ConflictMarker> allConflictMarkers = new HashSet<>();

    public boolean addConflictMarkerToInstance(ConflictMarker conflictMarker) {
        if(setOfPotentialConflicts.contains(conflictMarker)){
            return false;
        }
        setOfPotentialConflicts.add(conflictMarker);
        allConflictMarkers.add(conflictMarker);
        return true;
    }

    public boolean checkForPotentialConflicts(Conflictable conflictable) {
        if (this.setOfPotentialConflicts.isEmpty() || conflictable.setOfPotentialConflicts.isEmpty()) {
            this.isConflict = false;
            conflictable.isConflict = false;
        } else {
            Iterator<ConflictMarker> itr = this.setOfPotentialConflicts.iterator();
            while (itr.hasNext()) {
                if (conflictable.setOfPotentialConflicts.contains(itr.next())) {
                    this.isConflict = true;
                    conflictable.isConflict = true;
                }
            }
        }
        return isConflict;
    }
    public boolean removeConflictMarkerFromInstance(ConflictMarker conflictMarker) {
        return setOfPotentialConflicts.remove(conflictMarker);
    }
    public boolean removeConflictMarkerFromAllConflictMarkers(ConflictMarker conflictMarker){
        return allConflictMarkers.remove(conflictMarker);
    }

}
