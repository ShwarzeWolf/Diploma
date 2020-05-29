package volunteersservice.utils;

import volunteersservice.repositories.*;
import volunteersservice.repositories.hibernate.*;

public class RepositoryFactory {

    private final static UserRepository USER_REPOSITORY = new UserRepositoryHibernate();
    private final static EventRepository EVENT_REPOSITORY = new EventRepositoryHibernate();
    private final static StatusRepository STATUS_REPOSITORY = new StatusRepositoryHibernate();
    private final static VolunteerFunctionRepository VOLUNTEER_FUNCTION_REPOSITORY = new VolunteerFunctionRepositoryHibernate();
    private final static ReportRepository REPORT_REPOSITORY = new ReportRepositoryHibernate();

    public static UserRepository getUserRepository() {
        return USER_REPOSITORY;
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

    public static ReportRepository getReportRepository() { return REPORT_REPOSITORY;}
}