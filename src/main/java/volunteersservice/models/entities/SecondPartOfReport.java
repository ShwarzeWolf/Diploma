package volunteersservice.models.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "VolunteersService.SecondPartReports")
public class SecondPartOfReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID")
    private int reportID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventID", nullable = false)
    private Event event;

    @Column(name = "ResultsLinks", nullable = false)
    @NotNull
    private String resultsLinks;

    @Column(name = "NumberOfPeople")
    private int numberOfPeople;

    public SecondPartOfReport(){}

    public SecondPartOfReport(Event event, String resultsLinks, int numberOfPeople) {
        this.event = event;
        this.resultsLinks = resultsLinks;
        this.numberOfPeople = numberOfPeople;
    }

    public int getReportID() {
        return reportID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getResultsLinks() {
        return resultsLinks;
    }

    public void setResultsLinks(String resultsLinks) {
        this.resultsLinks = resultsLinks;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

}