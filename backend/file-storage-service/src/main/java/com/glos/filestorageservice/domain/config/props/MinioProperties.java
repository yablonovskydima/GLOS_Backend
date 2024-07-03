package com.glos.filestorageservice.domain.config.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    private String bucketPrefix;
    private String url;
    private String accessKey;
    private String secretKey;
    private String expiry;

    public MinioProperties(String bucketPrefix, String url, String accessKey, String secretKey, String expiry) {
        this.bucketPrefix = bucketPrefix;
        this.url = url;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.expiry = expiry;
    }

    public MinioProperties() {
    }

    public String getBucketPrefix() {
        return bucketPrefix;
    }

    public void setBucketPrefix(String bucket) {
        this.bucketPrefix = bucket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
