package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import volunteersservice.models.entities.User;
import volunteersservice.models.enums.UserRoleEnum;
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
    public String goToAcc() {
        return "personal_account";
    }
}
