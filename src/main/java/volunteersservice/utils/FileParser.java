package volunteersservice.utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import volunteersservice.models.entities.Volunteers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileParser {
    public static List<Volunteers> parseFile(MultipartFile file) throws IOException, InvalidFormatException {
        File fileWithInfoAboutVolunteers = new File(file.getOriginalFilename());
        FileUtils.writeByteArrayToFile(fileWithInfoAboutVolunteers, file.getBytes());

        ArrayList<Volunteers> volunteers = new ArrayList<>();


            Workbook workbook = new XSSFWorkbook(fileWithInfoAboutVolunteers);
            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> ri = sheet.rowIterator();
            while (ri.hasNext()) {
                XSSFRow row = (XSSFRow) ri.next();
                String status = row.getCell(2).getStringCellValue();

                if (status.equals("подтверждена")) {
                    String name = row.getCell(3).getStringCellValue();
                    String surname = row.getCell(4).getStringCellValue();
                    String patronymic;
                    try {
                        patronymic = row.getCell(5).getStringCellValue();
                    } catch (Exception ex){
                        patronymic = "";
                    }


                    String FIO =  name.concat(" ").concat(surname).concat(" ").concat(patronymic);

                    String role = "Волонтер";
                    String whatWasDone = row.getCell(21).getStringCellValue();

                    System.out.println(FIO);
                    System.out.println(whatWasDone);
                    System.out.println(role);
                    Volunteers volunteer = new Volunteers(FIO, whatWasDone, role);
                    volunteers.add(volunteer);
                }
            }

        return volunteers;
    }
}
