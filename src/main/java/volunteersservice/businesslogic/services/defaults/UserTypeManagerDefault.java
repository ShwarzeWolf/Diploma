package volunteersservice.businesslogic.services.defaults;

import org.springframework.stereotype.Service;

import volunteersservice.businesslogic.services.UserTypeManager;
import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.UserTypeDAO;
import volunteersservice.models.UserType;

@Service
public class UserTypeManagerDefault implements UserTypeManager {
    private final UserTypeDAO userTypeDAO;

    public UserTypeManagerDefault() {
        this.userTypeDAO = DAOFabric.getUserTypeDAO();
    }

    @Override
    public UserType getTypeByEnum(USER_TYPE typeEnum) {
        return userTypeDAO.getTypeByName(typeEnum.name().toLowerCase());
    }
}
