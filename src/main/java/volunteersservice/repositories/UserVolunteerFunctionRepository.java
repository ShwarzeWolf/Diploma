package volunteersservice.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;

@Repository
public interface UserVolunteerFunctionRepository {
    public UserVolunteerFunction getUserVolunteerFunctionByID(int id);

    public UserVolunteerFunction getUserVolunteerFunction(int userID, int volunteerFunctionID);

    public boolean save(UserVolunteerFunction userVolunteerFunction);

    public boolean update(UserVolunteerFunction userVolunteerFunction);

    public List<User> getAllVolunteersOfFunction(VolunteerFunction volunteerFunction);

    public List<User> getVolunteersOfFunction(VolunteerFunction volunteerFunction, UserVolunteerFunctionStatusEnum status);

    public List<UserVolunteerFunction> getUserVolunteerFunctionsOfVolunteerFunction(VolunteerFunction volunteerFunction);

    public boolean alreadySignedUp(int userID, int volunteerFunctionID);

    public List<UserVolunteerFunction> getAllVolunteersOfEvent(int eventId);


    public Long getHoursOfVolunteer(int userId);

    public double getAVGRating(int userId);

}
