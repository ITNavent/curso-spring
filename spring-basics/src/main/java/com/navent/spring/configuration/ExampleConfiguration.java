package com.navent.spring.configuration;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration // Es una clase contenedora donde se definen los Beans
@PropertySource("classpath:configuration.properties") // sirve para externalizar la configuracion en un .properties (se puede agregar una lista)
public class ExampleConfiguration {

	@Value("async-worker-") // define el valor de un field dentro de un Bean.  
	private String threadPrefix;

	@Value("${configuration.executor.poolSize:100}") // define el valor configurado en un properties, si no lo encuentra toma un default
	private Integer poolSize;

	
	@Bean // El objeto que retorna queda registrado como Bean en el contexto de aplicación de spring 
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages/messages");
		return messageSource;
	}

	@Bean
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(poolSize);
		executor.setMaxPoolSize(poolSize);
		executor.setThreadNamePrefix(threadPrefix);
		executor.initialize();
		return executor;
	}

}
