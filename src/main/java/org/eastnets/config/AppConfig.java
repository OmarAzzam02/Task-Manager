package org.eastnets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration class for setting up Swagger documentation and JPA in the application.
 * <p>
 * This configuration:
 * <ul>
 *     <li>Enables component scanning for Spring components in the "org.eastnets" package.</li>
 *     <li>Enables JPA repositories for data access in the "org.eastnets.*" package.</li>
 *     <li>Enables transaction management.</li>
 *     <li>Configures Swagger 2 for API documentation and includes JWT authentication support.</li>
 * </ul>
 */
@Configuration
@ComponentScan("org.eastnets")
@EnableJpaRepositories(basePackages = "org.eastnets.*")
@EnableTransactionManagement
@EnableSwagger2
public class AppConfig {

    /**
     * Bean definition for Swagger Docket configuration.
     * <p>
     * Configures Swagger 2 to scan the "org.eastnets" package for REST controllers and
     * sets up JWT authentication for the API documentation.
     *
     * @return a {@link Docket} instance configured for Swagger 2.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.eastnets"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    /**
     * Defines the API key used for JWT authentication in Swagger.
     * <p>
     * Configures Swagger to expect JWT tokens in the "Authorization" header.
     *
     * @return an {@link ApiKey} instance configured for JWT authentication.
     */
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    /**
     * Configures the security context for Swagger to apply JWT authentication.
     * <p>
     * Applies the security context to all API paths in Swagger documentation.
     *
     * @return a {@link SecurityContext} instance configured with JWT authentication.
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    /**
     * Provides the default security references for JWT authentication.
     * <p>
     * Sets up the authorization scope for Swagger UI to use JWT tokens.
     *
     * @return a list of {@link SecurityReference} instances configured for JWT authentication.
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
