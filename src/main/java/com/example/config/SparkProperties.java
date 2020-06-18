package com.example.config;

import com.example.util.SparkConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Component
public class SparkProperties {

    private final Properties properties;
    private static final String TEMP_DIR = "C:/tmp";

    public SparkProperties(@Value("${spring.application.name}") final String appName, @Value("${spring.profiles.active:local}") final String profile) {
        log.info("Application Name: {}", appName);
        String givenProfile = Optional.ofNullable(profile).orElse(SparkConstant.LOCAL);
        log.info("Profiles: {}", givenProfile);

        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        if(StringUtils.isNotBlank(profile)) {
            if(profile.equalsIgnoreCase("test")) {
                yamlPropertiesFactoryBean.setResources(new ClassPathResource("spark-test.yml"), new ClassPathResource("kafka-test.yml"));
                try {
                    FileUtils.deleteDirectory(new File(TEMP_DIR));
                } catch (Exception e) {
                    log.error("Deleting Spark Temp Directory", e);
                }
            } else {
                yamlPropertiesFactoryBean.setResources(new ClassPathResource("spark.yml"), new ClassPathResource("kafka.yml"));
            }
        }
        this.properties = yamlPropertiesFactoryBean.getObject();
    }

    public String getPropertyValue(final String key) {
        return properties.getProperty(key);
    }
}
