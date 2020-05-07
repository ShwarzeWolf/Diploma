package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import volunteersservice.models.entities.User;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class MainController {
    private final static Logger LOG = Logger.getLogger(EventController.class);
    private final EventService eventService = ServiceFactory.getEventService();

    @GetMapping({"/main", "/"})
    public String mainPage() {
        return "main";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR')")
    @GetMapping("/events")
    public String myEventPool(Model model) {
        User user = Utils.getUserFromContext();
        model.addAttribute("name", user.getName() + " " + user.getSurname());

        switch (user.getUserRole().getName()) {
            case "ORGANISER":
                model.addAttribute("uncheckedEvents", eventService.getEventsByStatusOrganisedByUser(user, EventStatusEnum.CREATED));
                //model.addAttribute("inProcessEvents", eventService.getOrganiserActiveEvents(user));
                //model.addAttribute("finishedEvents", eventService.getOrganiserExpiredEvents(user));

                return "OrganisersEventPool";

            case "MANAGER":
                model.addAttribute("uncheckedEvents", eventService.getEventsByStatus(EventStatusEnum.UNCHECKED));
                model.addAttribute("approvedEvents", eventService.getEventsByStatus(EventStatusEnum.APPROVED));
                model.addAttribute("coordinatedEvents", eventService.getEventsByStatus(EventStatusEnum.ASSIGNED));
                model.addAttribute("endedEvents", eventService.getEventsByStatus(EventStatusEnum.FINISHED));
                model.addAttribute("deniedEvents", eventService.getEventsByStatus(EventStatusEnum.DENIED));

                return "ManagersEventPool";

            case "COORDINATOR":
                model.addAttribute("approvedEvents", eventService.getEventsByStatus(EventStatusEnum.APPROVED));
                model.addAttribute("coordinatedEvents", eventService.getEventsByStatusCoordinatedByUser(user, EventStatusEnum.ASSIGNED ));
                model.addAttribute("endedEvents", eventService.getEventsByStatusCoordinatedByUser(user, EventStatusEnum.FINISHED));

                return "CoordinatorsEventPool";

            default:
                return "403";
        }
    }
}
