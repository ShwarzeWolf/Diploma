package volunteersservice.models.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

import volunteersservice.utils.exceptions.VolunteerFunctionCreationException;

@Entity
@Table(name = "VolunteersService.VolunteerFunctions")
public class VolunteerFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VolunteerFunctionID")
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

    @Column(name = "Requirements", nullable = true)
    @NotNull
    private String requirements;

    @Column(name = "TimeStart", nullable = false)
    @NotNull
    private LocalDateTime timeStart;

    @Column(name = "TimeFinish", nullable = false)
    @NotNull
    private LocalDateTime timeFinish;

    @Column(name = "NumberNeeded", nullable = false)
    @NotNull
    private int numberNeeded;

    public VolunteerFunction() {
    }

    private void init(Event event, String name, String description, String requirements, LocalDateTime timeStart,
            LocalDateTime timeFinish, int numberNeeded) {
        if (event != null) {
            if (timeStart.isAfter(timeFinish))
                throw new VolunteerFunctionCreationException("VolunteerFunction timeStart > timeFinish");
            if (event.getDateStart().isAfter(timeStart.plusDays(2)) || event.getDateFinish().isBefore(timeFinish.minusDays(2)))
                throw new VolunteerFunctionCreationException(
                        "VolunteerFunction (timeStart, timeFinish) is not inside event(dateStart - 2 days, dateFinish + 2 days)");
        }
        this.event = event;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.numberNeeded = numberNeeded;
    }

    public VolunteerFunction(VolunteerFunction other) {
        init(other.event, other.name, other.description, other.requirements, other.timeStart, other.timeFinish,
                other.numberNeeded);
        this.volunteerFunctionID = other.volunteerFunctionID;
    }

    public VolunteerFunction(Event event, String name, String description, String requirements, LocalDateTime timeStart,
            LocalDateTime timeFinish, int numberNeeded) {
        init(event, name, description, requirements, timeStart, timeFinish, numberNeeded);
    }

    public VolunteerFunction(String name, String description, String requirements, LocalDateTime timeStart,
            LocalDateTime timeFinish, int numberNeeded) {
        this(null, name, description, requirements, timeStart, timeFinish, numberNeeded);
    }

    public VolunteerFunction(String name, String description, String requirements, String timeStart, String timeFinish,
            int numberNeeded) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime start, finish;
        try {
            start = LocalDateTime.parse(timeStart, formatter);
            finish = LocalDateTime.parse(timeFinish, formatter);
        } catch (DateTimeParseException ex) {
            throw new VolunteerFunctionCreationException(ex);
        }
        init(null, name, description, requirements, start, finish, numberNeeded);
    }

    public int getVolunteerFunctionID() {
        return volunteerFunctionID;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = LocalDateTime.parse(timeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public LocalDateTime getTimeFinish() {
        return timeFinish;
    }

    public void setTimeFinish(LocalDateTime timeFinish) {
        this.timeFinish = timeFinish;
    }

    public void setTimeFinish(String timeFinish) {
        this.timeFinish = LocalDateTime.parse(timeFinish, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public String getPrettyTime(){
        String start = timeStart.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"));
        String finish = timeFinish.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"));
        return start.concat("—").concat(finish);
    }

    public int getNumberNeeded() {
        return numberNeeded;
    }

    public void setNumberNeeded(int numberNeeded) {
        this.numberNeeded = numberNeeded;
    }

    @Override
    public String toString() {
        return String.format("(VolunteerFunction %d): eventId [%s]; %s; %s; %s; %s - %s; %d needed",
                volunteerFunctionID, event.getEventID(), name, description.replace("\r", "\\r").replace("\n", "\\n"),
                requirements, timeStart, timeFinish, numberNeeded);
    }
}
