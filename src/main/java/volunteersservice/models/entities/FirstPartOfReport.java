package volunteersservice.models.entities;

import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.EventStatusRepository;
import volunteersservice.utils.RepositoryFactory;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "VolunteersService.FirstPartReports")
public class FirstPartOfReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReportID")
    private int reportID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventID", nullable = false)
    private Event event;

    @Column(name = "ShortName", nullable = false)
    @NotNull
    @NotEmpty(message = "Short name of Event cannot be empty")
    private String shortName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID", nullable = false)
    private CategoryStatus categoryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PublicityID", nullable = false)
    private PublicityStatus publicityStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LevelID", nullable = false)
    private LevelStatus level;

    @Column(name = "ShortDescription")
    private String shortDescription;

    @Column(name = "Participants")
    private String participants;

    public FirstPartOfReport(){};

    public FirstPartOfReport(Event event,
                             String shortName,
                             CategoryStatus categoryStatus,
                             PublicityStatus publicityStatus,
                             LevelStatus level,
                             String shortDescription,
                             String participants) {
        this.event = event;
        this.shortName = shortName;
        this.categoryStatus = categoryStatus;
        this.publicityStatus = publicityStatus;
        this.level = level;
        this.shortDescription = shortDescription;
        this.participants = participants;
    }


    public int getReportID() {
        return reportID;
    }

    public Event getEvent() {
        return event;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public CategoryStatus getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(CategoryStatus categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public PublicityStatus getPublicityStatus() {
        return publicityStatus;
    }

    public void setPublicityStatus(PublicityStatus publicityStatus) {
        this.publicityStatus = publicityStatus;
    }

    public LevelStatus getLevel() {
        return level;
    }

    public void setLevel(LevelStatus level) {
        this.level = level;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }
}