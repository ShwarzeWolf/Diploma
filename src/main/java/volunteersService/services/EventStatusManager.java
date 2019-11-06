package volunteersService.services;

import org.springframework.stereotype.Service;

import volunteersService.dal.DAOFabric;
import volunteersService.dal.repositories.EventStatusDAO;
import volunteersService.models.EventStatus;

@Service
public class EventStatusManager {
    private final EventStatusDAO eventStatusDAO;

    public EventStatusManager() {
        this.eventStatusDAO = DAOFabric.getEventStatusDAO();
    }

    public static enum EVENT_STATUS {
        UNCHECKED, APPROVED, COORDINATED, PUBLISHED, EXPIRED
    }

    public EventStatus getTypeByEnum(EVENT_STATUS typeEnum) {
        return eventStatusDAO.getStatusByName(typeEnum.name().toLowerCase());
    }
}
