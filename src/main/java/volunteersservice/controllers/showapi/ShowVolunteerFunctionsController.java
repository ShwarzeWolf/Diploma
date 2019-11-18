package volunteersservice.controllers.showapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import volunteersservice.models.entities.Event;
import volunteersservice.services.EventService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.exceptions.VolunteerFunctionCreationException;

@Controller
@RequestMapping("/showapi")
public class ShowVolunteerFunctionsController {

    VolunteerFunctionService volunteerFunctions;

    public ShowVolunteerFunctionsController() {
        volunteerFunctions = ServiceFactory.getVolunteerFunctionService();
    }

    @GetMapping(path = "/event/{eventID}/addVolunteerFunction")
    public String addRolePage(@PathVariable(value = "eventID") String eventID) {
        return "showapi/testAddVolunteerFunctionForm";
    }

    @PostMapping(path = "/event/{eventID}/addVolunteerFunction")
    public @ResponseBody String addRole(@PathVariable(value = "eventID") int eventID, @RequestParam String name,
            @RequestParam String description, @RequestParam String requirements, @RequestParam String timeStart,
            @RequestParam String timeFinish, @RequestParam int numberNeeded) {
        EventService events = ServiceFactory.getEventService();
        Event event = events.getEventByID(eventID);
        if (event == null) {
            return "No Such event";
        }
        try {
            volunteerFunctions.addVolunteerFunction(event, name, description, requirements, timeStart, timeFinish,
                    numberNeeded);
            return "Volunteer Functions added";
        } catch (VolunteerFunctionCreationException ex) {
            return "Error happened: " + ex;
        }
    }
}