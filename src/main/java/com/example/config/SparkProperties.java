package com.example.config;

import com.example.util.SparkConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Properties;

@Slf4j
@Component
public class SparkProperties {

    private final Properties properties;

    public SparkProperties(@Value("${spring.application.name}") final String appName, @Value("${spring.profiles.active:local}") final String profile) {
        log.info("Application Name: {}", appName);
        String givenProfile = Optional.ofNullable(profile).orElse(SparkConstant.LOCAL);
        log.info("Profiles: {}", givenProfile);

        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        String sparkProfile = "spark-" + profile + ".yml";
        String kafkaProfile = "kafka-" + profile + ".yml";
        yamlPropertiesFactoryBean.setResources(new ClassPathResource(sparkProfile), new ClassPathResource(kafkaProfile));
        this.properties = yamlPropertiesFactoryBean.getObject();
    }

    public String getPropertyValue(final String key) {
        return properties.getProperty(key);
    }
}
