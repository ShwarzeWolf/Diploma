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
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.ReportService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class EventController {
    private final static Logger LOG = Logger.getLogger(EventController.class);
    private final EventService eventService = ServiceFactory.getEventService();
    private final VolunteerFunctionService volunteerFunctionService = ServiceFactory.getVolunteerFunctionService();

    @GetMapping({"/main", "/"})
    public String getLandingPage() { return "main"; }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events")
    public String viewAllEvents(Model model) {
        User user = Utils.getUserFromContext();

        switch (user.getUserRole().getName()) {
            case "ORGANISER":
                model.addAttribute("uncheckedEvents", eventService.getEventsByStatusOrganisedByUser(user, EventStatusEnum.CREATED));
                model.addAttribute("inProcessEvents", eventService.getOrganiserInProcessEvents(user));
                model.addAttribute("finishedEvents", eventService.getOrganiserExpiredEvents(user));
                model.addAttribute("deniedEvents", eventService.getEventsByStatusOrganisedByUser(user, EventStatusEnum.DENIED));
                return "OrganisersEventPool";

            case "MANAGER":
                model.addAttribute("uncheckedEvents", eventService.getEventsByStatus(EventStatusEnum.UNCHECKED));
                model.addAttribute("approvedEvents", eventService.getEventsByStatus(EventStatusEnum.APPROVED));
                model.addAttribute("coordinatedEvents", eventService.getEventsByStatus(EventStatusEnum.ASSIGNED));
                model.addAttribute("endedEvents", eventService.getEventsByStatus(EventStatusEnum.FINISHED));
                model.addAttribute("deniedEvents", eventService.getEventsByStatus(EventStatusEnum.DENIED));

                return "ManagersEventPool";

            case "MOVEMENTLEADER":
                model.addAttribute("approvedEvents", eventService.getEventsByStatus(EventStatusEnum.APPROVED));
                model.addAttribute("coordinatedEvents", eventService.getEventsByStatus(EventStatusEnum.ASSIGNED));
                model.addAttribute("endedEvents", eventService.getEventsByStatus(EventStatusEnum.FINISHED));
                model.addAttribute("deniedEvents", eventService.getEventsByStatus(EventStatusEnum.DENIED));

                return "MovementLeaderEventPool";
            case "COORDINATOR":
                model.addAttribute("approvedEvents", eventService.getEventsByStatus(EventStatusEnum.APPROVED));
                model.addAttribute("coordinatedEvents", eventService.getEventsByStatusCoordinatedByUser(user, EventStatusEnum.ASSIGNED ));
                model.addAttribute("endedEvents", eventService.getEventsByStatusCoordinatedByUser(user, EventStatusEnum.FINISHED));

                return "CoordinatorsEventPool";

            default:
                return "403";
        }
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER')")
    @GetMapping("/addEvent")
    public String getAddEventPage() { return "addEventForm"; }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER')")
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

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}")
    public String getEventByID(@PathVariable int eventID,
                               Model model) {
        ReportService reportService = ServiceFactory.getFirstPartReportService();
        Event event = eventService.getEventByID(eventID);

        model.addAttribute("event", event);
        model.addAttribute("user", Utils.getUserFromContext());
        model.addAttribute("volunteerFunctions", volunteerFunctionService.getVolunteerFunctionsByEvent(event));
        model.addAttribute("report", reportService.getFirstPartByEvent(event));

        return "currentEvent";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/edit")
    public String getEditEventPage(@PathVariable int eventID,
                                Model model){

        Event event = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        boolean userIsOrganiser = event.getOrganiser().getLogin().equals(user.getLogin());
        boolean eventCanBeChangedByOrganiser = event.getStatus().getName().equals("CREATED");
        boolean userIsExecutiveCoordinator = event.getCoordinator() != null && event.getCoordinator().equals(user);

        if (userIsOrganiser && eventCanBeChangedByOrganiser || userIsExecutiveCoordinator)  {

            model.addAttribute("event", event);
            model.addAttribute("user", user);

            return "editEvent";
        }

        return "403";
    };


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
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



    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/setEventStatus")
    public String setEventStatus(@PathVariable int eventID,
                                 @RequestParam String changeStatus,
                                 @RequestParam(required = false) String message) {
        Event event = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        eventService.setStatus(event, EventStatusEnum.valueOf(changeStatus));

         if (changeStatus.equals("APPROVED") || changeStatus.equals("DENIED")) {
             eventService.setMessage(event, message);
             eventService.setManager(event, user);
         }

         if (changeStatus.equals("ASSIGNED")){
             eventService.setCoordinator(event, user);
         }

        LOG.info(String.format("User \"%s\" changes event [%s] status: \"%s\" -> \"%s\"", Utils.getUserFromContext().getLogin(), event, event.getStatus().getName(), changeStatus));
        return "redirect:/events/" + eventID;
    }

    @PreAuthorize("hasAuthority('ORGANISER')")
    @PostMapping("/events/{eventID}/delete")
    public String deleteEvent(@PathVariable int eventID) {
        User user = Utils.getUserFromContext();
        Event event = eventService.getEventByID(eventID);

        boolean userIsOrganiser = event.getOrganiser().getLogin().equals(user.getLogin());
        boolean eventCanBeDeleted = event.getStatus().getName().equals("CREATED");

        if (userIsOrganiser && eventCanBeDeleted) {
            eventService.deleteEvent(event);
        }

        return "redirect:/events/";
    }

}