package volunteersservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRolesController {
    @GetMapping(path = "/event/{id}/signup")
    public String addUserToRole() {
        return null;
    }
}