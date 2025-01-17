package com.cromxt.bucket.interceptors;

import com.cromxt.files.proto.MediaMetaData;
import static com.cromxt.grpc.MediaHeadersKey.MEDIA_META_DATA;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MediaHandlerInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers,
                                                                 ServerCallHandler<ReqT, RespT> next) {
        MediaMetaData metaData = null;
        Metadata.Key<?> fileExtensionKey = MEDIA_META_DATA.getMetaDataKey();

        if (headers.containsKey(fileExtensionKey)) {
            {
                try {
                    byte[] metaDataBytes = headers.get((Metadata.Key<byte[]>) fileExtensionKey);
                    metaData = MediaMetaData.parseFrom(metaDataBytes);
                    Context currentContext = Context.current().withValue(MEDIA_META_DATA.getContextKey(), metaData);
                    return Contexts.interceptCall(currentContext, call, headers, next);
                } catch (Exception e) {
                    log.error("Unable to parse media meta data", e);
                    call.close(Status.INTERNAL.withDescription("Unable to parse media meta data"), headers);
                }
            }
        }
        return new ServerCall.Listener<ReqT>() {
        };
    }
}