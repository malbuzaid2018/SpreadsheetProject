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

    public boolean modifyTimeSlot(String key, int min, int max) {
        if (timeSlotMap.containsKey(key)) {
            Slot theSlot = timeSlotMap.get(key);
            theSlot.setMinimumRequired(min);
            theSlot.setMax(max);
            return true;
        } else {
            return false;
        }
    }
    public void clear(){
        timeSlotMap.clear();
        numberOfTimeSlots = 0;
    }

    public boolean putNewTimeSlotOnSchedule(String time, Slot timeSlotObj) {
        if (timeSlotMap.containsKey(time)) {
            return false;
        }
        timeSlotMap.put(time, timeSlotObj);
        numberOfTimeSlots++;
        return true;
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

    // At first glance this method seems inefficient but for our algorithm it could really help us out. Certain time slots should be filled first. For example at 12:00 at WTC only two people might list themselves as available and the
    // minimum required slots might be 2. We would want to fill all timeSlots to the minimum if possible. BUT then we would also want to fill above the minimum. So we would want our algorithm to
    // continue to run. Using a priorityQueue when you push an item it goes to the right spot on the list(assuming a good comparator method) So once we serve an item we can actually push it back onto the queue. This is going to be useful once we fill all slots to minimum. We would fill
    // the slot with one person and then move onto the next item in the Queue until we reach a point where we can't(max at all items or no more people available).
    public PriorityQueue<Slot> slotPriorityQueue(Comparator<Slot> slotComparator){
        PriorityQueue<Slot> slotPriorityQueue = new PriorityQueue<>(numberOfTimeSlots, slotComparator);
        slotPriorityQueue.addAll(timeSlotMap.values());
        return slotPriorityQueue;
    }

    public void deleteDayTimes(ArrayList<String> dayTimes){
        Iterator<String> itr = timeSlotMap.keySet().iterator();
        while (itr.hasNext()){
            for (String str : dayTimes){
                if (itr.next().contains(str)) {
                    itr.remove();
                }
            }
        }
    }
    public boolean tryToAddPersonToAvailableWithMap(Person person, int min, int max, String timeDate, String time, String date, PersonMapHash mapToReadAndUpdate) {
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
                this.putNewTimeSlotOnSchedule(timeDate, new Slot(min, max, date, time));
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
                    this.putNewTimeSlotOnSchedule(timeDate, new Slot(min, max, date, time));
                    numberOfTimeSlots++;
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
                    this.putNewTimeSlotOnSchedule(timeDate, new Slot(min, max, date, time));
                    this.getTimeSlot(timeDate).addPersonToPeopleAvailable(person);
                }
            }
            return true;
        }
    }
}
