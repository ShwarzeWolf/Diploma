package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import volunteersservice.models.entities.User;
import volunteersservice.services.EventService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class MainController {
    private final static Logger LOG = Logger.getLogger(EventController.class);
    private final EventService eventService = ServiceFactory.getEventService();

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }


    @PreAuthorize("hasAnyAuthority('ORGANISER')")
    @GetMapping({"/listOfMyEvents", "/"})
    public String myEventPool(Model model) {
        User user = Utils.getUserFromContext();

        String str = new String(user.getName() + " : " + user.getUserRole().getName());
        model.addAttribute("name_and_role", str);

        model.addAttribute("currentEvents", eventService.getActiveEventsOfOrganiser(user));
        model.addAttribute("expiredEvents", eventService.getExpiredEventsOfOrganiser(user));
        model.addAttribute("advanced", true);

        return "myEventPool";
    }

}
