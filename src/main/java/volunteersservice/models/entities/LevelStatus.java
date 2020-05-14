package volunteersservice.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "VolunteersService.LevelStatus")
public class LevelStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private int statusID;

    @Column(name = "Name", nullable = false)
    private String name;

    public LevelStatus() {}

    public LevelStatus(LevelStatus other) {
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
        return String.format("(LevelStatus %d): %s", statusID, name);
    }
}
