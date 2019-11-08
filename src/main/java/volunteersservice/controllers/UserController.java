package volunteersservice.controllers;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import volunteersservice.models.enums.UserRoleEnum;
import volunteersservice.services.UserService;
import volunteersservice.utils.ServiceFactory;

@Controller
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class);
    private UserService users;

    public UserController() {
        super();
        users = ServiceFactory.getUserService();
    }

    @PostMapping("/registration")
    public @ResponseBody String addNewUser(@RequestParam String email, @RequestParam String name,
                                           @RequestParam String login, @RequestParam String password, @RequestParam String userRole,
                                           Model model) {
        if (users.addUser(email, login, name, password, UserRoleEnum.valueOf(userRole))){
            log.info("Зарегистрировались");
            return "redirect:/login";
        }
        else{
            log.info("Не получилась регистрация");
                return "registration";
        }
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


}
