package volunteersservice.businesslogic.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.UserType;
import volunteersservice.enums.UserTypeEnum;

@Service
public interface UserTypeManager {
	public UserType getTypeByEnum(UserTypeEnum typeEnum);
}
