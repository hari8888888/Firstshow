package com.example.BookMyShow.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class WebConfig implements WebMvcConfigurer { //WebMvcConfigurer we can able to implement cors configuration

    @Override
    public void addCorsMappings(CorsRegistry registry) { //corsregistery allow to use methods like allowedheaders etc
        // Allow cross-origin requests from http://127.0.0.1:5500 (your frontend)
        registry.addMapping("/**")
                .allowedOrigins( //it wont allow to call an API from one domain to another domain, only these two will permit
                        "http://127.0.0.1:5500",
                        "https://firstshow.vercel.app/"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Value("${jwt.secret}")
    private String secret; //we get this secret code from application.prop 

    @Bean
    public SecretKey secretKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret); //decodes that string into raw bytes, its a standard way
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256"); //wraps up raw bytes into secretkey object suitable for hmac
    }
}
