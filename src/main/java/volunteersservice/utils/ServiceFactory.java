package volunteersservice.utils;

import volunteersservice.services.*;
import volunteersservice.services.defaults.*;

public class ServiceFactory {

	private static final EventService EVENT_SERVICE = new EventServiceDefault();
	private static final EventStatusService EVENT_STATUS_SERVICE = new EventStatusServiceDefault();
	private static final VolunteerFunctionService VOLUNTEER_FUNCTION_SERVICE = new VolunteerFunctionServiceDefault();
	private static final UserService USER_SERVICE = new UserServiceDefault();
	private static final UserRoleService USER_ROLE_SERVICE = new UserRoleServiceDefault();

	public static EventService getEventService() {
		return EVENT_SERVICE;
	}

	public static EventStatusService getEventStatusService() {
		return EVENT_STATUS_SERVICE;
	}

	public static VolunteerFunctionService getVolunteerFunctionService() {
		return VOLUNTEER_FUNCTION_SERVICE;
	}

	public static UserService getUserService() {
		return USER_SERVICE;
	}

	public static UserRoleService getUserRoleService() {
		return USER_ROLE_SERVICE;
	}
}