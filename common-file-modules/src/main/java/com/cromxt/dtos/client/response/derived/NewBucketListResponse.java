package com.cromxt.dtos.client.response.derived;

import com.cromxt.dtos.client.response.BucketResponse;
import com.cromxt.dtos.client.response.BucketResponseDTO;
import com.cromxt.dtos.client.response.ResponseState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewBucketListResponse extends BucketResponse {
    private List<BucketResponseDTO> buckets;

    public NewBucketListResponse(ResponseState status, List<BucketResponseDTO> buckets) {
        super(status);
        this.buckets = buckets;
    }
}
