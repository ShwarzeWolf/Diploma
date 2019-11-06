package volunteersService.services;

import org.springframework.stereotype.Service;

import volunteersService.dal.DAOFabric;
import volunteersService.dal.repositories.UserTypeDAO;
import volunteersService.models.UserType;

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
