package com.glos.accessservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Access-service API",
                description = "This API allows to set access type to files for different users.",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://Licence-url"
                ),
                termsOfService = "terms of service"
        ),
        servers =
        @Server(
                description = "Local",
                url = "https://localhost:9011"
        ),
        security =
        @SecurityRequirement(
                name = "barerAuth"
        )

)
@SecurityScheme(
        name = "bearer",
        description = "JWT description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {
}
