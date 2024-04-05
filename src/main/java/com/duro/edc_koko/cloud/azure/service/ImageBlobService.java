package com.duro.edc_koko.cloud.azure.service;


import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.duro.edc_koko.entity.image.model.ImageDTO;
import com.duro.edc_koko.entity.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImageBlobService {

    private final BlobContainerClient containerClient;
    private final ImageService imageService;
    private final Path filePath = new File("/home/duro/Downloads/").toPath();

    public String uploadImage (String fileName, String fileDirectory) {
        BlobClient blobClient = containerClient.getBlobClient(fileName);
        blobClient.uploadFromFile(fileDirectory);
        return blobClient.getBlobUrl();
    }

    public List<String> getAllFile () {
        List<String> blobFiles = new ArrayList<>();
        containerClient.listBlobs().forEach(blobItem -> blobFiles.add("Blob name's " + blobItem.getProperties()));
        return blobFiles;
    }

    public void uploadAllProductImages () {
        Set<String> dirList = Stream.of(Objects.requireNonNull(filePath.toFile().listFiles()))
                                    .filter(File::isDirectory)
                                    .map(File::getPath)
                                    .collect(Collectors.toSet());

        for (String s : dirList) {
            File f = new File(s);

            String productIdDirectoryName = f.getName();
            int imageCount = 1;

            for (File childFile : Objects.requireNonNull(f.listFiles())) {
                String thisFileName = productIdDirectoryName + "_" + imageCount;
                String uploadedImageUrl = uploadImage(thisFileName, childFile.getAbsolutePath());
                ImageDTO imageDTO = new ImageDTO(uploadedImageUrl, Integer.parseInt(productIdDirectoryName));
                imageService.create(imageDTO);
                imageCount++;
            }
        }
    }
}
