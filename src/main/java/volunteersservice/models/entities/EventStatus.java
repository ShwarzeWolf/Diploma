package volunteersservice.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VolunteersService.EventStatus")
public class EventStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private int statusID;

    @Column(name = "Name", nullable = false)
    private String name;

    public EventStatus() {}

    public EventStatus(EventStatus other) {
        this.statusID = other.statusID;
        this.name = other.name;
    }

    public String getName() {
        return name;
    }

    public String getRussishName() {
        switch (this.name) {
            case "CREATED":
                return "Создана";
            case "UNCHECKED":
                return "На рассмотрении";
            case "APPROVED":
                return "Одобрена";
            case "ASSIGNED":
                return "Назначена";
            case "FINISHED":
                return "Завершена";
            case "DENIED":
                return "Отклонена";
            default:
                return name;
        }
    }
    public int getStatusID() {
        return statusID;
    }

    @Override
    public String toString() {
        return String.format("(EventStatus %d): %s", statusID, name);
    }
}
