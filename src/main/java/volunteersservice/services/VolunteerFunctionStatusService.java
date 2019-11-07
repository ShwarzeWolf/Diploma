package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.enums.VolunteerFunctionStatusEnum;
import volunteersservice.models.entities.VolunteerFunctionStatus;

@Service
public interface VolunteerFunctionStatusService {
    public VolunteerFunctionStatus getStatusByEnum(VolunteerFunctionStatusEnum statusEnum);
}
