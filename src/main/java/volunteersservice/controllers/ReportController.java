package volunteersservice.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.FirstPartOfReport;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.ReportService;
import volunteersservice.services.UserService;
import volunteersservice.utils.ServiceFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class ReportController {
    private final ReportService reportService = ServiceFactory.getFirstPartReportService();
    private final EventService eventService = ServiceFactory.getEventService();

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


        return "redirect:/events/" + eventID + "/reports";
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

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/reports/edit")
    public String editReportAdditionalInfoPage(@PathVariable int eventID,
                                               Model model){
        Event event = eventService.getEventByID(eventID);

        FirstPartOfReport report = reportService.getFirstPartOfAReportByEvent(event);
        model.addAttribute("report", report);

        return "editReport";
    }

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/reports/edit")
    public String editReportAdditionalInfo(@PathVariable int eventID,
                                           @RequestParam String shortName,
                                           @RequestParam String category,
                                           @RequestParam String publicity,
                                           @RequestParam String level,
                                           @RequestParam String shortDescription,
                                           @RequestParam String participants) {
        Event event = eventService.getEventByID(eventID);
        FirstPartOfReport report = reportService.getFirstPartOfAReportByEvent(event);

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

        report.setCategoryStatus(categoryStatus);
        report.setLevel(levelStatus);
        report.setPublicityStatus(publicityStatus);

        report.setShortName(shortName);
        report.setParticipants(participants);
        report.setShortDescription(shortDescription);

        reportService.updateFirstPartOfAReportReport(report);
        return "redirect:/events/" + eventID + "/reports";
    }


    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File( "uploaded")));
                stream.write(bytes);
                stream.close();
                System.out.println("Вы удачно загрузили " +  " в " +  "-uploaded !");
            } catch (Exception e) {
                return "Вам не удалось загрузить " + " => " + e.getMessage();
            }
        } else {
            System.out.println("Вам не удалось загрузить " + " потому что файл пустой.");
        }
        return "main";
    }
}
