package volunteersservice.services;

import volunteersservice.models.entities.*;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;

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

    public SecondPartOfReport addSecondPart(Event event,
                                            int numberOfParticipants,
                                            String links,
                                            List<Volunteers> volunteers);

    public SecondPartOfReport getSecondPartOfAReportByEvent(Event event);

    public void updateSecondPartOfAReportReport(SecondPartOfReport report);

}
