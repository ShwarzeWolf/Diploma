package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.RoleStatusDAO;
import volunteersservice.models.RoleStatus;

@Service
public class RoleStatusManager {
    private final RoleStatusDAO RoleStatusDAO;

    public RoleStatusManager() {
        this.RoleStatusDAO = DAOFabric.getRoleStatusDAO();
    }

    public static enum ROLE_STATUS {
        UNCHECKED, DENIED, APPROVED, PARTICIPATED, PARTLY, ABSENT
    }

    public RoleStatus getTypeByEnum(ROLE_STATUS typeEnum) {
        return RoleStatusDAO.getStatusByName(typeEnum.name().toLowerCase());
    }
}
