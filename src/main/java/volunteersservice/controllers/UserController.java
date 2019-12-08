package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String addNewUser(@RequestParam String email,
                             @RequestParam String name,
                             @RequestParam String login,
                             @RequestParam String password,
                             @RequestParam String userRole) {
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

    @GetMapping({"/personal_account"})
    public String getPersonalAccount(Model model) {
        User user = Utils.getUserFromContext();
        model.addAttribute("person", user);
        return "personal_account";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, RedirectAttributes redirectAttributes,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword1,
                                 @RequestParam String newPassword2){
        User user = Utils.getUserFromContext();
        String answer = "";
        if (users.changePassword(user.getLogin(), oldPassword, newPassword1, newPassword2))
            answer = "Password changed successfully";
        else
            answer = "Password has not been changed";
        model.addAttribute("person", user);
        redirectAttributes.addFlashAttribute("password_change_status", answer);
        return "redirect:/personal_account";
    }
}
