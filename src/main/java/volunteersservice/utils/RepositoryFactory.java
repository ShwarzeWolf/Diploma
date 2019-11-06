package volunteersservice.utils;

import volunteersservice.repositories.*;
import volunteersservice.repositories.hibernate.*;

public interface RepositoryFactory {

    public static UserRepository getUserRepository() {
        return new UserRepositoryHibernate();
    }

    public static UserRoleRepository getUserRoleRepository() {
        return new UserRoleRepositoryHibernate();
    }

    public static EventRepository getEventRepository() {
        return new EventRepositoryHibernate();
    }

    public static EventStatusRepository getEventStatusRepository() {
        return new EventStatusRepositoryHibernate();
    }

    public static VolunteerFunctionStatusRepository getVolunteerFunctionStatusRepository() {
        return new VolunteerFunctionStatusRepositoryHibernate();
    }

    public static VolunteerFunctionRepository getVolunteerFunctionRepository() {
        return new VolunteerFunctionRepositoryHibernate();
    }
}