package service.businesslogic;

import service.dal.dao.DAOFabric;
import service.dal.dao.EventStatusDAO;
import service.dal.models.EventStatus;

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
