package volunteersservice.models;

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

import volunteersservice.services.EventStatusManager;
import volunteersservice.services.EventStatusManager.EVENT_STATUS;
import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.EventStatusDAO;

@Entity
@Table(name = "VolunteersService.Events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventID")
    int eventID;

    @Column(name = "Name", nullable = false)
    @NotNull
    @NotEmpty
    String name;

    @Column(name = "Description", nullable = false)
    @NotNull
    @NotEmpty
    String description;

    @Column(name = "DateStart", nullable = false)
    @NotNull
    Timestamp dateStart;

    @Column(name = "DateFinish", nullable = false)
    @NotNull
    Timestamp dateFinish;

    public Event() {
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID", nullable = false)
    EventStatus status;

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
        EventStatusDAO eventStatusDAO = DAOFabric.getEventStatusDAO();
        this.status = eventStatusDAO.getStatusByName(EventStatusManager.EVENT_STATUS.UNCHECKED.name().toLowerCase());
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

    public void setStatus(EVENT_STATUS statusEnum) {
        EventStatusDAO eventStatusDAO = DAOFabric.getEventStatusDAO();
        this.status = eventStatusDAO.getStatusByName(statusEnum.name().toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("(Event) %d: %s; %s; %s - %s; %s", eventID, name, description, dateStart, dateFinish, status);
    }
}
