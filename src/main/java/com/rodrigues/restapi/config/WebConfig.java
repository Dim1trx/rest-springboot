package com.rodrigues.restapi.config;

import com.rodrigues.restapi.serializationConverter.YamlJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final MediaType MEDIA_TYPE_YML = MediaType.valueOf("application/x-yaml");

/*    //variables from application.yaml
    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "";*/

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }

 /*   @Override
    public void addCorsMappings(CorsRegistry registry) {
        var allowedOrigins = corsOriginPatterns.split(", ");

        registry.addMapping("/**")
                *//*permite a escolha de qual metodos sao permitidos
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")*//*
                .allowedMethods("*") // permite todos os metodos/verbos http
                *//*.allowedHeaders("*") // permite todos os headers;*//*
                .allowedOriginPatterns(allowedOrigins)
                .allowCredentials(true);

    }*/

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /* // via Query param http://localhost:8080/api/person/v1?mediaType=xml
        configurer.favorParameter(true)
                .parameterName("mediaType").ignoreAcceptHeader(true)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
        *//*.mediaType("x-yaml", MediaType.valueOf("application/x-yaml"))*//*;*/

        // via Header param http://localhost:8080/api/person/v1
        configurer.favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("X-yaml", MEDIA_TYPE_YML);

    }
}
