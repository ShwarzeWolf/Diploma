package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.UserVolunteerFunctionService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {
    private static Logger LOG = Logger.getLogger(EventController.class);

    private EventService eventService;
    private UserVolunteerFunctionService userVolunteerFunctionService;

    public EventController() {
        LOG.info("EventController is alive");
        eventService = ServiceFactory.getEventService();
        userVolunteerFunctionService = ServiceFactory.getUserVolunteerFunctionService();
    }

    @GetMapping("/addEvent")
    public String addEventPage(Model model) {
        return "addEventForm";
    }

    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name, @RequestParam String place, @RequestParam String description,
            @RequestParam String dateStart, @RequestParam String dateFinish) {
        eventService.addEvent(name, Utils.getUserFromContext(), description, place, dateStart, dateFinish);
        return "redirect:/main";
    }

    @GetMapping({ "/main", "/" })
    public String eventsList(Authentication auth, HttpServletRequest request, Model model) {
        List<Event> eventsList = eventService.getEventsForVolunteers();
        model.addAttribute("events", eventsList);
        User user = Utils.getUserFromContext();
        model.addAttribute("roleName", user != null ? user.getUserRole().getName() : "ROLE_ANONYMOUS");
        if (user != null) {
            String str = new String(user.getName() + " : " + user.getUserRole().getName());
            model.addAttribute("name_and_role", str);
        }
        return "main";
    }

    @GetMapping("/listOfMyEvents")
    public String myEventPool(Model model) {
        EventService eventService = ServiceFactory.getEventService();
        User user = Utils.getUserFromContext();
        model.addAttribute("advanced", true);
        if (user.getUserRole().getName().equals("COORDINATOR")) {
            model.addAttribute("currentEvents", eventService.getActiveEventsCoordinatedBy(user));
            model.addAttribute("expiredEvents", eventService.getExpiredEventsCoordinatedBy(user));
        } else if (user.getUserRole().getName().equals("VOLUNTEER")) {
            model.addAttribute("currentEvents", eventService.getActiveEventsWithVolunteer(user));
            model.addAttribute("expiredEvents", eventService.getExpiredEventsWithVolunteer(user));
        } else if (user.getUserRole().getName().equals("ORGANISER")) {
            model.addAttribute("currentEvents", eventService.getActiveEventsOfOrganiser(user));
            model.addAttribute("expiredEvents", eventService.getExpiredEventsOfOrganiser(user));
        }
        return "myEventPool";
    }

    @GetMapping("/listOfEventsToManage")
    public String poolEventsToManage(Model model) {
        EventService eventService = ServiceFactory.getEventService();
        User user = Utils.getUserFromContext();
        model.addAttribute("advanced", true);
        if (user.getUserRole().getName().equals("COORDINATOR"))
            model.addAttribute("events", eventService.getEventsForCoordinators());
        else if (user.getUserRole().getName().equals("MANAGER"))
            model.addAttribute("events", eventService.getEventsForManagers());
        else if (user.getUserRole().getName().equals("ADMIN"))
            model.addAttribute("events", eventService.getAllEvents());
        return "poolEventsToManage";
    }

    @GetMapping("/main/{eventID}")
    public String getEventByID(@PathVariable int eventID, Model model) {
        Event currentEvent = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();
        model.addAttribute("roleName", user != null ? user.getUserRole().getName() : "ROLE_ANONYMOUS");
        model.addAttribute("event", currentEvent);
        model.addAttribute("user", Utils.getUserFromContext());
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        model.addAttribute("volunteerFunctions", vfs.getVolunteerFunctions(currentEvent));
        return "currentEvent";
    }

    // TODO "APROVED"/"REJECTED" are only avaliable for MANAGER role and only for UNCHECKED events
    // "PUBLISHED" is only avaliabe for COORDINATOR of the event when it's COORDINATED
    // "COORDINATED" is only avaliable for COORDINATOR of the event when it's PUBLISHED
    @PostMapping("/main/{eventID}/setEventStatus")
    public String setEventStatus(@PathVariable int eventID, @RequestParam String changeStatus, @RequestParam(required = false) String message) {
        @NotNull Event event = eventService.getEventByID(eventID);
        LOG.info("Changing event status: " + event + ", " + changeStatus);
        eventService.setStatus(event, EventStatusEnum.valueOf(changeStatus));
        if (changeStatus.equals("APPROVED") || changeStatus.equals("REJECTED"))
            eventService.setMessage(event, message);
        return "redirect:/main/" + eventID;
    }

    // TODO "coordinate" is only avaliable for COORDINATOR role and only for APROVED
    // events
    // and "coordinate drop" is only avaliable for COORDINATOR of this event
    @PostMapping("/main/{eventID}/coordinate")
    public String coordinateEvent(@PathVariable int eventID,
            @RequestParam(required = false, defaultValue = "false") boolean drop) {
        User user = Utils.getUserFromContext();
        Event event = eventService.getEventByID(eventID);
        if (!drop) {
            LOG.info("Setting a coordinator for event " + eventID + ": " + user.getLogin());
            eventService.setCoordinator(event, user);
            eventService.setStatus(event, EventStatusEnum.COORDINATED);
        } else {
            LOG.info("Dropping a coordinator of event: " + eventID + ", decided by " + user.getLogin());
            eventService.setCoordinator(event, null);
            eventService.setStatus(event, EventStatusEnum.APPROVED);
        }
        return "redirect:/main/" + eventID;
    }

    @PostMapping("/main/{eventID}")
    public String addVolunteerFunction(@PathVariable int eventID, @RequestParam String name,
            @RequestParam String description, @RequestParam String requirements, @RequestParam String timeStart,
            @RequestParam String timeFinish, @RequestParam int numberNeeded) {
        Event currentEvent = eventService.getEventByID(eventID);
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
        model.addAttribute("userVolunteerFunctions",
                uvfs.getUserVolunteerFunctionsOfVolunteerFunction(volunteerFunction));
        model.addAttribute("user", Utils.getUserFromContext());
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

    @GetMapping("/main/{eventID}/volunteers")
    public String getListOfVolunteers(@PathVariable(value = "eventID") String eventID, Model model) {
        Event currentEvent = eventService.getEventByID(Integer.parseInt(eventID));
        List<UserVolunteerFunction> registeredUsers = userVolunteerFunctionService
                .getAllVolunteersOfEvent(currentEvent);
        model.addAttribute("registeredUsers", registeredUsers);

        LocalDateTime timeNow = LocalDateTime.now();
        model.addAttribute("timeNow", timeNow);

        return "volunteersForEvent";
    }

    @PostMapping("/main/{eventID}/volunteers")
    public String changeVolunteerStatus(@PathVariable(value="eventID") String eventID,
                                        @RequestParam (value="newStatus", required=true) String newStatus,
                                        @RequestParam (value="userVolunteerFunctionID", required=true) String userVolunteerFunctionId,
                                        @RequestParam (value="estimation", required=false) String estimation,
                                        @RequestParam (value="numberOfHours", required=false) String numberOfHours){

        UserVolunteerFunction uvf = userVolunteerFunctionService.getUserVolunteerFunctionByID(Integer.parseInt(userVolunteerFunctionId));

        switch (newStatus){
            case "Accept":
                userVolunteerFunctionService.setStatus(uvf, UserVolunteerFunctionStatusEnum.APPROVED);
                break;
            case "Reject":
                userVolunteerFunctionService.setStatus(uvf, UserVolunteerFunctionStatusEnum.DENIED);
                break;
            case "WasAbsent":
                userVolunteerFunctionService.setStatus(uvf, UserVolunteerFunctionStatusEnum.ABSENT);
                break;
            case "Participated":
                userVolunteerFunctionService.setStatus(uvf, UserVolunteerFunctionStatusEnum.PARTICIPATED);
                userVolunteerFunctionService.setEstimation(uvf, Integer.parseInt(numberOfHours), Integer.parseInt(estimation));
                break;
            default:
                LOG.info("no status changed");
                break;
        }

        return "redirect:/main/" + eventID + "/volunteers";
    }
}