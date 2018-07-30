import java.util.*;

public class Rule {
    private int minToSet;
    private int maxToSet;
    private ArrayList<String> dayTimesToRemove = new ArrayList<>(); //TODO make this implementable in DataInterface to remove certain days of the week.
    private ArrayList<String> dayTimeMinMaxSetList = new ArrayList<>();
    private ArrayList<String> justTimeMinMaxSetList = new ArrayList<>();

    public Rule(int min, int max){
        minToSet = min;
        maxToSet = max;
    }
    public void applyTimeDayRuleToSlot(Slot slot, String day, String time){
        if (slot.getDate().contains(day)){
            if (slot.getTime().equals(time)){
                slot.setMinimumRequired(minToSet);
                slot.setMax(maxToSet);
            }
        }
    }

    private class dayTime{
        private String day;
        private String time;

        private dayTime(){
            day = "";
            time = "";
        }
        private dayTime(String day, String time){
            this.day = day;
            this.time = time;
        }
        private String getDay(){
            return day;
        }
        private String getTime(){
            return time;
        }
    }
}

