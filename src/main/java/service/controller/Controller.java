package service.controller;


import java.nio.file.FileSystems;
import java.nio.file.Files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import service.businesslogic.UserTypeManager;
import service.businesslogic.UserManager;
import service.dal.models.User;

@RestController
public class Controller {
    private final static Logger LOG = LogManager.getLogger(UserManager.class.getName());
    private UserManager users;

    public Controller() {
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
        try {
                return new String(Files.readAllBytes(FileSystems.getDefault().getPath("src/main/resources/templates", "simpleRegisterForm.html")));
        } catch (Exception ex) {
            LOG.error(ex);
            return "simpleRegisterForm: use post-request";
        }
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
        try {
                return new String(Files.readAllBytes(FileSystems.getDefault().getPath("src/main/resources/templates", "simpleLoginForm.html")));
        } catch (Exception ex) {
            LOG.error(ex);
            return "simpleLoginForm: use post-request";
        }
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public @ResponseBody String loginIntoSystem() {
        return "<a href=\"/register\">Register</a><br><a href=\"/login\">Login</a>";
    }
}
