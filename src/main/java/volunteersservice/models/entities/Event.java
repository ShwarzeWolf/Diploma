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

    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    public String getName() {
        return this.name;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = LocalDateTime.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDateTime dateFinish) {
        this.dateFinish = dateFinish;
    }

    public void setDateFinish(String dateFinish) {
        this.dateFinish = LocalDateTime.parse(dateFinish, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatusEnum statusEnum) {
        EventStatusRepository eventStatusRepository = RepositoryFactory.getEventStatusRepository();
        this.status = eventStatusRepository.getStatusByEnum(statusEnum);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addToMessage(String messageAddition) {
        this.message += "\n" + messageAddition;
    }

    @Override
    public String toString() {
        return String.format(
                "(Event %d) \"%s\"; organised by \"%s\", coordinated by \"%s\"; %s; %s - %s; %s; message(%s)", eventID,
                name, organiser.getLogin(), coordinator == null ? "null" : coordinator.getLogin(),
                description.replace("\r", "\\r").replace("\n", "\\n"), dateStart, dateFinish, status, message);
    }
}
