package com.dfortch.rarebook.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "build")
@Getter
@Setter
public class BuildProperties {

    private String artifactId;

    private String version;

    private String name;

    private String description;
}
