package volunteersservice.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import volunteersservice.models.enums.VolunteerFunctionStatusEnum;
import volunteersservice.repositories.VolunteerFunctionStatusRepository;
import volunteersservice.utils.RepositoryFactory;

@Entity
@Table(name = "VolunteersService.UsersRoles")
public class UserVolunteerFunction {

    public UserVolunteerFunction() {
    }

    public UserVolunteerFunction(User user, VolunteerFunction volunteerFunction) {
        this.user = user;
        this.volunteerFunction = volunteerFunction;
        VolunteerFunctionStatusRepository volunteerFunctionStatusRepository = RepositoryFactory.getVolunteerFunctionStatusRepository();
        this.status = volunteerFunctionStatusRepository.getStatusByName(VolunteerFunctionStatusEnum.UNCHECKED.name().toLowerCase());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserRoleID")
    int UserVolunteerRoFunction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID", nullable = false)
    VolunteerFunction volunteerFunction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID", nullable = false)
    VolunteerFunctionStatus status;

    public User getUser() {
        return user;
    }

    public VolunteerFunction getvolunteerFunction() {
        return volunteerFunction;
    }

    public VolunteerFunctionStatus getStatus() {
        return status;
    }
}