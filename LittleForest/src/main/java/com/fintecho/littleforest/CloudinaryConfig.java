package com.fintecho.littleforest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dkaeihkco",
            "api_key", "662342265999261",
            "api_secret", "-86habTviGyU7xmaDoI9ERV-X3E"
        ));
    }
}