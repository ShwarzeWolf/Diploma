package volunteersservice.dal;

import volunteersservice.dal.repositories.*;
import volunteersservice.dal.repositorieshibernate.*;

public class DAOFabric {
    private static String defaultType = "Hibernate";

    public static UserDAO getUserDAO(String type) {
        if (type.equals("Hibernate"))
            return new UserDAOHibernate();
        else
            throw new IllegalArgumentException(String.format("%s is not a valid type of DAO Fabric", type));
    }

    public static UserDAO getUserDAO() {
        return getUserDAO(defaultType);
    }

    public static UserTypeDAO getUserTypeDAO(String type) {
        if (type.equals("Hibernate"))
            return new UserTypeDAOHibernate();
        else
            throw new IllegalArgumentException(String.format("%s is not a valid type of DAO Fabric", type));
    }

    public static UserTypeDAO getUserTypeDAO() {
        return getUserTypeDAO(defaultType);
    }

    public static EventDAO getEventDAO(String type) {
        if (type.equals("Hibernate"))
            return new EventDAOHibernate();
        else
            throw new IllegalArgumentException(String.format("%s is not a valid type of DAO Fabric", type));
    }

    public static EventDAO getEventDAO() {
        return getEventDAO(defaultType);
    }

    public static EventStatusDAO getEventStatusDAO(String type) {
        if (type.equals("Hibernate"))
            return new EventStatusDAOHibernate();
        else
            throw new IllegalArgumentException(String.format("%s is not a valid type of DAO Fabric", type));
    }

    public static EventStatusDAO getEventStatusDAO() {
        return getEventStatusDAO(defaultType);
    }

    public static RoleStatusDAO getRoleStatusDAO(String type) {
        if (type.equals("Hibernate"))
            return new RoleStatusDAOHibernate();
        else
            throw new IllegalArgumentException(String.format("%s is not a valid type of DAO Fabric", type));
    }

    public static RoleStatusDAO getRoleStatusDAO() {
        return getRoleStatusDAO(defaultType);
    }

    public static RoleDAO getRoleDAO(String type) {
        if (type.equals("Hibernate"))
            return new RoleDAOHibernate();
        else
            throw new IllegalArgumentException(String.format("%s is not a valid type of DAO Fabric", type));
    }

    public static RoleDAO getRoleDAO() {
        return getRoleDAO(defaultType);
    }
}