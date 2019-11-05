package volunteersservice.businesslogic.services;

import org.springframework.stereotype.Service;

import volunteersservice.enums.RoleStatusEnum;
import volunteersservice.models.RoleStatus;

@Service
public interface RoleStatusManager {
    public RoleStatus getTypeByEnum(RoleStatusEnum typeEnum);
}
