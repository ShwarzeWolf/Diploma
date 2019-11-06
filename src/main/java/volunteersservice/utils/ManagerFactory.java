package volunteersservice.utils;

import volunteersservice.services.*;
import volunteersservice.services.defaults.*;

public interface ManagerFactory {
	public static EventManager getEventManager() {
		return new EventManagerDefault();
	}
	public static EventStatusManager getEventStatusManager() {
		return new EventStatusManagerDefault();
	}
	public static RoleManager getRoleManager() {
		return new RoleManagerDefault();
	}
	public static RoleStatusManager getRoleStatusManager() {
		return new RoleStatusManagerDefault();
	}
	public static UserManager getUserManager() {
		return new UserManagerDefault();
	}
	public static UserTypeManager getUserTypeManager() {
		return new UserTypeManagerDefault();
	}
}