package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.services.defaults.EventServiceDefault;
import volunteersservice.services.defaults.VolunteerFunctionServiceDefault;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class EventController {
    private static Logger log = Logger.getLogger(UserController.class);

    private EventServiceDefault eventsManager;
    private VolunteerFunctionServiceDefault volunteerFunctionManager;

    public EventController() {
        super();
        eventsManager = new EventServiceDefault();
        volunteerFunctionManager = new VolunteerFunctionServiceDefault();
    }

    @GetMapping("/addEvent")
    public String addEventPage() {
        return  "testAddEventForm";
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name,
                                         @RequestParam String description,
                                         @RequestParam String dateStart,
                                         @RequestParam String dateFinish) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        eventsManager.addEvent(name, description, LocalDateTime.parse(dateStart, formatter),
                LocalDateTime.parse(dateFinish, formatter));
        return "redirect:/main";
    }

    @GetMapping({"/main", ""})
    public String eventsList(@RequestParam(required = false, defaultValue = "all") String showType,
                                           Model model) {
        List<Event> eventsList = eventsManager.getAllEvents();
        model.addAttribute("events", eventsList);
        return "main";
    }

    @GetMapping(path = "/main/{eventID}")
    public String getEventByID(@PathVariable(value = "eventID") String eventID,
                               Model model) {
        try {
            Event event = eventsManager.getEventByID(Integer.valueOf(eventID));
            List<VolunteerFunction> volunteerFunctions = volunteerFunctionManager.getVolunteerFunctions(event);

            model.addAttribute("event", event);
            model.addAttribute("volunteerFunctions", volunteerFunctions);

            return "currentEvent";
        } catch (NullPointerException ex) {
            return String.format("No such event: eventID=%s", eventID);
        } catch (NumberFormatException ex) {
            return String.format("%s is not a valid eventID", eventID);
        } catch (Exception ex) {
            return String.format("Exception occurred: %s", ex);
        }
    }

    @GetMapping("/main/{eventID}/addVolunteerFunction")
    public String addVolunteerFunctionPage(@PathVariable(value = "eventID") String eventID){
        return "addVolunteerFunctionForm";
    }

    @PostMapping("/main/{eventID}/addVolunteerFunction")
    public String addVolunteerFunction(@PathVariable(value="eventID") String eventID,
                                       @RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam String requirements,
                                       @RequestParam String timeStart,
                                       @RequestParam String timeFinish,
                                       @RequestParam int numberOfVolunteers){

        Event event = eventsManager.getEventByID(Integer.parseInt(eventID));

        if (event == null) {
            return "Event does not exist";
        };

        volunteerFunctionManager.addVolunteerFunction(event, name, description, requirements, timeStart, timeFinish,
                numberOfVolunteers);

        return "redirect:/main/" + eventID;
    }
}