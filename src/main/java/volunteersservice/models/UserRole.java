package volunteersservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.RoleStatusDAO;
import volunteersservice.enums.RoleStatusEnum;

@Entity
@Table(name = "VolunteersService.UsersRoles")
public class UserRole {

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        RoleStatusDAO roleStatusDAO = DAOFabric.getRoleStatusDAO();
        this.status = roleStatusDAO.getStatusByName(RoleStatusEnum.UNCHECKED.name().toLowerCase());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserRoleID")
    int userRoleID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID", nullable = false)
    Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID", nullable = false)
    RoleStatus status;

    public User getUser() {
        return user;
    }

    public Role getRole() {
        return role;
    }

    public RoleStatus getStatus() {
        return status;
    }
}