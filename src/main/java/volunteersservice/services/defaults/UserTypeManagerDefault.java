package volunteersservice.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.services.UserTypeManager;
import volunteersservice.models.entities.UserType;
import volunteersservice.models.enums.UserTypeEnum;
import volunteersservice.repositories.UserTypeDAO;
import volunteersservice.utils.DAOFabric;

@Service
public class UserTypeManagerDefault implements UserTypeManager {
    private final UserTypeDAO userTypeDAO;

    public UserTypeManagerDefault() {
        this.userTypeDAO = DAOFabric.getUserTypeDAO();
    }

    @Override
    public UserType getTypeByEnum(UserTypeEnum typeEnum) {
        return userTypeDAO.getTypeByName(typeEnum.name().toLowerCase());
    }
}
