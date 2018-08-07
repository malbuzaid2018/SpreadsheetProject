import java.util.*;
public class ComparePerson implements Comparator<Person> {

    public int compare(final Person personOne, final Person personTwo) {
        return personOne.getNumberScheduled() - personTwo.getNumberScheduled();
    }
}