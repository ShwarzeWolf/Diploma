package volunteersservice.businesslogic.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.businesslogic.services.RoleStatusManager;
import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.RoleStatusDAO;
import volunteersservice.enums.RoleStatusEnum;
import volunteersservice.models.RoleStatus;

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
