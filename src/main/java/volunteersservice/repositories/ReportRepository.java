package volunteersservice.repositories;

import volunteersservice.models.entities.*;

import java.util.List;

public interface ReportRepository {
    public FirstPartOfReport getFirstPartByID(int reportID);

    public FirstPartOfReport getFirstPartByEvent(Event event);

    public SecondPartOfReport getSecondPartByID(int reportID);

    public SecondPartOfReport getSecondPartByEvent(Event event);

    public boolean save(FirstPartOfReport report);

    public boolean update(FirstPartOfReport report);

    public boolean save(SecondPartOfReport report);

    public boolean update(SecondPartOfReport report);

    public boolean save(Volunteers volunteer);

    public List<Volunteers> getVolunteersByReport(SecondPartOfReport report);
}
