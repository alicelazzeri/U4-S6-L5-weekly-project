package it.epicode.U4_S6_L5_weekly_project.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary uploader() {

        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "cloudinary_name");
        config.put("api_key", "cloudinary_api_key");
        config.put("api_secret", "cloudinary_secret");
        return new Cloudinary(config);
    }
}
