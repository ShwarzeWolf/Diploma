package volunteersservice.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "simpleMainPage.html";
    }

    @PostMapping("/")
    public void mainPagePOST(@RequestParam String eventID, @RequestParam String action, HttpServletResponse httpServletResponse) {
        if (action.equals("Jump to event")) {
            httpServletResponse.setHeader("Location", "/event/" + eventID);
            httpServletResponse.setStatus(302);
        } else {
            httpServletResponse.setHeader("Location", "/event/" + eventID + "/addRole");
            httpServletResponse.setStatus(302);
        }
    }

    @GetMapping("/home")
    public @ResponseBody String homePageUser() {
        String res = "";
        GrantedAuthority authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findAny().get();
        //res = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString() + "<br>";
        if (!authority.getAuthority().equals("ROLE_ANONYMOUS"))
            res += "You're logged in as <b>" + authority.getAuthority() + "</b>";
        else
            res += "You're logged out";
        return res;
    }
}