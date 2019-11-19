package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.UserVolunteerFunctionStatus;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.repositories.UserVolunteerFunctionStatusRepository;
import volunteersservice.services.UserVolunteerFunctionStatusService;
import volunteersservice.utils.RepositoryFactory;

@Service
public class UserVolunteerFunctionStatusServiceDefault implements UserVolunteerFunctionStatusService{
    private final UserVolunteerFunctionStatusRepository userVolunteerFunctionStatusRepository;

    public UserVolunteerFunctionStatusServiceDefault() {
        this.userVolunteerFunctionStatusRepository = RepositoryFactory.getUserVolunteerFunctionStatusRepository();
    }

    @Override
    public UserVolunteerFunctionStatus getStatusByEnum(UserVolunteerFunctionStatusEnum statusEnum) {
        return userVolunteerFunctionStatusRepository.getStatusByEnum(statusEnum);
    }
}
