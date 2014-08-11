package agenda.exceptions;

/**
 *
 * @author Fuad Saud
 */
@SuppressWarnings("serial")
public class NomeComCaracteresInvalidosException extends Exception {

    public NomeComCaracteresInvalidosException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
