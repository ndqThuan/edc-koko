package com.duro.edc_koko.cloud.azure.controller;

import com.duro.edc_koko.cloud.azure.service.ImageBlobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blob")
public class BlobController {
    private final ImageBlobService service;

    @PostMapping("/uploadNalgeneBottle")
    public String upload() {
        return service.uploadImage();
    }

    @GetMapping("/listBlobs")
    public List<String> listAllBlobs() {
        return service.getAllFile();
    }
}
