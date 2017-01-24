package io.spiffy.common.mock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

public class AmazonS3ClientMock extends AmazonS3Client {

    private final static Map<String, S3Object> data = new HashMap<>();

    @Override
    public S3Object getObject(final String bucket, final String key) {
        final S3Object object = data.get(getKey(bucket, key));
        if (object == null) {
            return null;
        }

        if (object.getObjectContent() == null) {
            return object;
        }

        try {
            object.getObjectContent().reset();
        } catch (final IOException e) {
        }

        return object;
    }

    @Override
    public PutObjectResult putObject(final PutObjectRequest request) {
        final S3Object object = new S3Object();
        object.setObjectContent(request.getInputStream());

        data.put(getKey(request.getBucketName(), request.getKey()), object);

        return null;
    }

    private String getKey(final String bucket, final String key) {
        return String.format("%s:%s", bucket, key);
    }
}