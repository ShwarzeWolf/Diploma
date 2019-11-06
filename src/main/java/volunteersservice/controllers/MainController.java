package volunteersservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import volunteersservice.models.Messages;
import volunteersservice.repositories.test.MessageRepo;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @PostMapping("/testing")
    public String addNewUser(@RequestParam String text, @RequestParam String tag, Model model){
        Messages n = new Messages();
        n.setText(text);
        n.setTag(tag);
        messageRepo.save(n);

        Iterable<Messages> messages =  messageRepo.findAll();
        model.addAttribute("textforms", messages);
        return "main";
    }
}
