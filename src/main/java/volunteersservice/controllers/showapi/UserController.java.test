package volunteersservice.controllers.showapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import volunteersservice.models.entities.User;
import volunteersservice.models.enums.UserRoleEnum;
import volunteersservice.services.UserService;
import volunteersservice.utils.ServiceFactory;

@Controller
@RequestMapping("/testapi")
public class UserController {
    private UserService users;

    public UserController() {
        super();
        users = ServiceFactory.getUserService();
    }

    @PostMapping("/register")
    public @ResponseBody String addNewUser(@RequestParam String email, @RequestParam String name,
            @RequestParam String login, @RequestParam String password, @RequestParam String userRole) {
        if (users.addUser(email, login, name, password, UserRoleEnum.valueOf(userRole)))
            return "User <b>" + name + "</b> with password <i>" + password + "</i> was successfully saved";
        else
            return "Error happened";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "testapi/simpleRegisterForm";
    }

    @GetMapping("/users")
    public @ResponseBody String getUsers() {
        StringBuilder sb = new StringBuilder();
        for (User user : users.getUsers()) {
            sb.append(user.toString());
            sb.append("<br>");
        }
        return sb.toString();
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

    @GetMapping("/login")
    public String loginPage() {
        return "testapi/simpleLoginForm";
    }
}
