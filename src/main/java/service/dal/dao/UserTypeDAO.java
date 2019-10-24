package service.dal.dao;

import service.dal.models.UserType;

public interface UserTypeDAO {
    public UserType getTypeByName(String typeName);
}
