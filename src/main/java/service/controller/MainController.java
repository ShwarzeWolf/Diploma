package service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
//    private UsersManager users;

//    public Controller() {
//        super();
//        try {
//            users = new UsersManager();
//        } catch (BLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping(path="/register", method = RequestMethod.GET)
//    public @ResponseBody String addNewUser (@RequestParam String email,
//                                            @RequestParam String name,
//                                            @RequestParam String password) {
//        try {
//            users.addUser(name, email, password);
//            return "User with name " + name + " with password " + password + " was successfully saved" ;
//        } catch (BLException ex) {
//            // ex.printStackTrace();
//            throw new RuntimeException(ex);
//            // return "User addition failed";
//        }
//    }
//
//    @RequestMapping(path="/login", method = RequestMethod.GET)
//    public @ResponseBody String loginIntoSystem(@RequestParam String email,
//                                                @RequestParam String password) {
//        if (users.passwordOkay(email, password))
//            return "authorisation was successful";
//        else
//            return "authorisation failed";
//    }

//    @RequestMapping(path="/", method = RequestMethod.GET)
//    public @ResponseBody String loginIntoSystem() {
//        return "<a href=\"/refister\">Register</a><br><a href=\"/login\">Login</a>";
//    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String printHello(Model model) {
        model.addAttribute("message", "Hello Spring MVC Framework!");
        return "hello";
    }
}
