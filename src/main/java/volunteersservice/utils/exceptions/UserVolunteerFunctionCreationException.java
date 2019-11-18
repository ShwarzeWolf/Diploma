package volunteersservice.utils.exceptions;

public class UserVolunteerFunctionCreationException extends VolunteersServiceException {

	private static final long serialVersionUID = 167318659812666533L;
	
	public UserVolunteerFunctionCreationException(String message) {
		super(message);
	}

	public UserVolunteerFunctionCreationException(Exception ex) {
		super(ex);
	}

	public UserVolunteerFunctionCreationException(String message, Exception ex) {
		super(message, ex);
	}
}