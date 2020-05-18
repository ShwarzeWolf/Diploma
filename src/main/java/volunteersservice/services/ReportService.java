package volunteersservice.services;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.FirstPartOfReport;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {

    public FirstPartOfReport addFirstPartOfAReport(Event event,
                                                   String shortName,
                                                   CategoryStatusEnum categoryStatus,
                                                   PublicityStatusEnum publicityStatus,
                                                   LevelStatusEnum levelStatus,
                                                   String shortDescription,
                                                   String participants);

    public FirstPartOfReport getFirstPartOfAReportByEvent(Event event);

    public void updateFirstPartOfAReportReport(FirstPartOfReport report);
}
