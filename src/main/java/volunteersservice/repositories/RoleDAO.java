package volunteersservice.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.Role;

@Repository
public interface RoleDAO {
    public boolean save(Role role);
    public List<Role> getRoles(Event event);
}