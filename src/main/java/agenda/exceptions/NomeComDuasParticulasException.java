package agenda.exceptions;

/**
 * 
 * @author Fuad Saud
 */
@SuppressWarnings("serial")
public class NomeComDuasParticulasException extends Exception {

	public NomeComDuasParticulasException(String message) {
		super(message);
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
