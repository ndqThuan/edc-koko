package com.duro.edc_koko.cloud.azure.controller;

import com.duro.edc_koko.cloud.azure.service.ImageBlobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blob")
public class BlobController {
    private final ImageBlobService service;

    @GetMapping("/listBlobs")
    public List<String> listAllBlobs () {
        return service.getAllFile();
    }

    @GetMapping("/listFiles")
    public String listFilesUsingJavaIO () {
        String dir = "/home/duro/Downloads/";
        StringBuilder listAll = new StringBuilder();
        Set<String> dirList = Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                                    .filter(File::isDirectory)
                                    .map(File::getPath)
                                    .collect(Collectors.toSet());

        for (String s : dirList) {
            File f = new File(s);
            listAll.append(f.getName()).append("\n\t");


            listAll.append(Arrays.toString(f.listFiles()));

            listAll.append("\n\n");
        }

        return listAll.toString();
    }

    @PostMapping("uploadAll")
    public void uploadAllImages () {
        service.uploadAllProductImages();
    }

}
