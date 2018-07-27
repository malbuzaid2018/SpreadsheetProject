import java.util.*;

public class TheTimeMap implements Schedule {
    private final LinkedHashMap<String, Slot> timeSlotMap;
    private int numberOfTimeSlots;

    public TheTimeMap() {
        timeSlotMap = new LinkedHashMap<>();
        numberOfTimeSlots = 0;
    }

    public TheTimeMap(int initialCapacity) {
        timeSlotMap = new LinkedHashMap<>(initialCapacity);
        numberOfTimeSlots = 0;
    }

    public TheTimeMap(int initialCapacity, float loadFactor) {
        timeSlotMap = new LinkedHashMap<>(initialCapacity, loadFactor);
        numberOfTimeSlots = 0;
    }

    public boolean containsTimeSlot(Slot value) {
        return timeSlotMap.containsValue(value);
    }

    //* This is the one that we would use to see if there is a time on the schedule*/
    public boolean containsTime(String key) {
        return timeSlotMap.containsKey(key);
    }

    public Slot getTimeSlot(String key) {
        return timeSlotMap.get(key);
    }

    /*
        public Slot getOrDefault(String key, Slot defaultVal) {
            return timeSlotMap.getOrDefault(key, defaultVal); //** fix this/
        }
    */
    public Slot removeTimeSlot(String key) {
        numberOfTimeSlots--;
        return timeSlotMap.remove(key);
    }

    public boolean updateTimeSlot(String key, Slot value) {
        if (timeSlotMap.containsKey(key)) {
            timeSlotMap.put(key, value);
            return true;
        } else {
            return false;
        }
    }
    public void  clear(){
        timeSlotMap.clear();
    }

    public void putNewTimeSlotOnSchedule(String time, Slot timeSlotObj) {
        if (timeSlotMap.containsKey(time)) {
            System.out.println("Please use the updateTimeSlot method if you wish to update a time slot with a new time slot object. Otherwise please check your parameters as the schedule contains this time");
        }
        timeSlotMap.put(time, timeSlotObj);
        numberOfTimeSlots++;
    }

    public Slot removeTime(String key) {
        return timeSlotMap.remove(key);
    }

    public void clearSchedule() {

        timeSlotMap.clear();
    }
    public Set<String> setOfTimeStrings() {
        return timeSlotMap.keySet();
    }
    public Collection<Slot> collectionOfSlots() {

        return timeSlotMap.values();
    }
    /* This method guarantees the order of the entries in the entrySet will be the same as they order they
    were inserted when we iterate over them.
     */
    public Set<Map.Entry<String, Slot>> entrySet() {
        return timeSlotMap.entrySet();
    }
    // TODO  eliminate DRY and make this method more robust
    public boolean tryToAddPersonToAvailableWithMap(Person person, String timeDate, PersonMapHash mapToReadAndUpdate) {
        if (timeDate == "") {
            return false;
        }
        if (mapToReadAndUpdate.containsKey(person.getName())) {
            person = mapToReadAndUpdate.get(person.getName());
            person.incrementIntiallyAvailable();
            person.addTimeFree((String) timeDate);
            if (this.containsTime(timeDate)) {
                if (this.getTimeSlot(timeDate).containsInAvailable(person)) {
                    return false;
                }
                this.getTimeSlot(timeDate).addPersonToPeopleAvailable(person);
            } else {
                this.putNewTimeSlotOnSchedule(timeDate, new Slot(30));
                if (this.getTimeSlot(timeDate).containsInAvailable(person)){
                    return false;
                }
                else {
                    this.getTimeSlot(timeDate).addPersonToPeopleAvailable(person);
                }
            }
            return true;
        } else {
            if (person.getName().equals("")) {
                if (!this.containsTime(timeDate)) {
                    this.putNewTimeSlotOnSchedule(timeDate, new Slot(30));
                }
            }
            if ((person.getName() != "") && (person.getName() != null)) {
                person.addTimeFree(timeDate);
                person.incrementIntiallyAvailable();
                mapToReadAndUpdate.put(person.getName(), person);
                if (this.containsTime(timeDate)) {
                    if (this.getTimeSlot(timeDate).containsInAvailable(person)) {
                        return false;
                    }
                    this.getTimeSlot(timeDate).addPersonToPeopleAvailable(person);
                } else {
                    this.putNewTimeSlotOnSchedule(timeDate, new Slot(30));
                    this.getTimeSlot(timeDate).addPersonToPeopleAvailable(person);
                }
            }
            return true;
        }
    }
}
