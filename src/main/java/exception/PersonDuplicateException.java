package exception;

/**
 * PersonDuplicateException thrown if time slot contains the person
 * already
 */
public class PersonDuplicateException  extends RuntimeException {

    /**
     * constructor
     * @param message exception message
     */
    public PersonDuplicateException(String message) {
        super(message);
    }
}
