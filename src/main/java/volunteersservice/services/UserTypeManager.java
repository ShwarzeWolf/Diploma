package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.UserType;
import volunteersservice.models.enums.UserTypeEnum;

@Service
public interface UserTypeManager {
	public UserType getTypeByEnum(UserTypeEnum typeEnum);
}
