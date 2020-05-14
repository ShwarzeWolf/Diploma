package volunteersservice.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "VolunteersService.PublicityStatus")
public class PublicityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StatusID")
    private int statusID;

    @Column(name = "Name", nullable = false)
    private String name;

    public PublicityStatus() {}

    public PublicityStatus(PublicityStatus other) {
        this.statusID = other.statusID;
        this.name = other.name;
    }

    public String getName() {
        return name;
    }

    public String getRussishName(){
        switch (this.name){
            case "OPEN": return "Открытое";
            case "CLOSED": return "Закрытое";
            default: return this.name;
        }
    }

    public int getStatusID() {
        return statusID;
    }

    @Override
    public String toString() {
        return String.format("(CategoryStatus %d): %s", statusID, name);
    }
}
