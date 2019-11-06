package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.enums.EventStatusEnum;

@Service
public interface EventStatusService {
	public EventStatus getStatusByEnum(EventStatusEnum statusEnum);
}
