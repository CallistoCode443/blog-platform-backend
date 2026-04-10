package com.example.blogplatform.property;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "app.cookie")
public class CookieProperties {
    private String accessTokenName;
    private String refreshTokenName;
    private String path;
    private String refreshPath;
    private Duration maxAge;
}
