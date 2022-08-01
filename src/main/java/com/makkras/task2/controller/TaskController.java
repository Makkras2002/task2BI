package com.makkras.task2.controller;

import com.makkras.task2.entity.FileContent;
import com.makkras.task2.exception.ParsingException;
import com.makkras.task2.parser.CustomFileParser;
import com.makkras.task2.parser.impl.XlsFileParser;
import com.makkras.task2.repo.FileContentJpaRepository;
import com.makkras.task2.util.UtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TaskController {
    private static Logger logger = LogManager.getLogger();
    private CustomFileParser fileParser;
    private FileContentJpaRepository fileContentJpaRepository;
    @Autowired
    public TaskController(UtilService utilService, XlsFileParser xlsFileParser, FileContentJpaRepository fileContentJpaRepository){
        this.fileParser = xlsFileParser;
        this.fileContentJpaRepository =fileContentJpaRepository;
    }
    @GetMapping("/xlsAnalyzer")
    public String mainPage(Model model) {
        return "main_page";
    }
    @PostMapping("/xlsAnalyzer")
    public String postLoadFileToDb(Model model, @RequestParam MultipartFile excelFile) {
        try {
            FileContent fileContent = fileParser.parseFile(excelFile);
            if(fileContentJpaRepository.existsByFileName(fileContent.getFileName())) {
                model.addAttribute("successfullySavedDataToDb", false);
            } else {
                fileContentJpaRepository.save(fileContent);
                model.addAttribute("successfullySavedDataToDb", true);
            }
        } catch (ParsingException e ) {
            logger.error(e.getMessage());
        }
        return "main_page";
    }
}
