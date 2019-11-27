package volunteersservice.controllers.showapi;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/showapi")
public class ShowMainController {

    @GetMapping({ "/", "" })
    public String mainPage() {
        return "showapi/simpleMainPage";
    }

    @PostMapping("/")
    public String mainPagePOST(@RequestParam String jumpWhat, @RequestParam String id, @RequestParam String action) {
        if (jumpWhat.equals("event")) {
            if (action.equals("Jump to event")) {
                return "redirect:/showapi/event" + id;
            } else {
                return "redirect:/showapi/event/" + id + "/addVolunteerFunction";
            }
        } else {
            return "redirect:/showapi/volunteerFunction/" + id;
        }
    }

    @GetMapping("/home")
    public @ResponseBody String homePageUser() {
        String res = "";
        GrantedAuthority authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .findAny().get();
        if (!authority.getAuthority().equals("ROLE_ANONYMOUS"))
            res += "You're logged in as <b>" + authority.getAuthority() + "</b>";
        else
            res += "You're logged out";
        return res;
    }
}