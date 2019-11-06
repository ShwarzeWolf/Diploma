package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.VolunteerFunctionStatus;
import volunteersservice.models.enums.VolunteerFunctionStatusEnum;
import volunteersservice.repositories.VolunteerFunctionStatusRepository;
import volunteersservice.services.VolunteerFunctionStatusService;
import volunteersservice.utils.RepositoryFactory;

@Service
public class VolunteerFunctionStatusServiceDefault implements VolunteerFunctionStatusService{
    private final VolunteerFunctionStatusRepository VolunteerFunctionStatusRepository;

    public VolunteerFunctionStatusServiceDefault() {
        this.VolunteerFunctionStatusRepository = RepositoryFactory.getVolunteerFunctionStatusRepository();
    }

    @Override
    public VolunteerFunctionStatus getStatusByEnum(VolunteerFunctionStatusEnum statusEnum) {
        return VolunteerFunctionStatusRepository.getStatusByName(statusEnum.name().toLowerCase());
    }
}
