package volunteersservice.models;

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
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    int roleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventID", nullable = false)
    Event event;

    @Column(name = "Name", nullable = false)
    @NotNull
    @NotEmpty(message = "Role name cannot be empty")
    String name;

    @Column(name = "Description", nullable = false)
    @NotNull
    @NotEmpty(message = "Role description cannot be empty")
    String description;

    @Column(name = "Requirements", nullable = false)
    @NotNull
    @NotEmpty(message = "Role requirements cannot be empty")
    String requirements;

    @Column(name = "TimeStart", nullable = false)
    @NotNull
    Timestamp timeStart;

    @Column(name = "TimeFinish", nullable = false)
    @NotNull
    Timestamp timeFinish;

    @Column(name = "NumberNeeded", nullable = false)
    @NotNull
    int numberNeeded;

    public Role() {
    }

    public Role(Role other) {
        if (this == other)
            return;
        this.roleID = other.roleID;
        this.event = other.event;
        this.name = other.name;
        this.description = other.description;
        this.requirements = other.requirements;
        this.timeStart = other.timeStart;
        this.timeFinish = other.timeFinish;
        this.numberNeeded = other.numberNeeded;
    }

    public Role(Event event, String name, String description, String requirements, Timestamp timeStart,
            Timestamp timeFinish, int numberNeeded) {
        if (event.getDateStart().toLocalDateTime().isAfter(timeStart.toLocalDateTime())
                || event.getDateFinish().toLocalDateTime().isBefore(timeFinish.toLocalDateTime())
                || timeStart.toLocalDateTime().isAfter(timeFinish.toLocalDateTime()))
            throw new IllegalArgumentException("Exception at role creation: role (start-finish) is wrong");
        this.event = event;
        this.name = name;
        this.description = description;
        this.requirements = requirements;
        this.timeStart = timeStart;
        this.timeFinish = timeFinish;
        this.numberNeeded = numberNeeded;
    }

    public Role(String name, String description, String requirements, Timestamp timeStart, Timestamp timeFinish,
            int numberNeeded) {
        this(null, name, description, requirements, timeStart, timeFinish, numberNeeded);
    }

    public Role(String name, String description, String requirements, LocalDateTime timeStart, LocalDateTime timeFinish,
            int numberNeeded) {
        this(null, name, description, requirements, Timestamp.valueOf(timeStart), Timestamp.valueOf(timeFinish),
                numberNeeded);
    }

    public int getRoleID() {
        return roleID;
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
        return String.format("(Role) %d: [%s]; %s; %s; %s; %s - %s", roleID, event.getEventID(), name, description,
                requirements, timeStart, timeFinish);
    }
}
