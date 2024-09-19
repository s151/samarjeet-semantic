package com.semanticdataquery.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
public class FileUploadConfig {
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // Set max file size to 2MB
        factory.setMaxFileSize(DataSize.parse("4MB"));
        // Set max request size to 2MB
        factory.setMaxRequestSize(DataSize.parse("4MB"));
        return factory.createMultipartConfig();
    }
}
