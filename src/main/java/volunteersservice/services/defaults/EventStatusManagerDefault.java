package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.EventStatusDAO;
import volunteersservice.services.EventStatusManager;
import volunteersservice.utils.DAOFabric;;

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
