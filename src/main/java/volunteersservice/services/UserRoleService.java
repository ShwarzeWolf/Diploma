package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.UserRole;
import volunteersservice.models.enums.UserRoleEnum;

@Service
public interface UserRoleService {
	public UserRole getRoleByEnum(UserRoleEnum roleEnum);
}
