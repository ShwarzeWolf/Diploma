package volunteersservice.models.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "VolunteersService.Roles")
public class VolunteerFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private int volunteerFunctionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventID", nullable = false)
    private Event event;

    @Column(name = "Name", nullable = false)
    @NotNull
    @NotEmpty(message = "VolunteerFunction name cannot be empty")
    private String name;

    @Column(name = "Description", nullable = false)
    @NotNull
    @NotEmpty(message = "VolunteerFunction description cannot be empty")
    private String description;

    @Column(name = "Requirements", nullable = false)
    @NotNull
    @NotEmpty(message = "VolunteerFunction requirements cannot be empty")
    private String requirements;

    @Column(name = "TimeStart", nullable = false)
    @NotNull
    private Timestamp timeStart;

    @Column(name = "TimeFinish", nullable = false)
    @NotNull
    private Timestamp timeFinish;

    @Column(name = "NumberNeeded", nullable = false)
    @NotNull
    private int numberNeeded;

    public VolunteerFunction() {
    }

    public VolunteerFunction(VolunteerFunction other) {
        if (this == other)
            return;
        this.volunteerFunctionID = other.volunteerFunctionID;
        this.event = other.event;
        this.name = other.name;
        this.description = other.description;
        this.requirements = other.requirements;
        this.timeStart = other.timeStart;
        this.timeFinish = other.timeFinish;
        this.numberNeeded = other.numberNeeded;
    }

    public VolunteerFunction(Event event, String name, String description, String requirements, Timestamp timeStart,
            Timestamp timeFinish, int numberNeeded) {
        if (event != null && (event.getDateStart().toLocalDateTime().isAfter(timeStart.toLocalDateTime())
                || event.getDateFinish().toLocalDateTime().isBefore(timeFinish.toLocalDateTime())
                || timeStart.toLocalDateTime().isAfter(timeFinish.toLocalDateTime())))
            throw new IllegalArgumentException("Exception at VolunteerFunction creation: VolunteerFunction (start-finish) is wrong");
        this.event = event;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.numberNeeded = numberNeeded;
    }

    public VolunteerFunction(String name, String description, String requirements, Timestamp timeStart, Timestamp timeFinish,
            int numberNeeded) {
        this(null, name, description, requirements, timeStart, timeFinish, numberNeeded);
    }

    public VolunteerFunction(String name, String description, String requirements, LocalDateTime timeStart, LocalDateTime timeFinish,
            int numberNeeded) {
        this(null, name, description, requirements, Timestamp.valueOf(timeStart), Timestamp.valueOf(timeFinish),
                numberNeeded);
    }

    public int getVolunteerFunctionID() {
        return volunteerFunctionID;
    }

    public Event getEvent() {
        return event;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRequirements() {
        return requirements;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public Timestamp getTimeFinish() {
        return timeFinish;
    }

    public int getNumberNeeded() {
        return numberNeeded;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return String.format("(VolunteerFunction) %d: [%s]; %s; %s; %s; %s - %s; %d needed", volunteerFunctionID, event.getEventID(), name, description,
                requirements, timeStart, timeFinish, numberNeeded);
    }
}
