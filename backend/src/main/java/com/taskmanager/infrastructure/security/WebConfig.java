package com.taskmanager.infrastructure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Cross origin resource sharing
     * = Protokoll, was Webbrowsern erlaubt auf andere Ressourcen zuzugreifen
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**"). // erlaubt cors für alle pfade der Anwendung
                allowedOrigins("http://localhost:4200"). // erlaubt nur Anfragen aus dieser Herkunft --> das ist unser Angular frontend
                allowedMethods("GET", "POST", "PUT", "DELETE"). // welche HTTP Methoden CORS untersützt werden
                allowedHeaders("*"). // erlaubt alle HTTP Header in CORS-Anfragen
                allowCredentials(true);
    }

}
