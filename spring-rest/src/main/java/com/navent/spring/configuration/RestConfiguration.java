package com.navent.spring.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableCaching
@EnableSwagger2
public class RestConfiguration {

	@Value("${configuration.api.rest.readTimeout:2000}")
	private int readTimeout;
	
	@Value("${configuration.api.rest.connectionTimeout:2000}")
	private int connectionTimeout;
	
	@Autowired
	private UserAgentInterceptor userAgentInterceptor;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ObjectMapper jacksonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}
	

	@Bean // http://www.ehcache.org/documentation/2.8/integrations/spring.html 
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}
	
	
	@Bean // http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("com.navent.spring.controller"))
					.paths(PathSelectors.any())
					.build()
					.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	     return new ApiInfo(
	       "Spring REST API", 
	       "API de ejemplo para Charla de Spring Framework", 
	       "v0.0.1", 
	       "Terms of service", 
	       new Contact("sclinis", "www.navent.com", "sclinis@navent.com"), 
	       "License of API",
	       "API license URL");
	}
	
	
	@Bean
	public RestTemplate apiTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
				//.setConnectTimeout(connectionTimeout)
				//.setReadTimeout(readTimeout)
				.additionalInterceptors(userAgentInterceptor)
				.build();
	}

}
