package volunteersservice.services;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.FirstPartOfReport;

import java.time.LocalDateTime;
import java.util.List;

public interface FirstPartReportService {

    public FirstPartOfReport addFirstPartOfAReport(Event event);

    public FirstPartOfReport getFirstPartOfAReportByID(int reportID);

    public FirstPartOfReport getFirstPartOfAReportByEvent(Event event);

    public void updateFirstPartOfAReportReport(FirstPartOfReport report);
}
