package io.github.jkymmel.idu0075.petclinic.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.api")
@Data
public class SecurityConfig {
    private String key;
}
