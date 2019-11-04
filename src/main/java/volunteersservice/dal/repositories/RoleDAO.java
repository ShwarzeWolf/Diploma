package volunteersservice.dal.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.Event;
import volunteersservice.models.Role;

@Repository
public interface RoleDAO {
    public boolean save(Role role);
    public List<Role> getRoles(Event event);
}