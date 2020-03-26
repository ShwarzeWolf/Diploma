package volunteersservice.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import volunteersservice.models.enums.UserRoleEnum;

@Entity
@Table(name = "VolunteersService.UserRole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private int roleID;

    @Column(name = "Name")
    private String name;

    public UserRole() {
    }

    public UserRole(UserRole other) {
        this.roleID = other.roleID;
        this.name = other.name;
    }

    public int getUserRoleID() {
        return this.roleID;
    }

    public String getName() {
        return this.name;
    }

    public UserRoleEnum getEnum() {
        return UserRoleEnum.valueOf(this.name.toUpperCase());
    }

    @Override
    public String toString() {
        return String.format("(UserRole %d): %s", roleID, name);
    }
}
