package volunteersservice.models.entities;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrganiserID", referencedColumnName = "UserID", nullable = false)
    @NotNull
    User organiser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CoordinatorID", referencedColumnName = "UserID", nullable = true)
    User coordinator;

    @Column(name = "Name", nullable = false)
    @NotNull
    @NotEmpty(message = "Name of Event cannot be empty")
    private String name;

    @Column(name = "Description", nullable = false)
    @NotNull
    @NotEmpty(message = "Description of Event cannot be empty")
    private String description;

    @Column(name = "Place", nullable = false)
    @NotNull
    @NotEmpty(message = "Place of event cannot be empty")
    private String place;

    @Column(name = "DateStart", nullable = false)
    @NotNull
    private LocalDateTime dateStart;

    @Column(name = "DateFinish", nullable = false)
    @NotNull
    private LocalDateTime dateFinish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID", nullable = false)
    private EventStatus status;

    @Column(name = "Message", nullable = false)
    private String message;

    public Event() {
    }

    public Event(Event other) {
        this.eventID = other.eventID;
        this.name = other.name;
        this.organiser = other.organiser;
        this.coordinator = other.coordinator;
        this.description = other.description;
        this.place = other.place;
        this.dateStart = other.dateStart;
        this.dateFinish = other.dateFinish;
        this.status = other.status;
    }

    public Event(String name, User organiser, String description, String place, LocalDateTime dateStart,
            LocalDateTime dateFinish) {
        this.name = name;
        this.organiser = organiser;
        this.description = description;
        this.place = place;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        EventStatusRepository eventStatusRepository = RepositoryFactory.getEventStatusRepository();
        this.status = eventStatusRepository.getStatusByEnum(EventStatusEnum.UNCHECKED);
        this.message = "";
    }

    public int getEventID() {
        return this.eventID;
    }

    public User getOrganiser() {
        return organiser;
    }

    public User getCoordinator() {
        return coordinator;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public EventStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(EventStatusEnum statusEnum) {
        EventStatusRepository eventStatusRepository = RepositoryFactory.getEventStatusRepository();
        this.status = eventStatusRepository.getStatusByEnum(statusEnum);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addToMessage(String messageAddition) {
        this.message += "\n" + messageAddition;
    }

    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public String toString() {
        return String.format("(Event) %d: by [%s]%s; %s; %s - %s; %s; %s", eventID, organiser, name, description,
                dateStart, dateFinish, status, message);
    }
}
