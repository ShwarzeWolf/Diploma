package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.CategoryStatus;
import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.entities.LevelStatus;
import volunteersservice.models.entities.PublicityStatus;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;
import volunteersservice.repositories.StatusRepository;
import volunteersservice.services.StatusService;
import volunteersservice.utils.RepositoryFactory;;

@Service
public class StatusServiceDefault implements StatusService {
    private final StatusRepository statusRepository;

    public StatusServiceDefault() {
        this.statusRepository = RepositoryFactory.getStatusRepository();
    }

    @Override
    public EventStatus getStatusByEnum(EventStatusEnum statusEnum) {
        return statusRepository.getStatusByEnum(statusEnum);
    }

    @Override
    public CategoryStatus getStatusByEnum(CategoryStatusEnum statusEnum) {
        return null;
    }

    @Override
    public LevelStatus getStatusByEnum(LevelStatusEnum statusEnum) {
        return null;
    }

    @Override
    public PublicityStatus getStatusByEnum(PublicityStatusEnum statusEnum) {
        return null;
    }
}
