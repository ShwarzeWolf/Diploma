package volunteersservice.controllers;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.FirstPartOfReport;
import volunteersservice.models.entities.SecondPartOfReport;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;
import volunteersservice.services.EventService;
import volunteersservice.services.ReportService;
import volunteersservice.utils.ServiceFactory;

import java.io.*;
import java.util.Iterator;

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
        SecondPartOfReport secondPart = reportService.getSecondPartOfAReportByEvent(event);
        //volunteersBySecondPart
        model.addAttribute("event", event);
        model.addAttribute("reportInfo", report);
        model.addAttribute("secondPart", secondPart);

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







    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @GetMapping("/events/{eventID}/addSecondPart")
    public String addSecondPart(@PathVariable int eventID) {
        return "addSecondPart";
    }

    @PreAuthorize("hasAnyAuthority('COORDINATOR', 'MOVEMENTLEADER')")
    @PostMapping("/events/{eventID}/addSecondPart")
    public String addReport(@PathVariable int eventID,
                            @RequestParam String links,
                            @RequestParam int numberOfParticipants,
                            @RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {

        Event event = eventService.getEventByID(eventID);
        SecondPartOfReport secondPart = reportService.addSecondPart(event, numberOfParticipants, links, null);

        /*
        File fileWithInfoAboutVolunteers = new File(file.getOriginalFilename());
        FileUtils.writeByteArrayToFile(fileWithInfoAboutVolunteers, file.getBytes());

        Workbook workbook = new XSSFWorkbook(fileWithInfoAboutVolunteers);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> ri = sheet.rowIterator();
        while(ri.hasNext()) {
            XSSFRow row = (XSSFRow) ri.next();
            String status = row.getCell(2).getStringCellValue();

            if (status.equals("подтверждена")){
                String name = row.getCell(3).getStringCellValue();
                String surname = row.getCell(4).getStringCellValue();
                String patronymic = row.getCell(5).getStringCellValue();

                String FIO = name.concat(" ").concat(surname).concat(" ").concat(patronymic);

                String role = "Волонтер";
                String thatWasDone = row.getCell(21).getStringCellValue();
            }
        }
*/
        return "redirect:/events/" + eventID + "/reports";
    }
}
