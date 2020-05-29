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
import volunteersservice.services.EventService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class VolunteerFunctionController {
    private final static Logger LOG = Logger.getLogger(EventController.class);
    private final EventService eventService = ServiceFactory.getEventService();
    private final VolunteerFunctionService volunteerFunctionService = ServiceFactory.getVolunteerFunctionService();


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}")
    public String addVolunteerFunction(@PathVariable int eventID,
                                       @RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam String requirements,
                                       @RequestParam String timeStart,
                                       @RequestParam String timeFinish,
                                       @RequestParam int numberNeeded) {
        Event currentEvent = eventService.getEventByID(eventID);
        volunteerFunctionService.addVolunteerFunction(currentEvent, name, description, requirements, timeStart, timeFinish, numberNeeded);

        return "redirect:/events/" + eventID;
    }


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/volunteerFunctions/{volunteerFunctionID}/edit")
    public String getEditVolunteerFunctionPage(@PathVariable int volunteerFunctionID,
                                               @PathVariable int eventID,
                                               Model model) {
        Event event = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        boolean userIsOrganiser = event.getOrganiser().getLogin().equals(user.getLogin());
        boolean eventCanBeChangedByOrganiser = event.getStatus().getName().equals("CREATED");
        boolean userIsExecutiveCoordinator = event.getCoordinator() != null && event.getCoordinator().equals(user);

        if (userIsOrganiser && eventCanBeChangedByOrganiser || userIsExecutiveCoordinator) {

            model.addAttribute("volunteerFunction", volunteerFunctionService.getVolunteerFunctionByID(volunteerFunctionID));
            model.addAttribute("user", user);

            return "editVolunteerFunction";
        }

        return "403";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
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

        VolunteerFunction volunteerFunction = volunteerFunctionService.getVolunteerFunctionByID(volunteerFunctionID);

        volunteerFunction.setName(name);
        volunteerFunction.setDescription(description);
        volunteerFunction.setRequirements(requirements);
        volunteerFunction.setTimeStart(timeStart);
        volunteerFunction.setTimeFinish(timeFinish);
        volunteerFunction.setNumberNeeded(numberNeeded);

        volunteerFunctionService.updateVolunteerFunctionInformation(volunteerFunction);

        LOG.info(String.format("User \"%s\" edits information of the volunteerFunction [%s]", Utils.getUserFromContext().getLogin(), volunteerFunction));
        return "redirect:/events/" + eventID;
    }


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/volunteerFunctions/{volunteerFunctionID}/delete")
    public String deleteVolunteerFunction(@PathVariable int eventID,
                                          @PathVariable int volunteerFunctionID){
        User user = Utils.getUserFromContext();
        Event event = eventService.getEventByID(eventID);

        boolean userIsOrganiser = event.getOrganiser().getLogin().equals(user.getLogin());
        boolean eventCanBeChangedByOrganiser = event.getStatus().getName().equals("CREATED");
        boolean userIsExecutiveCoordinator = event.getCoordinator() != null && event.getCoordinator().equals(user);

        if (userIsOrganiser && eventCanBeChangedByOrganiser || userIsExecutiveCoordinator) {
            volunteerFunctionService.deleteVolunteerFunctionByID(volunteerFunctionID);
        }

        return "redirect:/events/" + eventID;
    }
}
