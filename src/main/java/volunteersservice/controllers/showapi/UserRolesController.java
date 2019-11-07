package volunteersservice.controllers.showapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testapi")
public class UserRolesController {
    @GetMapping(path = "/event/{id}/signup")
    public String addUserToRole() {
        return null;
    }
}