package com.cromxt.grpc;


import com.cromxt.files.proto.MediaMetaData;
import io.grpc.Context;
import io.grpc.Metadata;
import lombok.Getter;

public class MediaHeadersKey {

    public static final HeaderKeyValue<MediaMetaData> MEDIA_META_DATA = new HeaderKeyValue<>("media-details", HeaderType.BINARY);

    @Getter
    public static class HeaderKeyValue <T>{
        private final String keyIdentifier;
        private final Metadata.Key<?> metaDataKey;
        private final Context.Key<T> contextKey;

        public HeaderKeyValue(
                String keyIdentifier,
                HeaderType headerType
        ) {
            this.keyIdentifier = keyIdentifier;
            if(headerType == HeaderType.STRING){
                this.metaDataKey = Metadata.Key.of(keyIdentifier, Metadata.ASCII_STRING_MARSHALLER);
            }else {
                this.metaDataKey = Metadata.Key.of(String.format("%s-bin", keyIdentifier), Metadata.BINARY_BYTE_MARSHALLER);
            }
            this.contextKey = Context.key(keyIdentifier);
        }

    }

    public enum HeaderType{
        STRING,
        BINARY
    }
}