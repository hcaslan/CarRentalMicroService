package org.hca.constant;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnvironmentProperties {
    private final Environment environment;

    public String getPort() {
        return environment.getProperty("server.port");
    }
}
