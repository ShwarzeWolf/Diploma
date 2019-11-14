package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.models.entities.UserVolunteerFunctionStatus;

@Service
public interface UserVolunteerFunctionStatusService {
    public UserVolunteerFunctionStatus getStatusByEnum(UserVolunteerFunctionStatusEnum statusEnum);
}
