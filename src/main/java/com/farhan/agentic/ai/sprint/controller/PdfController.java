package com.farhan.agentic.ai.sprint.controller;

import com.farhan.agentic.ai.sprint.service.PdfService;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/search")
    public String search(@RequestParam String conversationId,
            @RequestParam String query) {
        return pdfService.search(conversationId, query);
    }


}
