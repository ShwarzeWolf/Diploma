package volunteersservice.services.defaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.Role;
import volunteersservice.repositories.RoleDAO;
import volunteersservice.services.RoleManager;
import volunteersservice.utils.DAOFabric;

@Service
public class RoleManagerDefault implements RoleManager {

    private final RoleDAO roleDAO;

    public RoleManagerDefault() {
        roleDAO = DAOFabric.getRoleDAO();
    }

    @Override
    public boolean addRole(Event event, String name, String description, String requirements, LocalDateTime timeStart,
            LocalDateTime timeFinish, int numberNeeded) {
        if (event.getDateStart().toLocalDateTime().isAfter(timeStart)
                || event.getDateFinish().toLocalDateTime().isBefore(timeFinish) || timeStart.isAfter(timeFinish))
            return false;
        return roleDAO.save(new Role(event, name, description, requirements, Timestamp.valueOf(timeStart),
                Timestamp.valueOf(timeFinish), numberNeeded));
    }

    @Override
    public boolean addRole(Role role) {
        return roleDAO.save(role);
    }

    @Override
    public List<Role> getRoles(Event event) {
        return roleDAO.getRoles(event);
    }
}