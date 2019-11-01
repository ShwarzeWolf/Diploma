package service.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.model.Role;
import service.model.User;
import service.repos.UserRepository;

import java.util.Collections;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Controller
public class RegistrationController {
    private static Logger log = Logger.getLogger(RegistrationController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

//    @PostMapping("/registration")
//    public String addUser(User user, Model model) {
//        User userFromDb = userRepository.findByUsername(user.getUsername());
//
//        if (userFromDb != null) {
//            model.addAttribute("message", "User exists!");
//            return "registration";
//        }
//        log.info("registration, parametrs: " +  user);
//
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        userRepository.save(user);
//
//        return "redirect:/login";
//    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = new User(username, password);
        log.info("registration, parametrs: " +  username + " " + password);

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
