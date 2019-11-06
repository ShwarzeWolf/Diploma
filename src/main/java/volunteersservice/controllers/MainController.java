package volunteersservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model){
        return "main";
    }
}
