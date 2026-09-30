package com.farhan.agentic.ai.sprint.controller;

import com.farhan.agentic.ai.sprint.service.PdfService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }
    @PostMapping
    public String uploadPdf(@RequestParam("file") MultipartFile file) {
        return pdfService.uploadPdf(file);
    }

}
