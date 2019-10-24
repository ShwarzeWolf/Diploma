package service.dal.dao;

import service.dal.models.EventStatus;

public interface EventStatusDAO {
    public EventStatus getStatusByName(String typeName);
}
