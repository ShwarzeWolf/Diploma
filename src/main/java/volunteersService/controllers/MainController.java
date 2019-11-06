package volunteersService.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import volunteersService.utils.Utils;

@RestController
public class MainController {
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public @ResponseBody String mainPage() {
        return Utils.getStaticFileContents("src/main/resources/templates", "simpleMainPage.html",
                "%Main page get error%");
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public void mainPagePOST(@RequestParam String eventID, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/event/" + eventID);
        httpServletResponse.setStatus(302);
    }
}