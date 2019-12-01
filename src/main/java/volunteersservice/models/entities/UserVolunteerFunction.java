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

import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.repositories.UserVolunteerFunctionStatusRepository;
import volunteersservice.utils.RepositoryFactory;

@Entity
@Table(name = "VolunteersService.UsersVolunteerFunctions")
public class UserVolunteerFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserVolunteerFunctionID")
    int userVolunteerFunctionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VolunteerFunctionID", nullable = false)
    VolunteerFunction volunteerFunction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID", nullable = false)
    UserVolunteerFunctionStatus status;

    public UserVolunteerFunction() {
    }

    public UserVolunteerFunction(UserVolunteerFunction other) {
        this.userVolunteerFunctionID = other.userVolunteerFunctionID;
        this.user = other.user;
        this.volunteerFunction = other.volunteerFunction;
        this.status = other.status;
    }

    public UserVolunteerFunction(User user, VolunteerFunction volunteerFunction) {
        this.user = user;
        this.volunteerFunction = volunteerFunction;
        UserVolunteerFunctionStatusRepository volunteerFunctionStatusRepository = RepositoryFactory.getUserVolunteerFunctionStatusRepository();
        this.status = volunteerFunctionStatusRepository.getStatusByEnum(UserVolunteerFunctionStatusEnum.UNCHECKED);
    }

    public User getUser() {
        return user;
    }

    public VolunteerFunction getVolunteerFunction() {
        return volunteerFunction;
    }

    public UserVolunteerFunctionStatus getStatus() {
        return status;
    }

    public void setStatus(UserVolunteerFunctionStatus status) {
        this.status = status;
    }

    public int getUserVolunteerFunctionID(){
        return this.userVolunteerFunctionID;
    }
}