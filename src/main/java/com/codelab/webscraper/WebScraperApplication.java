package com.codelab.webscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class WebScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebScraperApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(Boolean.TRUE);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers", "id"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    /**
     *  @Bean
     *     public CorsFilter corsFilter() {
     *         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     *         CorsConfiguration config = new CorsConfiguration();
     *         config.setAllowCredentials(true);
     *         config.addAllowedOrigin("*");
     *         config.addAllowedHeader("*");
     *         config.addAllowedMethod("OPTIONS");
     *         config.addAllowedMethod("GET");
     *         config.addAllowedMethod("POST");
     *         config.addAllowedMethod("PUT");
     *         config.addAllowedMethod("DELETE");
     *         source.registerCorsConfiguration("/**", config);
     *         return new CorsFilter(source);
     *     }
     */

}
