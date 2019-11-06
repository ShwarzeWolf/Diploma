package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.RoleStatus;
import volunteersservice.models.enums.RoleStatusEnum;
import volunteersservice.repositories.RoleStatusDAO;
import volunteersservice.services.RoleStatusManager;
import volunteersservice.utils.DAOFabric;

@Service
public class RoleStatusManagerDefault implements RoleStatusManager{
    private final RoleStatusDAO RoleStatusDAO;

    public RoleStatusManagerDefault() {
        this.RoleStatusDAO = DAOFabric.getRoleStatusDAO();
    }

    @Override
    public RoleStatus getTypeByEnum(RoleStatusEnum typeEnum) {
        return RoleStatusDAO.getStatusByName(typeEnum.name().toLowerCase());
    }
}
