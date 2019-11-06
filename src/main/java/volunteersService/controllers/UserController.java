package volunteersService.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import volunteersService.services.UserManager;
import volunteersService.services.UserTypeManager;
import volunteersService.models.User;
import volunteersService.utils.Utils;

@RestController
public class UserController {
    private UserManager users;

    public UserController() {
        super();
        users = new UserManager();
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public @ResponseBody String addNewUser(@RequestParam String email, @RequestParam String name,
            @RequestParam String login, @RequestParam String password, @RequestParam String userType) {
        users.addUser(email, login, name, password, UserTypeManager.USER_TYPE.valueOf(userType));
        return "User <b>" + name + "</b> with password <i>" + password + "</i> was successfully saved";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public @ResponseBody String registerPage() {
        return Utils.getStaticFileContents("src/main/resources/templates", "simpleRegisterForm.html",
                "simpleRegisterForm: use post-request");
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public @ResponseBody String loginIntoSystem(@RequestParam String email, @RequestParam String password) {
        if (users.passwordOkay(email, password)) {
            User logged = users.getUserByEmail(email);
            return "authorisation was successful\nLogged in as <b>" + logged.getName() + "</b><br>FullInfo: " + logged;
        } else
            return "authorisation failed";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public @ResponseBody String loginPage() {
        return Utils.getStaticFileContents("src/main/resources/templates", "simpleLoginForm.html",
                "simpleLoginForm: use post-request");
    }
}
