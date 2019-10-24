package service.businesslogic;

import service.dal.dao.DAOFabric;
import service.dal.dao.UserTypeDAO;
import service.dal.models.UserType;

public class UserTypeManager {
    private final UserTypeDAO userTypeDAO;

    public UserTypeManager() {
        this.userTypeDAO = DAOFabric.getUserTypeDAO();
    }
    
    public static enum USER_TYPE {
        ORGANISER, MANAGER, COORDINATOR, VOLUNTEER
    }

    public UserType getTypeByEnum(USER_TYPE typeEnum) {
        return userTypeDAO.getTypeByName(typeEnum.name().substring(0, 1).toUpperCase() + typeEnum.name().substring(1).toLowerCase());
    }
}
