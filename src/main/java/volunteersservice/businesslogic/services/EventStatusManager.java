package volunteersservice.businesslogic.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.EventStatus;

@Service
public interface EventStatusManager {
	
	public static enum EVENT_STATUS {
		UNCHECKED, APPROVED, COORDINATED, PUBLISHED, EXPIRED
	}

	public EventStatus getTypeByEnum(EVENT_STATUS typeEnum);
}
