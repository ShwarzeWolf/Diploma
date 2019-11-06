package volunteersservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController1 {
//    @GetMapping({"/", "/main"})
//    public String greeting(Model model) {
//        return "main";
//    }

    @GetMapping({"/main"})
    public String greeting(Model model) {
        return "main";
    }

    @GetMapping({"/"})
    public String main(Model model) {
        return "greeting";
    }


}
