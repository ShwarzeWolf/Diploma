package volunteersservice.utils;

import volunteersservice.repositories.*;
import volunteersservice.repositories.hibernate.*;

public class RepositoryFactory {

    private final static UserRepository USER_REPOSITORY = new UserRepositoryHibernate();
    private final static UserRoleRepository USER_ROLE_REPOSITORY = new UserRoleRepositoryHibernate();
    private final static EventRepository EVENT_REPOSITORY = new EventRepositoryHibernate();
    private final static StatusRepository STATUS_REPOSITORY = new StatusRepositoryHibernate();
    private final static VolunteerFunctionRepository VOLUNTEER_FUNCTION_REPOSITORY = new VolunteerFunctionRepositoryHibernate();

    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }

    public static UserRoleRepository getUserRoleRepository() {
        return USER_ROLE_REPOSITORY;
    }

    public static EventRepository getEventRepository() {
        return EVENT_REPOSITORY;
    }

    public static StatusRepository getStatusRepository() {
        return STATUS_REPOSITORY;
    }

    public static VolunteerFunctionRepository getVolunteerFunctionRepository() {
        return VOLUNTEER_FUNCTION_REPOSITORY;
    }
}