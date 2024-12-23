package com.cf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {

    private boolean enable;

    private String secret;

    private long expire;

    private String header;

    private String whiteUrls;

    private String whiteTokens;

}

