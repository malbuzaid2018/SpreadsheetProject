import java.util.*;

public class Slot {
    private final ArrayList<String> peopleAvailable = new ArrayList<>();
    private final ArrayList<String> peopleWorking = new ArrayList<>();
    int numberOfPeopleWorking = 0;
    final int minimumRequired;
    private String date;

    public Slot(int min){
        this.minimumRequired = min;
    }

    public ArrayList<String> getPeopleAvailable() {
        ArrayList<String> people = new ArrayList<>(peopleAvailable.size());
        for (int i = 0; i < peopleAvailable.size(); i++) {
            people.add(peopleAvailable.get(i));
        }
        return people;
    }

    public ArrayList<String> getPeopleWorking(){
        ArrayList<String> people = new ArrayList<>(peopleAvailable.size());
        for (String str : peopleAvailable){
            people.add(str);
        }
        return people;
    }
    public boolean isFilledToMin(){
        return numberOfPeopleWorking >= minimumRequired;
    }
    public int getNumberOfPeople(){
        return numberOfPeopleWorking;
    }

    public void addPersonToPeopleAvailable(String person){
        ListIterator<String> itr = peopleAvailable.listIterator();
        while (itr.hasNext()){
            if (person.compareTo(itr.next()) > 0){
                itr.previous();
                itr.add(person);
            }
        }
    }
    public void addPersontoPeopleFilling(String person){

    }
    public void removePersonFromPeopleAvailable(String person){

    }
    public void removePersonFromPeopleFilling(String person){

    }
    public void sortPeopleAvailable(){
        Collections.sort(peopleAvailable);
    }
    public boolean isEmpty(){
        return numberOfPeopleWorking == 0;
    }



}
