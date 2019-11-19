package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.EventStatusRepository;
import volunteersservice.services.EventStatusService;
import volunteersservice.utils.RepositoryFactory;;

@Service
public class EventStatusServiceDefault implements EventStatusService {
    private final EventStatusRepository eventStatusRepository;

    public EventStatusServiceDefault() {
        this.eventStatusRepository = RepositoryFactory.getEventStatusRepository();
    }

    @Override
    public EventStatus getStatusByEnum(EventStatusEnum statusEnum) {
        return eventStatusRepository.getStatusByEnum(statusEnum);
    }
}
