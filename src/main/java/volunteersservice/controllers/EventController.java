package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import volunteersservice.models.entities.Event;
import volunteersservice.services.defaults.EventServiceDefault;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class EventController {
    private static Logger log = Logger.getLogger(UserController.class);

    private EventServiceDefault events;

    public EventController() {
        super();
        events = new EventServiceDefault();
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
        events.addEvent(name, description, LocalDateTime.parse(dateStart, formatter),
                LocalDateTime.parse(dateFinish, formatter));
        return "redirect:/main";
    }

    @GetMapping({"/main", ""})
    public String eventsList(@RequestParam(required = false, defaultValue = "all") String showType,
                                           Model model) {
        List<Event> eventsList = events.getAllEvents();
        model.addAttribute("events", eventsList);
        return "main";
    }

    @GetMapping(path = "/main/{eventID}")
    public String getEventByID(@PathVariable(value = "eventID") String eventID,
                               Model model) {
        try {
            Event currentEvent = events.getEventByID(Integer.valueOf(eventID));
            model.addAttribute("event", currentEvent);

            return "currentEvent";
        } catch (NullPointerException ex) {
            return String.format("No such event: eventID=%s", eventID);
        } catch (NumberFormatException ex) {
            return String.format("%s is not a valid eventID", eventID);
        } catch (Exception ex) {
            return String.format("Exception occurred: %s", ex);
        }
    }
}