package com.makkras.task2.parser;

import com.makkras.task2.entity.FileContent;
import com.makkras.task2.exception.ParsingException;
import org.springframework.web.multipart.MultipartFile;

public interface CustomFileParser {
    FileContent parseFile(MultipartFile excelFile) throws ParsingException;
}
