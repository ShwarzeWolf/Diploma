package volunteersservice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.services.UserVolunteerFunctionService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.services.defaults.EventServiceDefault;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class EventController {
    private static Logger LOG = Logger.getLogger(EventController.class);

    private EventServiceDefault events;

    public EventController() {
        LOG.info("EventController is alive");
        events = new EventServiceDefault();
    }

    @GetMapping("/addEvent")
    public String addEventPage(Model model) {
        // User user = ((UserDetailsImpl)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        // model.addAttribute("user", user);
        return "addEventForm";
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name, @RequestParam String place, @RequestParam String description,
            @RequestParam String dateStart, @RequestParam String dateFinish) {
        events.addEvent(name, Utils.getUserFromContext(), description, place, dateStart, dateFinish);
        return "redirect:/main";
    }

    @GetMapping({ "/main", "" })
    public String eventsList(HttpServletRequest request, Model model) {
        List<Event> eventsList = events.getAllEvents(); // TODO change to getEventsForVolunteers() / others depending on user
        model.addAttribute("events", eventsList);
        // 
        // FIXME #userRole
        //        isUserInRole or hasRole in Thymeleaf aren't working
        // String roleName =
        // SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findAny().get().getAuthority();
        // String roleToString =
        // SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findAny().get().toString()
        // LOG.info("User has role - " + roleName + ", toString - " + roleToString);
        // if (request.isUserInRole(roleName)) {
        // LOG.info("ok");
        // } else {
        // LOG.info("not ok");
        // }
        String roleName = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findAny()
                .get().getAuthority();
        model.addAttribute("roleName", roleName);
        return "main";
    }

    @GetMapping("/main/{eventID}")
    public String getEventByID(@PathVariable int eventID, Model model) {
        Event currentEvent = events.getEventByID(eventID);
        model.addAttribute("event", currentEvent);
        model.addAttribute("user", Utils.getUserFromContext());
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        model.addAttribute("volunteerFunctions", vfs.getVolunteerFunctions(currentEvent));
        return "currentEvent";
    }

    @PostMapping("/main/{eventID}")
    public String addVolunteerFunction(@PathVariable int eventID, @RequestParam String name,
            @RequestParam String description, @RequestParam String requirements, @RequestParam String timeStart,
            @RequestParam String timeFinish, @RequestParam int numberNeeded) {
        Event currentEvent = events.getEventByID(eventID);
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        vfs.addVolunteerFunction(currentEvent, name, description, requirements, timeStart, timeFinish, numberNeeded);
        return "redirect:/main/" + eventID;
    }

    @GetMapping("/volunteerFunction/{volunteerFunctionID}")
    public String getVolunteerFunction(@PathVariable int volunteerFunctionID, Model model) {
        LOG.info("Getting VolunteerFunction with id = " + volunteerFunctionID);
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        UserVolunteerFunctionService uvfs = ServiceFactory.getUserVolunteerFunctionService();
        VolunteerFunction volunteerFunction = vfs.getVolunteerFunctionByID(volunteerFunctionID);
        model.addAttribute("volunteerFunction", volunteerFunction);
        model.addAttribute("userVolunteerFunctions", uvfs.getUserVolunteerFunctionsOfVolunteerFunction(volunteerFunction));
        model.addAttribute("user", Utils.getUserFromContext()); // FIXME #userRole
        return "currentVolunteerFunction";
    }

    @PostMapping("/volunteerFunction/{volunteerFunctionID}")
    public String signUpOnVolunteerFunction(@PathVariable int volunteerFunctionID) {
        UserVolunteerFunctionService uvfService = ServiceFactory.getUserVolunteerFunctionService();
        VolunteerFunctionService vfService = ServiceFactory.getVolunteerFunctionService();
        UserVolunteerFunction uvf = uvfService.addUserVolunteerFunction(Utils.getUserFromContext(),
                vfService.getVolunteerFunctionByID(volunteerFunctionID));
        return "redirect:/main/" + uvf.getVolunteerFunction().getEvent().getEventID();
    }
}