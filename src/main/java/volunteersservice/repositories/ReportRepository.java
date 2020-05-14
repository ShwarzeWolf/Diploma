package volunteersservice.repositories;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.FirstPartOfReport;

import java.util.List;

public interface ReportRepository {
    public FirstPartOfReport getReportByID(int reportID);

    public FirstPartOfReport getReportByEvent(Event event);

    public boolean save(FirstPartOfReport report);

    public boolean update(FirstPartOfReport report);
}
