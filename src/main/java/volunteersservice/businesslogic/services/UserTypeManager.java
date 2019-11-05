package volunteersservice.businesslogic.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.UserType;

@Service
public interface UserTypeManager {

	public static enum USER_TYPE {
		ORGANISER, MANAGER, COORDINATOR, VOLUNTEER
	}

	public UserType getTypeByEnum(USER_TYPE typeEnum);
}
