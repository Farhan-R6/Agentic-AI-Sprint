package com.farhan.agentic.ai.sprint.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PdfService {

    private final VectorStore vectorStore;

    public PdfService(@Qualifier("resumeVectorStore") VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public String uploadPdf(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please upload a file.");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only PDF files are allowed. Please upload a PDF file");
        }

        Resource resource = file.getResource();
        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
        List<Document> pages = reader.get();

        pages.forEach(page -> page.getMetadata().put("filename", file.getOriginalFilename()));

        TokenTextSplitter splitter = TokenTextSplitter.builder().build();
        List<Document> chunks = splitter.apply(pages);

        vectorStore.add(chunks);

        return "Uploaded " + chunks.size() + " chunks";
    }
}
