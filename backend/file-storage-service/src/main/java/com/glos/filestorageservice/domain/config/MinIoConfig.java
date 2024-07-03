package com.glos.filestorageservice.domain.config;

import com.glos.filestorageservice.domain.config.props.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIoConfig {

    @Bean
    public MinioClient minioClient(MinioProperties minioProps) {
        return MinioClient.builder()
                .endpoint(minioProps.getUrl())
                .credentials(minioProps.getAccessKey(), minioProps.getSecretKey())
                .build();
    }

}
