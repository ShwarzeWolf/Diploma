package volunteersservice.controllers.showapi;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
@RequestMapping("/testapi")
public class EventController {
    // private static final Logger LOG =
    // LogManager.getLogger(EventController.class.getName());

    private EventService events;

    public EventController() {
        events = ServiceFactory.getEventService();
    }

    @GetMapping("/addEvent")
    public String addEventPage() {
        return "testapi/testAddEventForm";
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
                EventStatusEnum status = EventStatusEnum.valueOf(showType.toUpperCase());
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
        VolunteerFunctionService volunteerFunctionService = ServiceFactory.getVolunteerFunctionService();
        List<VolunteerFunction> volunteerFunctions = volunteerFunctionService.getVolunteerFunctions(event);
        for (VolunteerFunction r : volunteerFunctions) {
            sb.append(r.toString());
            sb.append("<br>");
        }
        return sb.toString() + Utils.getStaticFileContents("src/main/resources/templates/",
                "testapi/setEventStatus.html", "[no status html found]");
    }

    @PostMapping("/event/{eventID}")
    public @ResponseBody String setEventStatus(@PathVariable int eventID,
            @RequestParam(value = "status") String statusName) {
        Event event = events.getEventByID(Integer.valueOf(eventID));
        if (event == null) {
            return String.format("No such event: eventID=%s", eventID);
        }
        try {
            EventStatusEnum status = EventStatusEnum.valueOf(statusName.toUpperCase());
            events.setStatus(event, status);
        } catch (Exception ex) {
            return "No such status: " + statusName;
        }
        return "ok<br><a href=\"/event/" + eventID + "\">Refresh</a>";
    }

    @GetMapping("/addEventVolunteerFunctions")
    public String addEventvolunteerFunctionsPage() {
        return "testapi/testAddEventVolunteerFunctionsForm";
    }

    @PostMapping("/addEventVolunteerFunctions")
    public @ResponseBody String addEventVolunteerFunctions(@RequestParam String eventName,
            @RequestParam String eventDescription, @RequestParam String eventDateStart,
            @RequestParam String eventDateFinish, @RequestParam String volunteerFunctionName,
            @RequestParam String volunteerFunctionDescription, @RequestParam String volunteerFunctionRequirements,
            @RequestParam String volunteerFunctionTimeStart, @RequestParam String volunteerFunctionTimeFinish,
            @RequestParam int volunteerFunctionNumberNeeded) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        List<VolunteerFunction> volunteerFunctions = new ArrayList<>();
        volunteerFunctions.add(new VolunteerFunction(volunteerFunctionName, volunteerFunctionDescription,
                volunteerFunctionRequirements, LocalDateTime.parse(volunteerFunctionTimeStart, formatter),
                LocalDateTime.parse(volunteerFunctionTimeFinish, formatter), volunteerFunctionNumberNeeded));
        events.addEvent(eventName, eventDescription, LocalDateTime.parse(eventDateStart, formatter),
                LocalDateTime.parse(eventDateFinish, formatter), volunteerFunctions);
        return "ok";
    }
}
