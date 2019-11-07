package volunteersservice.controllers.showapi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

@Controller
@RequestMapping("/testapi")
public class VolunteerFunctionsController {

    VolunteerFunctionService volunteerFunctions;

    public VolunteerFunctionsController() {
        volunteerFunctions = ServiceFactory.getVolunteerFunctionService();
    }

    @GetMapping(path = "/event/{eventID}/addVolunteerFunction")
    public String addRolePage(@PathVariable(value = "eventID") String eventID) {
        return "testapi/testAddVolunteerFunctionForm";
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (volunteerFunctions.addVolunteerFunction(event, name, description, requirements, LocalDateTime.parse(timeStart, formatter),
                LocalDateTime.parse(timeFinish, formatter), numberNeeded))
            return "Volunteer Functions added";
        else
            return "Error while adding a volunteer function";
    }
}