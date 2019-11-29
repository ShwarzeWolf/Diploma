package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteersservice.models.entities.User;
import volunteersservice.models.enums.UserRoleEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.UserService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class UserController {
    private static Logger LOG = Logger.getLogger(UserController.class);
    private UserService users;

    public UserController() {
        users = ServiceFactory.getUserService();
    }

    @PostMapping("/registration")
    public String addNewUser(@RequestParam String email, @RequestParam String name, @RequestParam String login,
            @RequestParam String password, @RequestParam String userRole) {
        if (users.addUser(email, login, name, password, UserRoleEnum.valueOf(userRole))) {
            LOG.info("Registered okay");
            return "login";
        } else {
            LOG.info("Registration failed");
            return "registration";
        }
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        LOG.info("User " + Utils.getUserFromContext() + " came to /register");
        model.addAttribute("roleName",
                Utils.getUserFromContext() == null ? null : Utils.getUserFromContext().getUserRole().getName()); // fixme #userRole
        return "registration";
    }

    @GetMapping("/personal_account")
    public String personalPage(Model model) {
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
        return "personal_account";
    }
}
