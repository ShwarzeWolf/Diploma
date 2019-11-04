package volunteersservice.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping(path = "/")
    public String mainPage() {
        return "simpleMainPage.html";
    }

    @PostMapping(path = "/")
    public void mainPagePOST(@RequestParam String eventID, @RequestParam String action, HttpServletResponse httpServletResponse) {
        if (action.equals("Jump to event")) {
            httpServletResponse.setHeader("Location", "/event/" + eventID);
            httpServletResponse.setStatus(302);
        } else {
            httpServletResponse.setHeader("Location", "/event/" + eventID + "/addRole");
            httpServletResponse.setStatus(302);
        }
    }
}