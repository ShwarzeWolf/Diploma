package service.dal.models;

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

    @Column(name = "Name")
    private String name;

    public EventStatus() {
    }

    public EventStatus(EventStatus other) {
        if (this == other)
            return;
        this.statusID = other.statusID;
        this.name = other.name;
    }

    @Override
    public String toString() {
        return String.format("(EventStatus) %d: %s", statusID, name);
    }
}
