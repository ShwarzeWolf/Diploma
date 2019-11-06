package volunteersservice.controllers.showapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import volunteersservice.models.enums.UserTypeEnum;
import volunteersservice.services.UserManager;
import volunteersservice.utils.ManagerFactory;

@Controller
@RequestMapping("/testapi")
public class UserController {
    private UserManager users;

    public UserController() {
        super();
        users = ManagerFactory.getUserManager();
    }

    @PostMapping(path = "/register")
    public @ResponseBody String addNewUser(@RequestParam String email, @RequestParam String name,
            @RequestParam String login, @RequestParam String password, @RequestParam String userType) {
        if (users.addUser(email, login, name, password, UserTypeEnum.valueOf(userType)))
            return "User <b>" + name + "</b> with password <i>" + password + "</i> was successfully saved";
        else
            return "Error happened";
    }

    @GetMapping(path = "/register")
    public String registerPage() {
        return "testapi/simpleRegisterForm";
    }

    // @PostMapping(path = "/login")
    // public @ResponseBody String loginIntoSystem(@RequestParam String email, @RequestParam String password) {
    //     if (users.emailPasswordOkay(email, password)) {
    //         User logged = users.getUserByEmail(email);
    //         return "authorisation by email was successful\nLogged in as <b>" + logged.getName() + "</b><br>FullInfo: " + logged;
    //     } else if (users.loginPasswordOkay(email, password)) {
    //         User logged = users.getUserByLogin(email);
    //         return "authorisation by login was successful\nLogged in as <b>" + logged.getName() + "</b><br>FullInfo: " + logged;
    //     } else
    //         return "authorisation failed";
    // }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "testapi/simpleLoginForm";
    }
}
