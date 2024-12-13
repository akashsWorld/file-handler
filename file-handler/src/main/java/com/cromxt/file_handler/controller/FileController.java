package com.cromxt.file_handler.controller;

import com.cromxt.file_handler.clients.BucketClient;
import com.cromxt.file_handler.dtos.requests.FileUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final BucketClient bucketServerClient;

    @PostMapping(value = "/upload")
    public Mono<ResponseEntity<Void>> uploadFile(@ModelAttribute FileUploadRequest fileUploadRequest) {
        bucketServerClient
                .uploadFile(fileUploadRequest.mediaObject(),String.valueOf(1))
                .subscribe(System.out::println);
        return Mono.just(ResponseEntity.ok().build());
    }
}
