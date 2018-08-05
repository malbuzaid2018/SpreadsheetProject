import java.util.*;
public class ComparePerson implements Comparator<Person> {

    public int compare(final Person personOne, final Person personTwo) {
        double loadOne = personOne.getLoad();
        double loadTwo = personTwo.getLoad();
        if (personOne == personTwo){
            return 0;
        }
        if (personOne.getName().equalsIgnoreCase(personTwo.getName())){
            return 0;
        }
        if (personOne.getLoad() >= 1 && personTwo.getLoad() <1){
            return 1;
        }
        if (personTwo.getLoad() >= 1 && personOne.getLoad() <1){
            return -1;
        }
        if (personOne.getLoad() >= 1 && personTwo.getLoad() >= 1){
            return 0;
        }
        double personOneCalc = loadOne * (personOne.getNumberInitiallyAvailable() + 1.0);
        double personTwoCalc = loadTwo * (personTwo.getNumberInitiallyAvailable() + 1.0);
        double firstTest = personOneCalc - personTwoCalc;
        if ((firstTest != 0)) {
            if (personOneCalc < personTwoCalc){
                return -1;
            }
            else{
                return 1;
            }
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