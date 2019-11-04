package volunteersservice.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.RoleDAO;
import volunteersservice.models.Event;
import volunteersservice.models.Role;

@Service
public class RoleManager {

    private final RoleDAO roleDAO;

    public RoleManager() {
        roleDAO = DAOFabric.getRoleDAO();
    }

    public boolean addRole(Event event, String name, String description, String requirements, LocalDateTime timeStart,
            LocalDateTime timeFinish, int numberNeeded) {
        if (event.getDateStart().toLocalDateTime().isAfter(timeStart)
                || event.getDateFinish().toLocalDateTime().isBefore(timeFinish) || timeStart.isAfter(timeFinish))
            return false;
        return roleDAO.save(new Role(event, name, description, requirements, Timestamp.valueOf(timeStart),
                Timestamp.valueOf(timeFinish), numberNeeded));
    }

    public boolean addRole(Role role) {
        return roleDAO.save(role);
    }

    public List<Role> getRoles(Event event) {
        return roleDAO.getRoles(event);
    }
}