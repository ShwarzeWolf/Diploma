package volunteersservice.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.Role;

@Service
public interface RoleManager {

	public boolean addRole(Event event, String name, String description, String requirements, LocalDateTime timeStart,
			LocalDateTime timeFinish, int numberNeeded);

	public boolean addRole(Role role);

	public List<Role> getRoles(Event event);
}