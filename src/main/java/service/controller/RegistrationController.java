package service.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import service.model.Role;
import service.model.User;
import service.repos.UserRepository;
import java.util.Collections;

@Controller
public class RegistrationController {
    private static Logger log = Logger.getLogger(RegistrationController.class);
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        User userFromDb = userRepository.findByLogin(user.getLogin());

        if (userFromDb != null) {
//            model.addAttribute("message", "User exists!");
            log.info("Пользователь уже существует: " + userFromDb);
            return "registration";
        }

        user.setUsername("ok");
        user.setEmail("ok@");
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        log.info("Зарегистрировали нового пользователя: " + user.getLogin() + " " + user.getPassword());

        userRepository.save(user);

        return "redirect:/login";
    }
}
