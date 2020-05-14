package volunteersservice.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteersservice.models.entities.*;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.FirstPartReportService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Controller
public class EventController {
    private final static Logger LOG = Logger.getLogger(EventController.class);
    private final EventService eventService = ServiceFactory.getEventService();
    private final FirstPartReportService reportService = ServiceFactory.getFirstPartReportService();

    @PreAuthorize("hasAuthority('ORGANISER')")
    @GetMapping("/addEvent")
    public String addEventPage(Model model) {
        User user = Utils.getUserFromContext();

        String name = user.getName().concat(" ").concat(user.getSurname());

        model.addAttribute("name", name);

        return "addEventForm";
    }

    @PreAuthorize("hasAuthority('ORGANISER')")
    @PostMapping("/addEvent")
    public String addEvent(@RequestParam String name,
                           @RequestParam String place,
                           @RequestParam String description,
                           @RequestParam String dateStart,
                           @RequestParam String dateFinish,
                           @RequestParam (defaultValue = "") String requirements,
                           @RequestParam (defaultValue = "") String clothesType,
                           @RequestParam (defaultValue = "") String accommodation,
                           @RequestParam (defaultValue = "") String food) {

        Event ev = eventService.addEvent(name, Utils.getUserFromContext(), description, place, dateStart, dateFinish, requirements, clothesType, accommodation, food);

        LOG.info(String.format("User \"%s\" added event %s", Utils.getUserFromContext().getLogin(), ev));
        return "redirect:/events/" + ev.getEventID();
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}")
    public String getEventByID(@PathVariable int eventID,
                               Model model) {

        Event currentEvent = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        model.addAttribute("roleName", user.getUserRole().getName());
        model.addAttribute("event", currentEvent);
        model.addAttribute("user", Utils.getUserFromContext());

        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        model.addAttribute("volunteerFunctions", vfs.getVolunteerFunctions(currentEvent));

        return "currentEvent";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/edit")
    public String editEventPage(@PathVariable int eventID,
                                Model model){

        Event currentEvent = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        if (currentEvent.getOrganiser().getName().equals(user.getName())
                && currentEvent.getStatus().getName().equals("CREATED")
                || currentEvent.getCoordinator() != null && currentEvent.getCoordinator().equals(user)) {

            model.addAttribute("roleName", user.getUserRole().getName());
            model.addAttribute("event", currentEvent);
            model.addAttribute("user", Utils.getUserFromContext());

            return "editEvent";
        }

        return "403";
    };


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/edit")
    public String editEvent(@PathVariable int eventID,
                            Model model,
                            @RequestParam String name,
                            @RequestParam String description,
                            @RequestParam String place,
                            @RequestParam String dateStart,
                            @RequestParam String dateFinish,
                            @RequestParam String requirements,
                            @RequestParam String clothesType,
                            @RequestParam String accommodation,
                            @RequestParam String food) {
        Event event = eventService.getEventByID(eventID);

        event.setName(name);
        event.setDescription(description);
        event.setPlace(place);
        event.setDateStart(dateStart);
        event.setDateFinish(dateFinish);
        event.setAccommodation(accommodation);
        event.setClothesType(clothesType);
        event.setRequirements(requirements);
        event.setFood(food);

        eventService.updateEventInformation(event);

        LOG.info(String.format("User \"%s\" edits information of the event [%s]", Utils.getUserFromContext().getLogin(), event));
        return "redirect:/events/" + eventID;
    }


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}")
    public String addVolunteerFunction(@PathVariable int eventID,
                                       @RequestParam String name,
                                       @RequestParam String description,
                                       @RequestParam String requirements,
                                       @RequestParam String timeStart,
                                       @RequestParam String timeFinish,
                                       @RequestParam int numberNeeded) {
        Event currentEvent = eventService.getEventByID(eventID);

        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        VolunteerFunction vf = vfs.addVolunteerFunction(currentEvent, name, description, requirements, timeStart, timeFinish, numberNeeded);

        LOG.info(String.format("User \"%s\" adds volunteer function [%s] for event [%s]", Utils.getUserFromContext().getLogin(), vf, currentEvent));

        return "redirect:/events/" + eventID;
    }


    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/volunteerFunctions/{volunteerFunctionID}/edit")
    public String editVolunteerFunction(@PathVariable int volunteerFunctionID,
                                       @PathVariable int eventID,
                                       Model model) {
        Event currentEvent = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        if (currentEvent.getOrganiser().getName().equals(user.getName()) && currentEvent.getStatus().getName().equals("CREATED")
                || currentEvent.getCoordinator()!= null && currentEvent.getCoordinator().getLogin() == user.getLogin())
        {
            VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
            VolunteerFunction volunteerFunction = vfs.getVolunteerFunctionByID(volunteerFunctionID);

            model.addAttribute("volunteerFunction", volunteerFunction);
            model.addAttribute("user", Utils.getUserFromContext());

            return "editVolunteerFunction";
        }

        return "403";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/volunteerFunctions/{volunteerFunctionID}/edit")
    public String editVolunteerFunction(@PathVariable int volunteerFunctionID,
                                        @PathVariable int eventID,
                                        @RequestParam String name,
                                        @RequestParam String description,
                                        @RequestParam String requirements,
                                        @RequestParam String timeStart,
                                        @RequestParam String timeFinish,
                                        @RequestParam int numberNeeded,
                                        Model model) {
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        VolunteerFunction volunteerFunction = vfs.getVolunteerFunctionByID(volunteerFunctionID);

        volunteerFunction.setName(name);
        volunteerFunction.setDescription(description);
        volunteerFunction.setRequirements(requirements);
        volunteerFunction.setTimeStart(timeStart);
        volunteerFunction.setTimeFinish(timeFinish);
        volunteerFunction.setNumberNeeded(numberNeeded);

        vfs.updateVolunteerFunctionInformation(volunteerFunction);

        LOG.info(String.format("User \"%s\" edits information of the volunteerFunction [%s]", Utils.getUserFromContext().getLogin(), volunteerFunction));
        return "redirect:/events/" + eventID;
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'MANAGER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/setEventStatus")
    public String setEventStatus(@PathVariable int eventID,
                                 @RequestParam String changeStatus,
                                 @RequestParam(required = false) String message) {
        Event event = eventService.getEventByID(eventID);
        User user = Utils.getUserFromContext();

        eventService.setStatus(event, EventStatusEnum.valueOf(changeStatus));
        LOG.info(String.format("User \"%s\" changes event [%s] status: \"%s\" -> \"%s\"", Utils.getUserFromContext().getLogin(), event, event.getStatus().getName(), changeStatus));

         if (changeStatus.equals("APPROVED") || changeStatus.equals("DENIED")) {
             eventService.setMessage(event, message);
             eventService.setManager(event, user);
         }

         return "redirect:/events/";
    }

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/coordinate")
    public String coordinateEvent(@PathVariable int eventID) {
        User user = Utils.getUserFromContext();
        Event event = eventService.getEventByID(eventID);

        eventService.setCoordinator(event, user);
        eventService.setStatus(event, EventStatusEnum.ASSIGNED);

        LOG.info(String.format("Setting a coordinator for event [%s]: %s", event, user.getLogin()));

        return "redirect:/events/" + eventID;
    }

    @PreAuthorize("hasAuthority('ORGANISER')")
    @PostMapping("/events/{eventID}/delete")
    public String deleteEvent(@PathVariable int eventID){
        User user = Utils.getUserFromContext();
        Event eventToDelete = eventService.getEventByID(eventID);

        if (eventToDelete.getStatus().getName().equals("CREATED") && user.getLogin().equals(eventToDelete.getOrganiser().getLogin())) {
            eventService.deleteEvent(eventToDelete);
        }

        return "redirect:/events/";
    }

    @PreAuthorize("hasAnyAuthority('ORGANISER', 'COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/volunteerFunctions/{volunteerFunctionID}/delete")
    public String deleteVolunteerFunction(@PathVariable int eventID,
                                        @PathVariable int volunteerFunctionID){
        User user = Utils.getUserFromContext();

        Event event = eventService.getEventByID(eventID);
        VolunteerFunctionService vfs = ServiceFactory.getVolunteerFunctionService();
        VolunteerFunction volunteerFunctionToDelete = vfs.getVolunteerFunctionByID(volunteerFunctionID);

        if (event.getStatus().getName().equals("CREATED")
                && user.getLogin().equals(event.getOrganiser().getLogin())
                ||  event.getCoordinator() != null
                && event.getCoordinator().getLogin().equals(user.getLogin())) {
            vfs.deleteVolunteerFunction(volunteerFunctionToDelete);
        }

        return "redirect:/events/" + eventID;
    }





    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/createReport")
    public String addReportPage(@PathVariable int eventID) {
        return "reportAdditionalInfo";
    }

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/createReport")
    public String addReport(@PathVariable int eventID,
                            @RequestParam String shortName,
                            @RequestParam String category,
                            @RequestParam String publicity,
                            @RequestParam String level,
                            @RequestParam String shortDescription,
                            @RequestParam String participants)
    {
        CategoryStatusEnum categoryStatus = category.equals("categoryInner") ? CategoryStatusEnum.INNER : CategoryStatusEnum.OUTER;
        PublicityStatusEnum publicityStatus = publicity.equals("publicityOpen") ? PublicityStatusEnum.OPEN : PublicityStatusEnum.CLOSED;

        LevelStatusEnum levelStatus;
        switch (level){
            case "levelFaculty": levelStatus = LevelStatusEnum.FACULTY; break;
            case "levelUniversity": levelStatus = LevelStatusEnum.UNIVERSITY; break;
            case "levelCity": levelStatus = LevelStatusEnum.CITY; break;
            case "levelRegion": levelStatus = LevelStatusEnum.REGION; break;
            case "levelCountry": levelStatus = LevelStatusEnum.FEDERAL; break;
            case "levelInternational": levelStatus = LevelStatusEnum.INTERNATIONAL; break;
            default: levelStatus = LevelStatusEnum.FACULTY;
        }

        Event event = eventService.getEventByID(eventID);

        FirstPartOfReport report = reportService.addFirstPartOfAReport(event, shortName, categoryStatus, publicityStatus, levelStatus, shortDescription, participants);


        return "redirect:/events/" + eventID ; //+ "/reports/firstPart/"+ report.getReportID();
    }

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/reports")
    public String getReport(@PathVariable int eventID,
                            Model model) {

        Event event = eventService.getEventByID(eventID);
        FirstPartOfReport report = reportService.getFirstPartOfAReportByEvent(event);

        model.addAttribute("event", event);
        model.addAttribute("reportInfo", report);

        return "report";
    }

}