package volunteersservice.controllers.showapi;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteersservice.models.entities.Event;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.services.defaults.EventServiceDefault;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
@RequestMapping("/showapi")
public class ShowEventController {
    private static Logger log = Logger.getLogger(ShowEventController.class);

    private EventServiceDefault events;

    public ShowEventController() {
        events = new EventServiceDefault();
    }

    @GetMapping("/addEvent")
    public String addEventPage() {
        return "showapi/testAddEventForm";
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name, @RequestParam String description, @RequestParam String place, @RequestParam String dateStart,
            @RequestParam String dateFinish) {
        Event ev = events.addEvent(name, Utils.getUserFromContext(), description, place, dateStart, dateFinish);
        log.info(ev + " is added");
        return "redirect:/showapi/";
    }

    @GetMapping("/events")
    public String eventsList(@RequestParam(required = false, defaultValue = "all") String showType, Model model) {
        List<Event> eventsList = events.getAllEvents();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findAny().get().getAuthority();
        model.addAttribute("role", role);
        model.addAttribute("events", eventsList);
        return "main";
    }

    @GetMapping(path = "/event/{eventID}")
    public String getEventByID(@PathVariable String eventID, Model model) {
        try {
            Event currentEvent = events.getEventByID(Integer.valueOf(eventID));
            model.addAttribute("event", currentEvent);
            VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
            model.addAttribute("volunteerFunctions", vfs.getVolunteerFunctions(currentEvent));
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