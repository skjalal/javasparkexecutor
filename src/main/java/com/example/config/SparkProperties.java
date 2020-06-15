package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Optional;
import java.util.Properties;

@Slf4j
public class SparkProperties {

    private final Properties properties;

    public SparkProperties(final String appName, final String profile) {
        log.info("Application Name: {}", appName);
        String givenProfile = Optional.ofNullable(profile).orElse("");
        log.info("Profiles: {}", givenProfile);

        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("application.yml"));
        this.properties = yamlPropertiesFactoryBean.getObject();
    }

    public String getPropertyValue(final String key)  {
        return properties.getProperty(key);
    }
}
