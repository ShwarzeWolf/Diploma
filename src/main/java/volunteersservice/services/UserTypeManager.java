package volunteersservice.services;

import org.springframework.stereotype.Service;

import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.UserTypeDAO;
import volunteersservice.models.UserType;

@Service
public class UserTypeManager {
    private final UserTypeDAO userTypeDAO;

    public UserTypeManager() {
        this.userTypeDAO = DAOFabric.getUserTypeDAO();
    }

    public static enum USER_TYPE {
        ORGANISER, MANAGER, COORDINATOR, VOLUNTEER
    }

    public UserType getTypeByEnum(USER_TYPE typeEnum) {
        return userTypeDAO.getTypeByName(typeEnum.name().toLowerCase());
    }
}
