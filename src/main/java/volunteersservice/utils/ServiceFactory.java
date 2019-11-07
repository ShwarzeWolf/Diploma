package volunteersservice.utils;

import volunteersservice.services.*;
import volunteersservice.services.defaults.*;

public interface ServiceFactory {
	public static EventService getEventService() {
		return new EventServiceDefault();
	}
	public static EventStatusService getEventStatusService() {
		return new EventStatusServiceDefault();
	}
	public static VolunteerFunctionService getVolunteerFunctionService() {
		return new VolunteerFunctionServiceDefault();
	}
	public static VolunteerFunctionStatusService getRoleStatusService() {
		return new VolunteerFunctionStatusServiceDefault();
	}
	public static UserService getUserService() {
		return new UserServiceDefault();
	}
	public static UserRoleService getUserRoleService() {
		return new UserRoleServiceDefault();
	}
}