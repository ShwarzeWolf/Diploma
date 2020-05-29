package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private static final Logger LOG = Logger.getLogger(UserController.class);
    private final UserService users = ServiceFactory.getUserService();

    @PostMapping("/registration")
    public String addUser(@RequestParam String email,
                          @RequestParam String name,
                          @RequestParam String surname,
                          @RequestParam String login,
                          @RequestParam String password,
                          @RequestParam String contactPhone,
                          @RequestParam (required = false) String userRole) {
        User currentUser = Utils.getUserFromContext();

        boolean userIsManager = currentUser != null && currentUser.getUserRole().getName().equals("MANAGER");

        if (userIsManager){
            return users.addUser(email, login, name, surname, password, UserRoleEnum.valueOf(userRole), contactPhone) ? "login" : "registration";
        } else {
            return users.addUser(email, login, name, surname, password, UserRoleEnum.ORGANISER, contactPhone) ? "login" : "registration";
        }
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        User user = Utils.getUserFromContext();

        return user != null && user.getUserRole().getName().equals("MANAGER") ? "TNLTeamRegistration" : "registration";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginError", false);
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/personal_account")
    public String getPersonalAccount(Model model) {
        User user = Utils.getUserFromContext();
        model.addAttribute("person", user);
        return "personal_account";
    }

    @PostMapping("/changePassword")
    public String changePassword(Model model, 
                                 RedirectAttributes redirectAttributes,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword1,
                                 @RequestParam String newPassword2) {
        User user = Utils.getUserFromContext();
        String answer;
        if (users.changePassword(user.getLogin(), oldPassword, newPassword1, newPassword2)) {
            answer = "Password changed successfully";
            LOG.info(String.format("User \"%s\" changed password successfully", user));
        } else {
            answer = "Password has not been changed";
        }
        model.addAttribute("person", user);
        redirectAttributes.addFlashAttribute("password_change_status", answer);
        return "redirect:/personal_account";
    }
}
