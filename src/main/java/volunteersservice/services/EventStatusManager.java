package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.EventStatusDAO;
import volunteersservice.models.EventStatus;

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
