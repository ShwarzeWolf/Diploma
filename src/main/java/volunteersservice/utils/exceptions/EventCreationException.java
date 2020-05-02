package volunteersservice.utils.exceptions;

public class EventCreationException extends VolunteersServiceException {

	private static final long serialVersionUID = 167318659812666533L;
	
	public EventCreationException(String message) {
		super(message);
	}

	public EventCreationException(Exception ex) {
		super(ex);
		initCause(ex);
	}

	public EventCreationException(String message, Exception ex) {
		super(message, ex);
		initCause(ex);
	}
}