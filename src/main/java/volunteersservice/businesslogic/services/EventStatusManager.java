package volunteersservice.businesslogic.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.EventStatus;
import volunteersservice.enums.EventStatusEnum;

@Service
public interface EventStatusManager {
	public EventStatus getTypeByEnum(EventStatusEnum typeEnum);
}
