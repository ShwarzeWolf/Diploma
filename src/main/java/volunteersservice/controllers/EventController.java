package volunteersservice.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import volunteersservice.services.EventManager;
import volunteersservice.services.RoleManager;
import volunteersservice.services.EventStatusManager.EVENT_STATUS;
import volunteersservice.utils.Utils;
import volunteersservice.models.Event;
import volunteersservice.models.Role;

@Controller
public class EventController {
    // private static final Logger LOG =
    // LogManager.getLogger(EventController.class.getName());

    private EventManager events;

    public EventController() {
        super();
        events = new EventManager();
    }

    @GetMapping("/addEvent")
    public String addEventPage() {
        return "testAddEventForm";
    }

    @PostMapping("/addEvent")
    public @ResponseBody String addEvent(@RequestParam String name, @RequestParam String description,
            @RequestParam String dateStart, @RequestParam String dateFinish) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        events.addEvent(name, description, LocalDateTime.parse(dateStart, formatter),
                LocalDateTime.parse(dateFinish, formatter));
        return "ok";
    }

    @GetMapping("/events")
    public @ResponseBody String eventsList(@RequestParam(required = false, defaultValue = "all") String showType,
            @RequestParam(required = false, defaultValue = "off") boolean activeOnly) {
        List<Event> eventsList;
        StringBuilder sb = new StringBuilder();
        if (showType.equals("all"))
            if (!activeOnly)
                eventsList = events.getAllEvents();
            else
                eventsList = events.getActiveEvents();
        else {
            try {
                EVENT_STATUS status = EVENT_STATUS.valueOf(showType.toUpperCase());
                if (!activeOnly)
                    eventsList = events.getEventsByStatus(status);
                else
                    eventsList = events.getActiveEventsByStatus(status);
            } catch (Exception ex) {
                return String.format("Wrong parameter value: need one of radiobuttons on main page, got %s", showType);
            }
        }
        for (Event e : eventsList) {
            sb.append(e.toString());
            sb.append("<br>");
        }
        if (sb.length() == 0)
            return "There are no events with status: <b>" + showType + "</b>";
        return sb.toString();
    }

    @GetMapping("/event/{eventID}")
    public @ResponseBody String getEventByID(@PathVariable int eventID) {
        Event event = events.getEventByID(Integer.valueOf(eventID));
        if (event == null) {
            return String.format("No such event: eventID=%s", eventID);
        }
        StringBuilder sb = new StringBuilder(event.toString());
        sb.append("<br>");
        RoleManager roleManager = new RoleManager();
        List<Role> roles = roleManager.getRoles(event);
        for (Role r : roles) {
            sb.append(r.toString());
            sb.append("<br>");
        }
        return sb.toString() + Utils.getStaticFileContents("src/main/resources/templates/", "setEventStatus.html",
                "[no status html found]");
    }

    @PostMapping("/event/{eventID}")
    public @ResponseBody String setEventStatus(@PathVariable int eventID,
            @RequestParam(value = "status") String statusName) {
        Event event = events.getEventByID(Integer.valueOf(eventID));
        if (event == null) {
            return String.format("No such event: eventID=%s", eventID);
        }
        try {
            EVENT_STATUS status = EVENT_STATUS.valueOf(statusName.toUpperCase());
            events.setStatus(event, status);
        } catch (Exception ex) {
            return "No such status: " + statusName;
        }
        return "ok<br><a href=\"/event/" + eventID + "\">Refresh</a>";
    }

    @GetMapping("/addEventRoles")
    public String addEventRolesPage() {
        return "testAddEventRolesForm";
    }

    @PostMapping("/addEventRoles")
    public @ResponseBody String addEventRoles(@RequestParam String eventName, @RequestParam String eventDescription,
            @RequestParam String eventDateStart, @RequestParam String eventDateFinish, @RequestParam String roleName,
            @RequestParam String roleDescription, @RequestParam String roleRequirements,
            @RequestParam String roleTimeStart, @RequestParam String roleTimeFinish,
            @RequestParam int roleNumberNeeded) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleName, roleDescription, roleRequirements, LocalDateTime.parse(roleTimeStart, formatter),
                LocalDateTime.parse(roleTimeFinish, formatter), roleNumberNeeded));
        events.addEvent(eventName, eventDescription, LocalDateTime.parse(eventDateStart, formatter),
                LocalDateTime.parse(eventDateFinish, formatter), roles);
        return "ok";
    }
}
