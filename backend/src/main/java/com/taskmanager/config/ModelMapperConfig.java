package com.taskmanager.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Diese Klasse stellt die Spring-Configuration für ModelMapper bereit.
 * Sie definiert ein ModelMapper-Bean, das für Object-Object-Mapping (DTO <-> Entity)
 * genutzt wird.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().
                setFieldMatchingEnabled(true).
                setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE).
                setMatchingStrategy(MatchingStrategies.STRICT); // --> nur wenn Name und Typ übereinstimmen
        return mm;
    }
}