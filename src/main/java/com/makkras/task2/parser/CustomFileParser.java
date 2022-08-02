package com.makkras.task2.parser;

import com.makkras.task2.entity.Data;
import com.makkras.task2.entity.FileContent;
import com.makkras.task2.exception.ParsingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomFileParser {
    FileContent parseFile(MultipartFile excelFile) throws ParsingException;
    void parseDataIntoFile(List<Data> dataFromAllFiles) throws ParsingException;
}
