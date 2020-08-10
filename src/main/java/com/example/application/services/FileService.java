package com.example.application.services;

import com.example.application.model.database.Person;
import com.example.application.model.database.PersonAddress;
import com.example.application.model.database.UploadedFileLogs;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Service
public class FileService {

    @Autowired
    TableService service;

    public void readWorkbook(InputStream inputStream, MemoryBuffer memoryBuffer, int userId) {
        Date date = new Date();
        Timestamp ts=new Timestamp(date.getTime());
        SimpleDateFormat dateFormatFolders = new SimpleDateFormat("yyyy/MM/");
        int counter = 0;
        File targetExcelFile = new File("D:\\vaadin projects\\tableUploader\\Excel Files\\" +
                dateFormatFolders.format(date) + memoryBuffer.getFileName() + date.getTime());
        targetExcelFile.getParentFile().mkdirs();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            FileOutputStream excelFile = new FileOutputStream(targetExcelFile);
            XSSFSheet firstSheet = workbook.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;
            Iterator<Row> rows = firstSheet.rowIterator();

            while (rows.hasNext()) {
                counter++;
                row = (XSSFRow) rows.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Person person = new Person();
                PersonAddress address1 = new PersonAddress();
                while (cellIterator.hasNext()) {
                    cell = (XSSFCell) cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            person.setName(cell.getStringCellValue());
                            cell.getStringCellValue();
                            break;
                        case 1:
                            person.setLastName(cell.getStringCellValue());
                            cell.getStringCellValue();
                            break;
                        case 2:
                            person.setPhoneNumber(cell.getStringCellValue());
                            cell.getStringCellValue();
                            break;
                        case 3:
                            address1.setCity(cell.getStringCellValue());
                            break;
                        case 4:
                            address1.setStreet(cell.getStringCellValue());
                            break;
                        case 5:
                            address1.setNumber((int) cell.getNumericCellValue());
                            break;
                    }
                }
                person.setAddress(address1);
                service.save(person);
            }
            Notification.show("Done, " + counter + " entries uploaded.").setPosition(Notification.Position.MIDDLE);
            UploadedFileLogs uploadedFileLogs = new UploadedFileLogs(memoryBuffer.getFileName(), userId, ts);
            workbook.write(excelFile);
            service.saveFile(uploadedFileLogs);
            excelFile.close();
        } catch (
                IOException ex1) {
            Notification.show("Error reading file").setPosition(Notification.Position.MIDDLE);
            ex1.printStackTrace();
        }
    }
}