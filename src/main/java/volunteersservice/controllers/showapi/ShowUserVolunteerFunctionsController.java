package volunteersservice.controllers.showapi;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.services.UserService;
import volunteersservice.services.UserVolunteerFunctionService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;

@Controller
@RequestMapping("/showapi")
public class ShowUserVolunteerFunctionsController {
    private static final Logger LOG = Logger.getLogger(ShowUserVolunteerFunctionsController.class);

    private final UserVolunteerFunctionService uvfService = ServiceFactory.getUserVolunteerFunctionService();

    @GetMapping("/signup")
    public String addUserToVolunteerFunctionPage() {
        return "showapi/addUserToVolunteerFunction";
    }

    @PostMapping("/signup")
    public String addUserToVolunteerFunction(@RequestParam String userLogin, @RequestParam int volunteerFunctionID) {
        UserService userService = ServiceFactory.getUserService();
        VolunteerFunctionService vfService = ServiceFactory.getVolunteerFunctionService();
        UserVolunteerFunction uvf = uvfService.addUserVolunteerFunction(userService.getUserByLogin(userLogin),
                vfService.getVolunteerFunctionByID(volunteerFunctionID));
        return "redirect:/showapi/event/" + uvf.getVolunteerFunction().getEvent().getEventID();
    }

    @GetMapping("/volunteerFunction/{vfID}")
    public String getVolunteerFunction(@PathVariable int vfID, Model model) {
        LOG.info("Getting VolunteerFunction with id = " + vfID);
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        UserVolunteerFunctionService uvfs = ServiceFactory.getUserVolunteerFunctionService();
        VolunteerFunction volunteerFunction = vfs.getVolunteerFunctionByID(vfID);
        model.addAttribute("volunteerFunction", volunteerFunction);
        model.addAttribute("userVolunteerFunctions", uvfs.getUserVolunteerFunctionsOfVolunteerFunction(volunteerFunction));
        return "showapi/currentVolunteerFunction";
    }
}