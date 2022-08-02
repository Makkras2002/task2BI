package com.makkras.task2.controller;

import com.makkras.task2.entity.Data;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {
    private static Logger logger = LogManager.getLogger();
    private CustomFileParser fileParser;
    private FileContentJpaRepository fileContentJpaRepository;
    @Autowired
    public TaskController(XlsFileParser xlsFileParser, FileContentJpaRepository fileContentJpaRepository){
        this.fileParser = xlsFileParser;
        this.fileContentJpaRepository =fileContentJpaRepository;
    }
    @GetMapping("/xlsAnalyzer")
    public String mainPage(Model model, @ModelAttribute("dbStatus") Object dbStatus,
                           @ModelAttribute("importStatus") Object importStatus) {
        List<String> fileNamesList = fileContentJpaRepository.findAllFileName();
        System.out.println(fileNamesList.toString());
        model.addAttribute("files",fileNamesList);
        model.addAttribute("successfullySavedDataToDb",dbStatus);
        model.addAttribute("successfullyImportedDataFromDb",importStatus);
        return "main_page";
    }
    @PostMapping("/xlsAnalyzer")
    public RedirectView postLoadFileToDb(Model model, @RequestParam MultipartFile excelFile,
                                         RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        try {
            FileContent fileContent = fileParser.parseFile(excelFile);
            if(fileContentJpaRepository.existsByFileName(fileContent.getFileName())) {
                redirectAttributes.addFlashAttribute("dbStatus", false);
            } else {
                fileContentJpaRepository.save(fileContent);
                redirectAttributes.addFlashAttribute("dbStatus", true);
            }
        } catch (ParsingException e ) {
            logger.error(e.getMessage());
        }
        return new RedirectView(httpServletRequest.getContextPath()+"/xlsAnalyzer");
    }
    @PostMapping("/showDataForFile")
    public String showDataForFile(Model model, @RequestParam String fileName) {
        Optional<FileContent> optionalFileContent = fileContentJpaRepository.findById(fileName);
        if(optionalFileContent.isPresent()) {
            FileContent fileContent = optionalFileContent.get();
            model.addAttribute("fileContent",fileContent);
            return "file_data_page";
        } else {
            return "main_page";
        }
    }
    @GetMapping("/importDataFromDbToFile")
    public RedirectView importDataFromDbToFile(Model model, RedirectAttributes redirectAttributes,
                                         HttpServletRequest httpServletRequest) {
        List<FileContent> fileContents = fileContentJpaRepository.findAll();
        List<Data> dataFromAllFiles = new ArrayList<>();
        fileContents.forEach(fileContent -> dataFromAllFiles.addAll(fileContent.getFileDataList()));
        try {
            fileParser.parseDataIntoFile(dataFromAllFiles);
            redirectAttributes.addFlashAttribute("importStatus",true);
        } catch (ParsingException e) {
            logger.error(e.getMessage());
            redirectAttributes.addFlashAttribute("importStatus",false);
        }
        return new RedirectView(httpServletRequest.getContextPath()+"/xlsAnalyzer");
    }
}
