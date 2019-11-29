package volunteersservice.utils;

import volunteersservice.repositories.*;
import volunteersservice.repositories.hibernate.*;

public class RepositoryFactory {

    private final static UserRepository USER_REPOSITORY = new UserRepositoryHibernate();
    private final static UserRoleRepository USER_ROLE_REPOSITORY = new UserRoleRepositoryHibernate();
    private final static EventRepository EVENT_REPOSITORY = new EventRepositoryHibernate();
    private final static EventStatusRepository EVENT_STATUS_REPOSITORY = new EventStatusRepositoryHibernate();
    private final static UserVolunteerFunctionStatusRepository USER_VOLUNTEER_FUNCTION_STATUS_REPOSITORY = new UserVolunteerFunctionStatusRepositoryHibernate();
    private final static VolunteerFunctionRepository VOLUNTEER_FUNCTION_REPOSITORY = new VolunteerFunctionRepositoryHibernate();
    private final static UserVolunteerFunctionRepository USER_VOLUNTEER_FUNCTION_REPOSITORY = new UserVolunteerFunctionRepositoryHibernate();

    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
    }

    public static UserRoleRepository getUserRoleRepository() {
        return USER_ROLE_REPOSITORY;
    }

    public static EventRepository getEventRepository() {
        return EVENT_REPOSITORY;
    }

    public static EventStatusRepository getEventStatusRepository() {
        return EVENT_STATUS_REPOSITORY;
    }

    public static UserVolunteerFunctionStatusRepository getUserVolunteerFunctionStatusRepository() {
        return USER_VOLUNTEER_FUNCTION_STATUS_REPOSITORY;
    }

    public static VolunteerFunctionRepository getVolunteerFunctionRepository() {
        return VOLUNTEER_FUNCTION_REPOSITORY;
    }

    public static UserVolunteerFunctionRepository getUserVolunteerFunctionRepository() {
        return USER_VOLUNTEER_FUNCTION_REPOSITORY;
    }
}