package com.cromxt.bucket.service.impl;


import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class GRPCServerEventHandlers {

    private final Server server;

    public GRPCServerEventHandlers(@Qualifier("mediaHandlerGrpcServer") Server server) {
        this.server = server;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartApplication () throws IOException {
        server.start();
        log.info("Bucket started successfully on Port {}", server.getPort());
    }

    @EventListener(value = ContextClosedEvent.class)
    public void onShutdown() throws InterruptedException {
        server.shutdown().awaitTermination();
        log.info("Bucket stopped successfully");
    }
}
