package com.hyundai.eventlogging2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@PropertySource("file:config/config.properties")
public class SwaggerConfig implements WebMvcConfigurer {
   
    @Value("${server.port}")
    private static String serverPort;
    private static final String SERVICE_NAME = "EventLogging";
    private static final String API_VERSION = "V1";
    private static final String API_DESCRIPTION = "EventLogging API TEST";
    private static final String API_IP = "http://localhost:";
    private static final String API_URL = API_IP + serverPort;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any()) // RequestMapping의 모든 URL LIST
                .paths(PathSelectors.any()) // .any() -> ant(/api/**") /api/**인 URL만 표시
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(SERVICE_NAME) // 서비스명
                .version(API_VERSION)                   // API 버전
                .description(API_DESCRIPTION)           // API 설명
                .termsOfServiceUrl(API_URL)             // 서비스 url
                .licenseUrl("라이센스 표시할 url")
                .build();

    }// API INFO
    
    // 아래 부분은 WebMvcConfigure 를 상속받아서 설정하는 Mehtod 
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // -- Static resources
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }
}