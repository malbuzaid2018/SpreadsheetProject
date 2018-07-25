import java.util.*;
public class ComparePerson implements Comparator<Person> {
    public int compare(final Person personOne, final Person personTwo) {
        return -1 * ((personOne.getNumberScheduled()) / (personOne.getNumberInitiallyAvailable())) - ((personTwo.getNumberScheduled()) / (personTwo.getNumberInitiallyAvailable()));
    }
}