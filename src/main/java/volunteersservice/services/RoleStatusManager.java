package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.enums.RoleStatusEnum;
import volunteersservice.models.entities.RoleStatus;

@Service
public interface RoleStatusManager {
    public RoleStatus getTypeByEnum(RoleStatusEnum typeEnum);
}
