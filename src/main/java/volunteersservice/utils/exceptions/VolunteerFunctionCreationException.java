package volunteersservice.utils.exceptions;

public class VolunteerFunctionCreationException extends VolunteersServiceException {

	private static final long serialVersionUID = 167318659816665533L;
	
	public VolunteerFunctionCreationException(String message) {
		super(message);
	}

	public VolunteerFunctionCreationException(Exception ex) {
		super(ex);
	}

	public VolunteerFunctionCreationException(String message, Exception ex) {
		super(message, ex);
	}
}