package volunteersservice.models.entities;

import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.StatusRepository;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ManagerID", referencedColumnName = "UserID")
    User manager;

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

    @Column(name = "Requirements", nullable = false)
    @NotNull
    private String requirements;

    @Column(name = "ClothesType", nullable = false)
    @NotNull
    private String clothesType;

    @Column(name = "Accommodation", nullable = false)
    @NotNull
    private String accommodation;

    @Column(name = "Food", nullable = false)
    @NotNull
    private String food;

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
        this.manager = other.manager;
        this.description = other.description;
        this.place = other.place;
        this.dateStart = other.dateStart;
        this.dateFinish = other.dateFinish;
        this.accommodation = other.accommodation;
        this.clothesType = other.clothesType;
        this.food = other.food;
        this.requirements = other.requirements;
        this.status = other.status;
    }

    public Event(String name,
                 User organiser,
                 String description,
                 String place,
                 LocalDateTime dateStart,
                 LocalDateTime dateFinish,
                 String requirements,
                 String clothesType,
                 String accommodation,
                 String food) {
        this.name = name;
        this.organiser = organiser;
        this.description = description;
        this.place = place;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.requirements = requirements;
        this.clothesType = clothesType;
        this.accommodation = accommodation;
        this.food = food;
        this.message = "";

        StatusRepository eventStatusRepository = RepositoryFactory.getStatusRepository();
        this.status = eventStatusRepository.getStatusByEnum(EventStatusEnum.CREATED);

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

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
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

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = LocalDateTime.parse(dateStart, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public LocalDateTime getDateFinish() {
        return dateFinish;
    }

    public String getPrettyDateStart() {
        return dateStart.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    public String getPrettyDateFinish() {
        return dateFinish.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public void setDateFinish(LocalDateTime dateFinish) {
        this.dateFinish = dateFinish;
    }

    public void setDateFinish(String dateFinish) {
        this.dateFinish = LocalDateTime.parse(dateFinish, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public void setRequirements(String requirements){
        this.requirements = requirements;
    }

    public String getRequirements(){
        return this.requirements;
    }

    public void setAccommodation(String accommodation){
        this.accommodation = accommodation;
    }

    public String getAccommodation(){
        return this.accommodation;
    }

    public void setClothesType(String clothesType){
        this.clothesType = clothesType;
    }

    public String getClothesType(){
        return this.clothesType;
    }

    public void setFood(String food){
        this.food = food;
    }

    public String getFood(){
        return this.food;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatusEnum statusEnum) {
        StatusRepository eventStatusRepository = RepositoryFactory.getStatusRepository();
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
