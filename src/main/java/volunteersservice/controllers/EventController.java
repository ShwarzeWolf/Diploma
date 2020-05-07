package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
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
        User user = Utils.getUserFromContext();

        String name = user.getName().concat(" ").concat(user.getSurname());
        String role = user.getUserRole().getName();

        model.addAttribute("name_and_role", name.concat(":").concat(role));

        return "addEventForm";
    }

    @PreAuthorize("hasAuthority('ORGANISER')")
    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name,
                           @RequestParam String place,
                           @RequestParam String description,
                           @RequestParam String dateStart,
                           @RequestParam String dateFinish,
                           @RequestParam (defaultValue = "") String requirements,
                           @RequestParam (defaultValue = "") String clothesType,
                           @RequestParam (defaultValue = "") String accommodation,
                           @RequestParam (defaultValue = "") String food) {

        Event ev = eventService.addEvent(name, Utils.getUserFromContext(), description, place, dateStart, dateFinish, requirements, clothesType, accommodation, food);

        LOG.info(String.format("User \"%s\" added event %s", Utils.getUserFromContext().getLogin(), ev));
        return "redirect:/events/" + ev.getEventID();
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR')")
    @GetMapping("/events/{eventID}")
    public String getEventByID(@PathVariable int eventID,
                               Model model) {

        Event currentEvent = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        model.addAttribute("roleName", user.getUserRole().getName());
        model.addAttribute("event", currentEvent);
        model.addAttribute("user", Utils.getUserFromContext());

        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        model.addAttribute("volunteerFunctions", vfs.getVolunteerFunctions(currentEvent));

        return "currentEvent";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR')")
    @GetMapping("/events/{eventID}/edit")
    public String editEventPage(@PathVariable int eventID,
                                Model model){

        Event currentEvent = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        if (currentEvent.getOrganiser().getName().equals(user.getName())
                && currentEvent.getStatus().getName().equals("CREATED")
                || currentEvent.getCoordinator().equals(user)) {

            model.addAttribute("roleName", user.getUserRole().getName());
            model.addAttribute("event", currentEvent);
            model.addAttribute("user", Utils.getUserFromContext());

            return "editEvent";
        }

        return "403";
    };


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR')")
    @PostMapping("/events/{eventID}/edit")
    public String editEvent(@PathVariable int eventID,
                            Model model,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam String place,
                            @RequestParam String dateStart,
                            @RequestParam String dateFinish,
                            @RequestParam String requirements,
                            @RequestParam String clothesType,
                            @RequestParam String accommodation,
                            @RequestParam String food) {
        Event event = eventService.getEventByID(eventID);

        event.setName(name);
        event.setDescription(description);
        event.setPlace(place);
        event.setDateStart(dateStart);
        event.setDateFinish(dateFinish);
        event.setAccommodation(accommodation);
        event.setClothesType(clothesType);
        event.setRequirements(requirements);
        event.setFood(food);

        eventService.updateEventInformation(event);

        LOG.info(String.format("User \"%s\" edits information of the event [%s]", Utils.getUserFromContext().getLogin(), event));
        return "redirect:/events/" + eventID;
    }


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR')")
    @PostMapping("/events/{eventID}")
    public String addVolunteerFunction(@PathVariable int eventID,
                                       @RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam String requirements,
                                       @RequestParam String timeStart,
                                       @RequestParam String timeFinish,
                                       @RequestParam int numberNeeded) {
        Event currentEvent = eventService.getEventByID(eventID);

        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        VolunteerFunction vf = vfs.addVolunteerFunction(currentEvent, name, description, requirements, timeStart, timeFinish, numberNeeded);

        LOG.info(String.format("User \"%s\" adds volunteer function [%s] for event [%s]", Utils.getUserFromContext().getLogin(), vf, currentEvent));

        return "redirect:/events/" + eventID;
    }


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR')")
    @GetMapping("/events/{eventID}/volunteerFunctions/{volunteerFunctionID}/edit")
    public String editVolunteerFunction(@PathVariable int volunteerFunctionID,
                                       @PathVariable int eventID,
                                       Model model) {
        Event currentEvent = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        if (currentEvent.getOrganiser().getName().equals(user.getName()) && currentEvent.getStatus().getName().equals("CREATED")
                || currentEvent.getCoordinator()!= null && currentEvent.getCoordinator().getLogin() == user.getLogin())
        {
            VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
            VolunteerFunction volunteerFunction = vfs.getVolunteerFunctionByID(volunteerFunctionID);

            model.addAttribute("volunteerFunction", volunteerFunction);
            model.addAttribute("user", Utils.getUserFromContext());

            return "editVolunteerFunction";
        }

        return "403";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR')")
    @PostMapping("/events/{eventID}/volunteerFunctions/{volunteerFunctionID}/edit")
    public String editVolunteerFunction(@PathVariable int volunteerFunctionID,
                                        @PathVariable int eventID,
                                        @RequestParam String name,
                                        @RequestParam String description,
                                        @RequestParam String requirements,
                                        @RequestParam String timeStart,
                                        @RequestParam String timeFinish,
                                        @RequestParam int numberNeeded,
                                        Model model) {
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
        return "redirect:/events/" + eventID;
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR')")
    @PostMapping("/events/{eventID}/setEventStatus")
    public String setEventStatus(@PathVariable int eventID,
                                 @RequestParam String changeStatus,
                                 @RequestParam(required = false) String message) {
        Event event = eventService.getEventByID(eventID);

        eventService.setStatus(event, EventStatusEnum.valueOf(changeStatus));
        LOG.info(String.format("User \"%s\" changes event [%s] status: \"%s\" -> \"%s\"", Utils.getUserFromContext().getLogin(), event, event.getStatus().getName(), changeStatus));

         if (changeStatus.equals("APPROVED") || changeStatus.equals("DENIED"))
             eventService.setMessage(event, message);

         return "redirect:/events/";
    }

    @PreAuthorize("hasAuthority('COORDINATOR')")
    @PostMapping("/events/{eventID}/coordinate")
    public String coordinateEvent(@PathVariable int eventID) {
        User user = Utils.getUserFromContext();
        Event event = eventService.getEventByID(eventID);

        eventService.setCoordinator(event, user);
        eventService.setStatus(event, EventStatusEnum.ASSIGNED);

        LOG.info(String.format("Setting a coordinator for event [%s]: %s", event, user.getLogin()));

        return "redirect:/events/" + eventID;
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
        return "ManagersEventPool";
    }
}