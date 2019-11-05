package volunteersservice.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import volunteersservice.models.Event;
import volunteersservice.businesslogic.ManagerFabric;
import volunteersservice.businesslogic.services.EventManager;
import volunteersservice.businesslogic.services.RoleManager;

@Controller
public class RolesController {

    RoleManager roles;

    public RolesController() {
        roles = ManagerFabric.getRoleManager();
    }

    @GetMapping(path = "/event/{eventID}/addRole")
    public String addRolePage(@PathVariable(value = "eventID") String eventID) {
        return "testAddRoleForm";
    }

    @PostMapping(path = "/event/{eventID}/addRole")
    public @ResponseBody String addRole(@PathVariable(value = "eventID") int eventID, @RequestParam String name,
            @RequestParam String description, @RequestParam String requirements, @RequestParam String timeStart,
            @RequestParam String timeFinish, @RequestParam int numberNeeded) {
        EventManager events = ManagerFabric.getEventManager();
        Event event = events.getEventByID(eventID);
        if (event == null) {
            return "No Such event";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (roles.addRole(event, name, description, requirements, LocalDateTime.parse(timeStart, formatter),
                LocalDateTime.parse(timeFinish, formatter), numberNeeded))
            return "Role added";
        else
            return "Error while adding a role";
    }
}