package joel.fsms.config.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
    public class SwaggerUiConfiguration {
        @Bean
        public Docket docket() {
            TypeResolver typeResolver = new TypeResolver();
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .select()
                    .paths(PathSelectors.ant("/api/**"))
                    .build()
                    .alternateTypeRules(new AlternateTypeRule(typeResolver.resolve(List.class, ZonedDateTime.class), typeResolver.resolve(List.class, String.class)))
                    .securityContexts(securityContexts())
                    .securitySchemes(apiKey());
        }

        private List<SecurityContext> securityContexts() {
            SecurityReference securityReference = new SecurityReference("Authorization", new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")});
            SecurityContext securityContext = SecurityContext.builder().securityReferences(Collections.singletonList(securityReference)).build();
            return Collections.singletonList(securityContext);
        }

        private ApiInfo apiInfo() {
            Contact contact = new Contact("Joel Augusto", "https://www.linkedin.com/in/joelaugusto97",
                    "joelaugusto97@gmail.com");
            return new ApiInfo(
                    "Food Supply Management System API",
                    "Trabalho de Licenciatura, UEM, 2022.",
                    "1.0.0",
                    null,
                    contact,
                    null,
                    null,
                    new ArrayList<>());
        }

        private List<SecurityScheme> apiKey() {
            ApiKey authorizationHeader = new ApiKey("Authorization", "Authorization", "header");
            return Collections.singletonList(authorizationHeader);
        }
    }
