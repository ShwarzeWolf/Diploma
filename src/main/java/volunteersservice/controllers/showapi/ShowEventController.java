package volunteersservice.controllers.showapi;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import volunteersservice.models.entities.Event;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.services.defaults.EventServiceDefault;
import volunteersservice.utils.ServiceFactory;

import java.util.List;

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
    public String addEvent(@RequestParam String name, @RequestParam String description, @RequestParam String dateStart,
            @RequestParam String dateFinish) {
        Event ev = events.addEvent(name, description, dateStart, dateFinish);
        log.info(ev);
        return "redirect:/showapi/";
    }

    @GetMapping({ "/events", "" })
    public String eventsList(@RequestParam(required = false, defaultValue = "all") String showType, Model model) {
        List<Event> eventsList = events.getAllEvents();
        log.info("test");
        model.addAttribute("events", eventsList);
        return "main";
    }

    @GetMapping(path = "/event/{eventID}")
    public String getEventByID(@PathVariable(value = "eventID") String eventID, Model model) {
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