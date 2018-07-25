import java.util.*;
public class ComparePerson implements Comparator<String> {
    PersonMapHash personMapHash = null;

    public void setHash(PersonMapHash hash){
        personMapHash = hash;
    }
    public int compare(final String nameOne, final String nameTwo) {
        if (personMapHash == null){
            System.out.println("Error! Please initialize the comparator object such that it calls personMap");
        }
        Person personOne = new Person(nameOne);
        Person personTwo = new Person(nameTwo);
        if (personMapHash.containsKey(nameOne)) {
            personOne = personMapHash.get(nameOne);
        }
        if (personMapHash.containsKey(nameTwo)){
            personTwo = personMapHash.get(nameTwo);
        }
        if (personOne.getName().compareToIgnoreCase(personTwo.getName()) == 0){
            return 0;
        }
        int firstTest= ((personOne.getNumberScheduled()) / (personOne.getNumberInitiallyAvailable() + 1) ) - ((personTwo.getNumberScheduled()) / (personTwo.getNumberInitiallyAvailable() + 1));
        if ((firstTest != 0)) {
            return (int) Math.floor(firstTest);
        }
        int secondTest = personOne.getNumberInitiallyAvailable() - personTwo.getNumberInitiallyAvailable();
        if (secondTest != 0) {
            return secondTest;
        }
        else{
            return personOne.getName().compareToIgnoreCase(personTwo.getName());
        }
    }
}