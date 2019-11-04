package volunteersservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VolunteersService.RoleStatus")
public class RoleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    int statusID;

    @Column(name = "Name", nullable = false)
    String name;

    public RoleStatus() {
    }

    public RoleStatus(RoleStatus other) {
        if (this == other)
            return;
        this.statusID = other.statusID;
        this.name = other.name;
    }

    public String getName() {
        return name;
    }

    public int getStatusID() {
        return statusID;
    }

    @Override
    public String toString() {
        return String.format("(RoleStatus) %d: %s", statusID, name);
    }
}
