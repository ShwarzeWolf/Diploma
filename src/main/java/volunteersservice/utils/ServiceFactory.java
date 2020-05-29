package volunteersservice.utils;

import volunteersservice.services.*;
import volunteersservice.services.defaults.*;

public class ServiceFactory {

	private static final EventService EVENT_SERVICE = new EventServiceDefault();
	private static final StatusService STATUS_SERVICE = new StatusServiceDefault();
	private static final VolunteerFunctionService VOLUNTEER_FUNCTION_SERVICE = new VolunteerFunctionServiceDefault();
	private static final UserService USER_SERVICE = new UserServiceDefault();
	private static final ReportService FIRST_PART_REPORT_SERVICE = new ReportServiceDefault();

	public static EventService getEventService() {
		return EVENT_SERVICE;
	}

	public static StatusService getStatusService() {
		return STATUS_SERVICE;
	}

	public static VolunteerFunctionService getVolunteerFunctionService() {
		return VOLUNTEER_FUNCTION_SERVICE;
	}

	public static UserService getUserService() {
		return USER_SERVICE;
	}

	public static ReportService getFirstPartReportService() {return FIRST_PART_REPORT_SERVICE; }
}