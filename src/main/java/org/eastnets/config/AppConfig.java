package org.eastnets.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("org.eastnets")
@EnableWebMvc
@ComponentScan(basePackages="org.eastnets.*")
@EnableJpaRepositories(basePackages = "org.eastnets.*")
public class AppConfig {




}