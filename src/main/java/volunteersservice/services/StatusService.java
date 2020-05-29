package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.*;
import volunteersservice.models.enums.*;

@Service
public interface StatusService {
	public EventStatus getStatusByEnum(EventStatusEnum statusEnum);

	public CategoryStatus getStatusByEnum(CategoryStatusEnum statusEnum);

	public LevelStatus getStatusByEnum(LevelStatusEnum statusEnum);

	public PublicityStatus getStatusByEnum(PublicityStatusEnum statusEnum);

	public UserRole getRoleByEnum(UserRoleEnum roleEnum);
}
