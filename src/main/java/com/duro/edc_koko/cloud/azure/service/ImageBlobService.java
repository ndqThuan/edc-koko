package com.duro.edc_koko.cloud.azure.service;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageBlobService {

    private final BlobContainerClient containerClient;
    private Path filePath = new File("/home/duro/Downloads/nalgene.jpg").toPath();

    public String uploadImage() {
        String fileName = filePath.getFileName().toString();
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.uploadFromFile(String.valueOf(filePath));
        return "Successfully uploaded" + blobClient.getBlobUrl();
    }

    public List<String> getAllFile() {
        List<String> blobFiles = new ArrayList<>();
        containerClient.listBlobs().forEach(blobItem -> blobFiles.add("Blob name's " + blobItem.getProperties()));
        return blobFiles;
    }
}
