package volunteersservice.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VolunteersService.UserVolunteerFunctionStatus")
public class UserVolunteerFunctionStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private int statusID;

    @Column(name = "Name", nullable = false)
    private String name;

    public UserVolunteerFunctionStatus() {
    }

    public UserVolunteerFunctionStatus(UserVolunteerFunctionStatus other) {
        this.statusID = other.statusID;
        this.name = other.name;
    }

    public int getStatusID() {
        return statusID;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("(UserVolunteerFunctionStatus) %d: %s", statusID, name);
    }
}
