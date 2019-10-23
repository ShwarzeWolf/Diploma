package service.dal.dao;

public class DAOFabric {
    private static String defaultType = "Hibernate";

    public static UsersDAO getUserDAO(String type) {
        if (type.equals("Hibernate"))
            return new UsersDAOHibernate();
        else
            throw new IllegalArgumentException(String.format("%s is not a valid type of DAO Fabric", type));
    }
    public static UsersDAO getUserDAO() {
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
}