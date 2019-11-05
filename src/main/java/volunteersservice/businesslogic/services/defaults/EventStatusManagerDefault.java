package volunteersservice.businesslogic.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.businesslogic.services.EventStatusManager;
import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.EventStatusDAO;
import volunteersservice.models.EventStatus;
import volunteersservice.enums.EventStatusEnum;;

@Service
public class EventStatusManagerDefault implements EventStatusManager {
    private final EventStatusDAO eventStatusDAO;

    public EventStatusManagerDefault() {
        this.eventStatusDAO = DAOFabric.getEventStatusDAO();
    }

    @Override
    public EventStatus getTypeByEnum(EventStatusEnum typeEnum) {
        return eventStatusDAO.getStatusByName(typeEnum.name().toLowerCase());
    }
}
