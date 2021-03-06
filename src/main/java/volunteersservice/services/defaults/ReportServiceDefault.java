package volunteersservice.services.defaults;

import volunteersservice.models.entities.*;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;
import volunteersservice.repositories.ReportRepository;
import volunteersservice.services.ReportService;
import volunteersservice.utils.RepositoryFactory;

import java.util.List;

public class ReportServiceDefault implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceDefault() {
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
    public FirstPartOfReport getFirstPartByEvent(Event event) {
        return reportRepository.getFirstPartByEvent(event);
    }

    @Override
    public void updateFirstPartOfAReportReport(FirstPartOfReport report) {
        reportRepository.update(report);
    }

    @Override
    public SecondPartOfReport addSecondPart(Event event, int numberOfParticipants, String links, List<Volunteers> volunteers) {
        SecondPartOfReport report = new SecondPartOfReport(event, links, numberOfParticipants);
        reportRepository.save(report);

        if (volunteers != null) {
            for (Volunteers currentVolunteer : volunteers) {
                currentVolunteer.setReport(report);
                addVolunteer(currentVolunteer);
            }
        }

        return report;
    }

    @Override
    public SecondPartOfReport getSecondPartOfAReportByEvent(Event event) {
        return reportRepository.getSecondPartByEvent(event);
    }

    @Override
    public void updateSecondPartOfAReportReport(SecondPartOfReport report) {
    }

    public void addVolunteer(Volunteers vol){
        reportRepository.save(vol);
    }

    @Override
    public List<Volunteers> getVolunteersByReport(SecondPartOfReport report) {
        return reportRepository.getVolunteersByReport(report);
    }
}
