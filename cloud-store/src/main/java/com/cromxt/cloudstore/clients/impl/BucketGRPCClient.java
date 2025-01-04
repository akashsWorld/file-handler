package com.cromxt.cloudstore.clients.impl;

import com.cromxt.cloudstore.clients.BucketClient;
import com.cromxt.cloudstore.dtos.response.MediaObjectDetails;
import com.cromxt.dtos.response.BucketDetails;
import com.cromxt.files.proto.HLSStatus;
import com.cromxt.files.proto.MediaHandlerServiceGrpc;
import com.cromxt.files.proto.MediaUploadRequest;
import com.cromxt.files.proto.ReactorMediaHandlerServiceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
public class BucketGRPCClient implements BucketClient {

    @Override
    public Mono<MediaObjectDetails> uploadFile(Flux<DataBuffer> fileData,
                                               MediaObjectDetails mediaObjectDetails,
                                               BucketDetails bucketDetails) {
        ReactorMediaHandlerServiceGrpc.ReactorMediaHandlerServiceStub reactorMediaHandlerServiceStub =
                getReactorMediaHandlerServiceStub(
                        bucketDetails,
                        generateHeaders(bucketDetails)
                );

        Flux<MediaUploadRequest> data = fileData
                .map(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            return MediaUploadRequest
                                    .newBuilder()
                                    .setFile(ByteString.copyFrom(bytes))
                                    .build();
                        }
                );
        return data.as(reactorMediaHandlerServiceStub.withInterceptors(
//                TODO:Add the interceptor for the grpc call.
                )::uploadFile)
                .flatMap(
                        fileUploadResponse ->
//                                TODO: Handle the response.
                                Mono.empty()
                );
    }

    private MediaHandlerServiceGrpc.MediaHandlerServiceStub getMediaHandlerServiceStub(BucketDetails bucketDetails) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(
                        bucketDetails.url(),
                        bucketDetails.port()
                )
                .usePlaintext()
                .build();
        return MediaHandlerServiceGrpc.newStub(channel);
    }


    private Metadata generateHeaders(BucketDetails bucketDetails) {
        return null;
    }
//    Use of reactive implementation instead of blocking.
    private ReactorMediaHandlerServiceGrpc.ReactorMediaHandlerServiceStub getReactorMediaHandlerServiceStub(
            BucketDetails bucketDetails,
            Metadata headers
    ) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress(
                        bucketDetails.url(),
                        bucketDetails.port()
                )
                .intercept(MetadataUtils.newAttachHeadersInterceptor(headers))
                .usePlaintext()
                .build();

//        Channel interceptedChannel = ClientInterceptors.intercept(managedChannel, )
        return ReactorMediaHandlerServiceGrpc.newReactorStub(managedChannel);
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class MediaObjectDetails{
        private String contentType;
        private HLSStatus hlsStatus;
    }
}
