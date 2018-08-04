import java.util.*;
public class ComparePerson implements Comparator<Person> {

    public int compare(final Person personOne, final Person personTwo) {
        if (personOne == personTwo){
            return 0;
        }
        if (personOne.getName().equalsIgnoreCase(personTwo.getName())){
            return 0;
        }   
        int firstTest= ((personOne.getNumberScheduled()) * (personOne.getNumberInitiallyAvailable() + 1) ) - ((personTwo.getNumberScheduled()) * (personTwo.getNumberInitiallyAvailable() + 1));
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