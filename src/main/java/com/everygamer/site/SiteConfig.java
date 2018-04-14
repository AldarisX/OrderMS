package com.everygamer.site;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "site.config")
public class SiteConfig {
    @Getter
    @Setter
    private String dbVer;
}
