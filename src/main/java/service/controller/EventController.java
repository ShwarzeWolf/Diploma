package service.controller;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import service.businesslogic.EventManager;
import service.dal.models.Event;

@RestController
public class EventController {
    private static final Logger LOG = LogManager.getLogger(EventController.class.getName());

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
            LOG.error(ex);
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

    @RequestMapping(path = "/events", method = RequestMethod.GET)
    public @ResponseBody String eventsList(@RequestParam(required = false, defaultValue = "all") String showType) {
        StringBuilder sb = new StringBuilder();
        if (showType.equals("all")) {
            for (Event e : events.getAllEvents()) {
                sb.append(e.toString());
                sb.append("<br>");
            }
            return sb.toString();
        } else if (showType.equals("active")) {
            for (Event e : events.getActiveEvents()) {
                sb.append(e.toString());
                sb.append("<br>");
            }
            return sb.toString();
        } else
            return String.format("Wrong parameter value: need (\"all\" | \"active\"), got %s", showType);
    }

    @RequestMapping(path = "/event/{eventID}", method = RequestMethod.GET)
    public @ResponseBody String getEventByID(@PathVariable(value = "eventID") String eventID) {
        try {
            return events.getEventByID(Integer.valueOf(eventID)).toString();
        } catch (NullPointerException ex) {
            return String.format("No such event: eventID=%s", eventID);
        } catch (NumberFormatException ex) {
            return String.format("%s is not a valid eventID", eventID);
        } catch (Exception ex) {
            return String.format("Exception occured: %s", ex);
        }
    }
}
