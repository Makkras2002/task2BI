package com.makkras.task2.parser.impl;

import com.makkras.task2.entity.Balance;
import com.makkras.task2.entity.Data;
import com.makkras.task2.entity.FileContent;
import com.makkras.task2.entity.Turnover;
import com.makkras.task2.exception.ParsingException;
import com.makkras.task2.parser.CustomFileParser;
import com.makkras.task2.util.UtilService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class XlsFileParser implements CustomFileParser {
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
}
