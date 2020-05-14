package volunteersservice.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "VolunteersService.CategoryStatus")
public class CategoryStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private int statusID;

    @Column(name = "Name", nullable = false)
    private String name;

    public CategoryStatus() {}

    public CategoryStatus(CategoryStatus other) {
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
        return String.format("(CategoryStatus %d): %s", statusID, name);
    }
}
