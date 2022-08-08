package com.example.cloudstorage.util;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class CloudStorage {
    @Autowired
    private Storage storage;

    public void createBucket(String bucketName, Long retentionPeriod){
        storage.create(BucketInfo.newBuilder(bucketName).setRetentionPeriod(retentionPeriod).build());
    }

    public void createBucket(String bucketName, Long retentionPeriod, StorageClass storageClass, String location){
        storage.create(
                BucketInfo.newBuilder(bucketName)
                        .setStorageClass(storageClass)
                        .setLocation(location)
                        .setRetentionPeriod(retentionPeriod).build());
    }

    public String uploadFile(String bucketName, String bucketPath, byte[] bytes){
        storage.create(BlobInfo.newBuilder(bucketName, bucketPath).build(), bytes);
        Blob blob = storage.get(bucketName, bucketPath, Storage.BlobGetOption.fields(Storage.BlobField.values()));
        return blob.getMediaLink();
    }

    public String uploadFile(String bucketName, String bucketPath, String filePath) throws IOException {
        storage.create(BlobInfo.newBuilder(bucketName, bucketPath).build(), Files.readAllBytes(Paths.get(filePath)));
        Blob blob = storage.get(bucketName, bucketPath, Storage.BlobGetOption.fields(Storage.BlobField.values()));
        return blob.getMediaLink();
    }

    public void downloadFile(String bucketName, String bucketPath, String filePath) {
        Blob blob = storage.get(BlobId.of(bucketName, bucketPath));
        blob.downloadTo(Paths.get(filePath));
    }

    public byte[] downloadFile(String bucketName, String bucketPath) {
        byte[] content = storage.readAllBytes(bucketName, bucketPath);
        return content;
    }

    public void deleteFile(String bucketName, String bucketPath) {
        storage.delete(bucketName, bucketPath);
    }
}
