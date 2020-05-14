package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.CategoryStatus;
import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.entities.LevelStatus;
import volunteersservice.models.entities.PublicityStatus;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;

@Service
public interface StatusService {
	public EventStatus getStatusByEnum(EventStatusEnum statusEnum);

	public CategoryStatus getStatusByEnum(CategoryStatusEnum statusEnum);

	public LevelStatus getStatusByEnum(LevelStatusEnum statusEnum);

	public PublicityStatus getStatusByEnum(PublicityStatusEnum statusEnum);
}
