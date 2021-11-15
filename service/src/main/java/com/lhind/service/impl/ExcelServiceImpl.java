package com.lhind.service.impl;

import com.lhind.dto.application.ApplicationResponseDto;
import com.lhind.exception.LhindNotFoundException;
import com.lhind.util.NoData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.lhind.util.LhindUtil.*;

@Service
public class ExcelServiceImpl {

    public Resource generateXlsx(List<ApplicationResponseDto> applicationResponseDtos) {
        return generateDocument((workbook -> {
            Sheet sheet = workbook.createSheet(EXCEL_APPLICATION_REPORT);
            Row header = sheet.createRow(0);
            this.createHeader(header, new String[]{
                    APPLICATION_ID,
                    USER,
                    SUPERVISOR,
                    DAYS_OFF,
                    STATUS,
                    START_DATE,
                    END_DATE,
                    COMMENT
            });

            int rowIndex = 1;
            for (ApplicationResponseDto application : applicationResponseDtos) {
                Row row = sheet.createRow(rowIndex++);
                this.createCell(row, 0, application.getId());
                this.createCell(row, 1, application.getUser().getFirstName() + " " + application.getUser().getLastName());
                this.createCell(row, 2, application.getSupervisor().getFirstName() + " " + application.getSupervisor().getLastName());
                this.createCell(row, 3, application.getDaysOff());
                this.createCell(row, 4, application.getStatus().name());
                this.createCell(row, 5, application.getStartDate());
                this.createCell(row, 6, application.getEndDate());
                this.createCell(row, 7, application.getComment());
            }
        }));
    }


    private void createHeader(Row row, String[] values) {
        int index = 0;
        for (String value : values) {
            Cell cell = row.createCell(index++);
            cell.setCellValue(value.toUpperCase());
        }
    }

    private Resource generateDocument(GenerateExcel generateExcel) {
        try (Workbook workbook = new XSSFWorkbook()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            generateExcel.generate(workbook);
            workbook.write(byteArrayOutputStream);
            return new ByteArrayResource(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new LhindNotFoundException(NoData.EXCEL_ERROR);
        }
    }

    private void createCell(Row row, int index, String value) {
        Cell cell = row.createCell(index);
        cell.setCellValue(value);
    }

    private void createCell(Row row, int index, Integer value) {
        Cell cell = row.createCell(index);
        cell.setCellValue(value != null ? value : 0);
    }

    private void createCell(Row row, int index, Date value) {
        Cell cell = row.createCell(index);
        cell.setCellValue(value == null ? "-" : new SimpleDateFormat("dd.MM.yyy").format(value));
    }

    @FunctionalInterface
    private interface GenerateExcel {
        void generate(Workbook workbook);
    }
}
