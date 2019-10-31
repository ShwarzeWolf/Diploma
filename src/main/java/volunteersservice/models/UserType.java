package volunteersservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VolunteersService.UserType")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeID")
    private int typeID;

    @Column(name = "Name")
    private String name;

    public UserType() {
    }

    public UserType(UserType other) {
        if (this == other) {
            return;
        }
        this.typeID = other.typeID;
        this.name = other.name;
    }

    public int getID() {
        return this.typeID;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format("(UserType) %d: %s", typeID, name);
    }
}
