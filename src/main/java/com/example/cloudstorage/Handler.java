package com.example.cloudstorage;

import com.example.cloudstorage.util.CloudStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Handler {
    @Autowired
    CloudStorage storage;

    @Value("${bucketName}")
    private String bucketName;

    public Mono<ServerResponse> init(ServerRequest serverRequest) {
//        storage.createBucket("testing", 300L);
        try {
            String bucketPath = "sample.png";
            String filePath = "/Users/zamahsyari/Downloads/newrelic.png";
            String url = storage.uploadFile(bucketName, bucketPath, filePath);
            Map<String, String> resp = new HashMap<>();
            resp.put("url", url);
            System.out.println(url);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
