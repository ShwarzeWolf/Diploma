package volunteersService.controllers;

import jdk.internal.util.xml.PropertiesDefaultHandler;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import volunteersService.services.EventManager;
import volunteersService.models.Event;
import volunteersService.utils.Utils;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {

    private EventManager events;

    public EventController() {
        super();
        events = new EventManager();
    }

    @RequestMapping(path = "/addEvent", method = RequestMethod.GET)
    public @ResponseBody String addEventPage() {
        try {
            return new String(Files.readAllBytes(
                    FileSystems.getDefault().getPath("src/main/resources/templates", "testAddEventForm.html")));
        } catch (Exception ex) {
            return "testAddEvent: use post-request";
        }
    }

    @RequestMapping(path = "/addEvent", method = RequestMethod.POST)
    public @ResponseBody String addEvent(@RequestParam String name, @RequestParam String description,
            @RequestParam String dateStart, @RequestParam String dateFinish) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        events.addEvent(name, description, LocalDateTime.parse(dateStart, formatter),
                LocalDateTime.parse(dateFinish, formatter));
        return "ok";
    }

    @GetMapping("/events")
    public String eventsList(@RequestParam(required = false, defaultValue = "all") String showType,
                                           Model model) {
        List<Event> eventsList = events.getAllEvents();
        model.addAttribute("events", eventsList);
        model.addAttribute("name", "Christina");
        return "eventsPage";
    }

    @RequestMapping(path = "/events/{eventID}", method = RequestMethod.GET)
    public String getEventByID(@PathVariable(value = "eventID") String eventID,
                               Model model) {
        try {
            Event currentEvent = events.getEventByID(Integer.valueOf(eventID));
            model.addAttribute("event", currentEvent);

            return "currentEventPage";
        } catch (NullPointerException ex) {
            return String.format("No such event: eventID=%s", eventID);
        } catch (NumberFormatException ex) {
            return String.format("%s is not a valid eventID", eventID);
        } catch (Exception ex) {
            return String.format("Exception occured: %s", ex);
        }
    }
}