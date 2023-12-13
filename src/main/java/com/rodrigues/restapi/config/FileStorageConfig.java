package com.rodrigues.restapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
//usar prefixo file para pegar as propriedades do arquivo application.yml
public class FileStorageConfig {
    private String uploadDir;

    public String getUploadDir(){
        return uploadDir;
    }

    public void setUploadDir(String uploadDir){
        this.uploadDir = uploadDir;
    }
}
