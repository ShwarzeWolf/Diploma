package volunteersservice.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class EventController {
    private final static Logger LOG = Logger.getLogger(EventController.class);

    private final EventService eventService = ServiceFactory.getEventService();

    @PreAuthorize("hasAuthority('ORGANISER')")
    @GetMapping("/addEvent")
    public String addEventPage(Model model) {
        return "addEventForm";
    }

    @PreAuthorize("hasAuthority('ORGANISER')")
    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name, @RequestParam String place, @RequestParam String description,
            @RequestParam String dateStart, @RequestParam String dateFinish) {
        Event ev = eventService.addEvent(name, Utils.getUserFromContext(), description, place, dateStart, dateFinish);
        LOG.info(String.format("User \"%s\" added event %s", Utils.getUserFromContext().getLogin(), ev));
        return "redirect:/main";
    }



    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MANAGER', 'ADMIN')")
    @GetMapping("/listOfEventsToManage")
    public String poolEventsToManage(Model model) {
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

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MANAGER')")
    @PostMapping("/main/{eventID}/setEventStatus")
    public String setEventStatus(@PathVariable int eventID, @RequestParam String changeStatus,
            @RequestParam(required = false) String message) {
        Event event = eventService.getEventByID(eventID);
        LOG.info(String.format("User \"%s\" changes event [%s] status: \"%s\" -> \"%s\"", Utils.getUserFromContext().getLogin(), event, event.getStatus().getName(), changeStatus));
        eventService.setStatus(event, EventStatusEnum.valueOf(changeStatus));
        if (changeStatus.equals("APPROVED") || changeStatus.equals("REJECTED"))
            eventService.setMessage(event, message);
        return "redirect:/main/" + eventID;
    }

    @PreAuthorize("hasAuthority('COORDINATOR')")
    @PostMapping("/main/{eventID}/coordinate")
    public String coordinateEvent(@PathVariable int eventID,
            @RequestParam(required = false, defaultValue = "false") boolean drop) {
        User user = Utils.getUserFromContext();
        Event event = eventService.getEventByID(eventID);
        if (!drop) {
            LOG.info(String.format("Setting a coordinator for event [%s]: %s", event, user.getLogin()));
            eventService.setCoordinator(event, user);
            eventService.setStatus(event, EventStatusEnum.COORDINATED);
        } else {
            LOG.info(String.format("Dropping a coordinator of event [%s], decided by %s", event, user.getLogin()));
            eventService.setCoordinator(event, null);
            eventService.setStatus(event, EventStatusEnum.APPROVED);
        }
        return "redirect:/main/" + eventID;
    }

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'ORGANISER')")
    @PostMapping("/main/{eventID}/edit")
    public String editEvent(@PathVariable int eventID, Model model, @RequestParam String name,
            @RequestParam String description, @RequestParam String place, @RequestParam String dateStart,
            @RequestParam String dateFinish) {
        Event event = eventService.getEventByID(eventID);
        event.setName(name);
        event.setDescription(description);
        event.setPlace(place);
        event.setDateStart(dateStart);
        event.setDateFinish(dateFinish);
        eventService.updateEventInformation(event);
        LOG.info(String.format("User \"%s\" edits information of the event [%s]", Utils.getUserFromContext().getLogin(), event));
        return "redirect:/main/" + eventID;
    }

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'ORGANISER')")
    @PostMapping("/main/{eventID}")
    public String addVolunteerFunction(@PathVariable int eventID, @RequestParam String name,
            @RequestParam String description, @RequestParam String requirements, @RequestParam String timeStart,
            @RequestParam String timeFinish, @RequestParam int numberNeeded) {
        Event currentEvent = eventService.getEventByID(eventID);
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        VolunteerFunction vf = vfs.addVolunteerFunction(currentEvent, name, description, requirements, timeStart, timeFinish, numberNeeded);
        LOG.info(String.format("User \"%s\" adds volunteer function [%s] for event [%s]", Utils.getUserFromContext().getLogin(), vf, currentEvent));
        return "redirect:/main/" + eventID;
    }

    @GetMapping("/volunteerFunction/{volunteerFunctionID}")
    public String getVolunteerFunction(@PathVariable int volunteerFunctionID, Model model) {
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        VolunteerFunction volunteerFunction = vfs.getVolunteerFunctionByID(volunteerFunctionID);
        model.addAttribute("volunteerFunction", volunteerFunction);
        model.addAttribute("user", Utils.getUserFromContext());
        return "currentVolunteerFunction";
    }

    @PostMapping("/volunteerFunction/{volunteerFunctionID}/edit")
    public String editVolunteerFunction(@PathVariable int volunteerFunctionID, Model model, @RequestParam String name,
            @RequestParam String description, @RequestParam String requirements, @RequestParam String timeStart,
            @RequestParam String timeFinish, @RequestParam int numberNeeded) {
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        VolunteerFunction volunteerFunction = vfs.getVolunteerFunctionByID(volunteerFunctionID);
        volunteerFunction.setName(name);
        volunteerFunction.setDescription(description);
        volunteerFunction.setRequirements(requirements);
        volunteerFunction.setTimeStart(timeStart);
        volunteerFunction.setTimeFinish(timeFinish);
        volunteerFunction.setNumberNeeded(numberNeeded);
        vfs.updateVolunteerFunctionInformation(volunteerFunction);
        LOG.info(String.format("User \"%s\" edits information of the volunteerFunction [%s]", Utils.getUserFromContext().getLogin(), volunteerFunction));
        return "redirect:/volunteerFunction/" + volunteerFunctionID;
    }
}