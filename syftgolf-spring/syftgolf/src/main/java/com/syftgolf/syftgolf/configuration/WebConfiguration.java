package com.syftgolf.syftgolf.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

//check for web config
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer{

    @Autowired
    AppConfiguration appConfiguration;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + appConfiguration.getUploadPath() + "/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Bean
    CommandLineRunner createUploadFolder() {
        return (args) -> {

            createNonExistingFolder(appConfiguration.getUploadPath());
            createNonExistingFolder(appConfiguration.getFullProfileImagesPath());
            createNonExistingFolder(appConfiguration.getFullAttachmentsPath());
        };
    }

    private void createNonExistingFolder(String path) {
        File folder = new File(path);
        boolean folderExist = folder.exists() && folder.isDirectory();
        if(!folderExist) {
            folder.mkdir();
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}
