package com.cromxt.cloudstore.dtos.requests;

import org.springframework.http.codec.multipart.FilePart;

public record FileUploadRequest(
        FilePart mediaObject
) {
}