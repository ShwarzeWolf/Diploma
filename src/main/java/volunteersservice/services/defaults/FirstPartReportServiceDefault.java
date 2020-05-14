package volunteersservice.services.defaults;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.FirstPartOfReport;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;
import volunteersservice.repositories.EventRepository;
import volunteersservice.repositories.ReportRepository;
import volunteersservice.services.FirstPartReportService;
import volunteersservice.utils.RepositoryFactory;

public class FirstPartReportServiceDefault implements FirstPartReportService {
    private final ReportRepository reportRepository;

    public FirstPartReportServiceDefault() {
        reportRepository = RepositoryFactory.getReportRepository();
    }


    @Override
    public FirstPartOfReport addFirstPartOfAReport(Event event,
                                                   String shortName,
                                                   CategoryStatusEnum categoryStatus,
                                                   PublicityStatusEnum publicityStatus,
                                                   LevelStatusEnum levelStatus,
                                                   String shortDescription,
                                                   String participants){
        FirstPartOfReport report = new FirstPartOfReport(event, shortName, categoryStatus, publicityStatus, levelStatus, shortDescription,  participants);
        reportRepository.save(report);

        return report;
    }

    @Override
    public FirstPartOfReport getFirstPartOfAReportByEvent(Event event) {
        return reportRepository.getReportByEvent(event);
    }

    @Override
    public void updateFirstPartOfAReportReport(FirstPartOfReport report) {
        reportRepository.update(report);
    }
}
