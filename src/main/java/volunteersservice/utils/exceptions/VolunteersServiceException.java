package volunteersservice.utils.exceptions;

public class VolunteersServiceException extends RuntimeException {

	private static final long serialVersionUID = 1673186598126665533L;
	
	public VolunteersServiceException(String message) {
		super(message);
	}

	public VolunteersServiceException(Exception ex) {
		super(ex);
	}

	public VolunteersServiceException(String message, Exception ex) {
		super(message, ex);
	}
}