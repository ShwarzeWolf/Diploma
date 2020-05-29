package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.*;
import volunteersservice.models.enums.*;
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
        return statusRepository.getStatusByEnum(statusEnum);
    }

    @Override
    public LevelStatus getStatusByEnum(LevelStatusEnum statusEnum) {
        return statusRepository.getStatusByEnum(statusEnum);
    }

    @Override
    public PublicityStatus getStatusByEnum(PublicityStatusEnum statusEnum) {
        return statusRepository.getStatusByEnum(statusEnum);
    }

    @Override
    public UserRole getRoleByEnum(UserRoleEnum typeEnum) {
        return statusRepository.getRoleByEnum(typeEnum);
    }

}
