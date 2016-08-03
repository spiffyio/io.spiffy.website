package io.spiffy.media.manager;

import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

import io.spiffy.common.Manager;
import io.spiffy.common.config.AppConfig;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MediaManager extends Manager {

    private static final String BUCKET = "spiffyio" + AppConfig.getSuffix();

    private final AmazonS3Client client;

    public byte[] get(final String key) {
        try {
            final S3Object object = getObject(key);
            object.getObjectContent();
            final byte[] bytes = IOUtils.toByteArray(object.getObjectContent());
            return bytes;
        } catch (final IOException e) {
        }

        return null;
    }

    public void put(final String key, final byte[] value, final String contentType, final String md5) {
        if (key == null) {
            return;
        }

        InputStream is = null;
        try {
            is = new ByteArrayInputStream(value);
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setCacheControl("public, max-age=604800");
            metadata.setContentLength(value.length);
            metadata.setContentMD5(md5);
            metadata.setContentType(contentType);

            final PutObjectRequest request = new PutObjectRequest(BUCKET, key, is, metadata);
            client.putObject(request);
        } catch (final AmazonServiceException e) {
            e.printStackTrace();
        } catch (final AmazonClientException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (final IOException e) {
                }
            }
        }
    }

    public void delete(final String key) {
        if (key == null) {
            return;
        }

        try {
            client.deleteObject(BUCKET, key);
        } catch (final AmazonServiceException e) {
        } catch (final AmazonClientException e) {
        }
    }

    private S3Object getObject(final String key) {
        if (key == null) {
            return null;
        }

        try {
            return client.getObject(BUCKET, key);
        } catch (final AmazonServiceException e) {
        } catch (final AmazonClientException e) {
        }

        return null;
    }
}
