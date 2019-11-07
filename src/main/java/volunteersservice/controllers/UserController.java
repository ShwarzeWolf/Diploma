package volunteersservice.controllers;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import volunteersservice.models.enums.UserTypeEnum;
import volunteersservice.services.UserManager;
import volunteersservice.utils.ManagerFactory;

@Controller
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class);
    private UserManager users;

    public UserController() {
        super();
        users = ManagerFactory.getUserManager();
    }

    @PostMapping(path = "/registration")
    public String addNewUser(@RequestParam String email, @RequestParam String name,
                      @RequestParam String login, @RequestParam String password, @RequestParam String userType,
                      Model model) {
        if (users.addUser(email, login, name, password, UserTypeEnum.valueOf(userType))) {
            log.info("Зарегистрировались");
            return ("redirect:/login");
        }
//            return "User <b>" + name + "</b> with password <i>" + password + "</i> was successfully saved";
        else {
            log.info("Не получилась регистрация");
            return "registration";
        }
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }


}
