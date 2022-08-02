package com.makkras.task2.parser.impl;

import com.makkras.task2.entity.Balance;
import com.makkras.task2.entity.Data;
import com.makkras.task2.entity.FileContent;
import com.makkras.task2.entity.Turnover;
import com.makkras.task2.exception.ParsingException;
import com.makkras.task2.parser.CustomFileParser;
import com.makkras.task2.util.UtilService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class XlsFileParser implements CustomFileParser {
    private static final String FILE_FOR_DB_DATA_IMPORT = "C:\\task2\\DataFromServer.xls";
    private UtilService utilService;
    public XlsFileParser(UtilService utilService) {
        this.utilService = utilService;
    }
    public FileContent parseFile(MultipartFile excelFile) throws ParsingException {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(excelFile.getInputStream());
            HSSFSheet sheet = workbook.getSheetAt(0);
            List<Data> dataList = new ArrayList<>();
            for(Row row : sheet) {
                int counter = 0;
                long bch = 0L;
                Balance incomingBalance = new Balance();
                Balance outcomingBalance = new Balance();
                Turnover currentTurnover = new Turnover();
                boolean isValidRow = true;
                for (Cell cell : row) {
                    if(!isValidRow || counter > 6) {
                        break;
                    }
                    switch (cell.getCellType()) {
                        case ERROR:
                        case _NONE:
                        case BOOLEAN:
                        case FORMULA:
                        case BLANK: {
                            isValidRow = false;
                            break;
                        }
                        case STRING: {
                            if(utilService.isLong(cell.getStringCellValue()) && counter == 0) {
                                bch = Long.parseLong(cell.getStringCellValue());
                                counter++;
                            }
                            break;
                        }
                        case NUMERIC: {
                            if(!cell.getCellType().equals(CellType.STRING)) {
                                BigDecimal cellValue = BigDecimal.valueOf(cell.getNumericCellValue());
                                switch (counter) {
                                    case 1:{
                                        incomingBalance.setActive(cellValue);
                                        break;
                                    }
                                    case 2:{
                                        incomingBalance.setPassive(cellValue);
                                        break;
                                    }
                                    case 3:{
                                        currentTurnover.setDebit(cellValue);
                                        break;
                                    }
                                    case 4:{
                                        currentTurnover.setCredit(cellValue);
                                        break;
                                    }
                                    case 5:{
                                        outcomingBalance.setActive(cellValue);
                                        break;
                                    }
                                    case 6:{
                                        outcomingBalance.setPassive(cellValue);
                                        break;
                                    }
                                }
                                counter++;
                            }
                        }

                    }
                }
                if(bch != 0) {
                    dataList.add(new Data(bch,incomingBalance,currentTurnover,outcomingBalance));
                }
            }
            return new FileContent(excelFile.getOriginalFilename(),dataList);
        } catch (IOException exception) {
            throw new ParsingException(exception.getMessage());
        }
    }
    public void parseDataIntoFile(List<Data> dataFromAllFiles) throws ParsingException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_FOR_DB_DATA_IMPORT)){
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("ServerData");
            int rowCounter = 0;
            Row row = sheet.createRow(rowCounter);
            row.createCell(0,CellType.STRING).setCellValue("Б/сч");
            row.createCell(1,CellType.STRING).setCellValue("Актив");
            row.createCell(2,CellType.STRING).setCellValue("Пассив");
            row.createCell(3,CellType.STRING).setCellValue("Дебит");
            row.createCell(4,CellType.STRING).setCellValue("Кредит");
            row.createCell(5,CellType.STRING).setCellValue("Актив");
            row.createCell(6,CellType.STRING).setCellValue("Пассив");
            rowCounter++;
            for(Data dataRow : dataFromAllFiles) {
                row = sheet.createRow(rowCounter);
                row.createCell(0,CellType.NUMERIC).setCellValue(dataRow.getBch());
                row.createCell(1,CellType.NUMERIC).setCellValue(dataRow.getIncomingBalance().getActive().doubleValue());
                row.createCell(2,CellType.NUMERIC).setCellValue(dataRow.getIncomingBalance().getPassive().doubleValue());
                row.createCell(3,CellType.NUMERIC).setCellValue(dataRow.getCurrentTurnover().getDebit().doubleValue());
                row.createCell(4,CellType.NUMERIC).setCellValue(dataRow.getCurrentTurnover().getCredit().doubleValue());
                row.createCell(5,CellType.NUMERIC).setCellValue(dataRow.getOutcomingBalance().getActive().doubleValue());
                row.createCell(6,CellType.NUMERIC).setCellValue(dataRow.getOutcomingBalance().getPassive().doubleValue());
                rowCounter++;
            }
            workbook.write(fileOutputStream);
            workbook.close();
        } catch (IOException e) {
            throw new ParsingException();
        }
    }
}
