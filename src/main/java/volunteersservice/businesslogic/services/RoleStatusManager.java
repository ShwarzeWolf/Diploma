package volunteersservice.businesslogic.services;

import org.springframework.stereotype.Service;

import volunteersservice.models.RoleStatus;

@Service
public interface RoleStatusManager {
	
	public static enum ROLE_STATUS {
        UNCHECKED, DENIED, APPROVED, PARTICIPATED, PARTLY, ABSENT
    }

    public RoleStatus getTypeByEnum(ROLE_STATUS typeEnum);
}
