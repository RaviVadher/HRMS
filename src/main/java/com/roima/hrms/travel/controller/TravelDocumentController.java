package com.roima.hrms.travel.controller;

import com.roima.hrms.travel.dto.RequiredTravelDocumentRequestDto;
import com.roima.hrms.travel.dto.RequiredTravelDocumentResponseDto;
import com.roima.hrms.travel.entity.RequiredDocument;
import com.roima.hrms.travel.repository.RequiredDocumentRepository;
import com.roima.hrms.travel.service.TravelDocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.List;


@RestController
@RequestMapping("/api/travels/{travelId}")
public class TravelDocumentController {

    private final TravelDocumentService travelDocumentService;
    public TravelDocumentController(TravelDocumentService travelDocumentService) {
        this.travelDocumentService = travelDocumentService;
    }


    //post required document
    @PostMapping("/requiredocument")
    public ResponseEntity<String> postDocument(@PathVariable Long travelId, RequiredTravelDocumentRequestDto dto) {
         travelDocumentService.postDocument(travelId, dto);
         return ResponseEntity.ok("Success fully posted document");
    }


    //get all required document
    @GetMapping("/requiredocument")
    public List<RequiredDocument> postDocument(@PathVariable Long travelId) {

        return  travelDocumentService. getDocument(travelId);
    }


    //uploading the travel document by user
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
            @PathVariable Long travelId,
            @RequestParam Long userId,
            @RequestParam String filetype,
            @RequestParam Long requiredDocumentId,
            @RequestParam MultipartFile file)
    {
        travelDocumentService.uploadDocument(travelId, requiredDocumentId, file,userId,filetype);
        return ResponseEntity.ok("Uploaded successfully");
    }


    //get uplloaded document
    @GetMapping("/{docId}/download")
    public ResponseEntity<Resource> download( @PathVariable Long docId) {

        Resource file = travelDocumentService.downloadDocument(docId);
        return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
