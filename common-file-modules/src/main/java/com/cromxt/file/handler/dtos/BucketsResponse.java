package com.cromxt.file.handler.dtos;

public record BucketsResponse(
    String id,
    String hostname,
    Integer port
) {
}