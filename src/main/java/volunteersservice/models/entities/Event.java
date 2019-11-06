package volunteersservice.models.entities;

import java.sql.Timestamp;

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

import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.EventStatusRepository;
import volunteersservice.utils.RepositoryFactory;

@Entity
@Table(name = "VolunteersService.Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventID")
    private int eventID;

    @Column(name = "Name", nullable = false)
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "Description", nullable = false)
    @NotNull
    @NotEmpty
    private String description;

    @Column(name = "DateStart", nullable = false)
    @NotNull
    private Timestamp dateStart;

    @Column(name = "DateFinish", nullable = false)
    @NotNull
    private Timestamp dateFinish;

    public Event() {
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID", nullable = false)
    private EventStatus status;

    public Event(Event other) {
        if (this == other)
            return;
        this.eventID = other.eventID;
        this.name = other.name;
        this.description = other.description;
        this.dateStart = other.dateStart;
        this.dateFinish = other.dateFinish;
        this.status = other.status;
    }

    public Event(@NotEmpty String name, @NotEmpty String description, @NotNull Timestamp dateStart, @NotNull Timestamp dateFinish) {
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        EventStatusRepository eventStatusRepository = RepositoryFactory.getEventStatusRepository();
        this.status = eventStatusRepository.getStatusByName(EventStatusEnum.UNCHECKED.name().toLowerCase());
    }

    public int getEventID() {
        return this.eventID;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public Timestamp getDateFinish() {
        return dateFinish;
    }

    public void setStatus(EventStatusEnum statusEnum) {
        EventStatusRepository eventStatusRepository = RepositoryFactory.getEventStatusRepository();
        this.status = eventStatusRepository.getStatusByName(statusEnum.name().toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("(Event) %d: %s; %s; %s - %s; %s", eventID, name, description, dateStart, dateFinish, status);
    }
}
