package com.duro.edc_koko.cloud.azure.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureBlobConfig {

    @Value("${azure.connection-string}")
    private String CONNECTION_STRING;

    @Value("${azure.image-container-name}")
    private String containerName;

    @Bean
    public BlobContainerClient GetBlobServiceClientTokenCredential() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(CONNECTION_STRING)
                .buildClient();

        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
        return blobContainerClient;
    }
}
