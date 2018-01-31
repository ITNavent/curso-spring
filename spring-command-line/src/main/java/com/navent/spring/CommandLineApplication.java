package com.navent.spring;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.navent.spring.processor.PostingsProcessor;
import com.navent.spring.reader.FilePostingsReader;

@SpringBootApplication 	// https://spring.io/guides/gs/spring-boot/
@ComponentScan("com.navent")
public class CommandLineApplication implements CommandLineRunner { // implementando CommandLineRunner hace que sea un programa comando
	
	@Autowired
	@Qualifier("jsonReader") // Indica una implementación en particular (para cuando hay mas de una implementación o subclase)
							 // Tambien se puede marcar con @Primary cuando hay mas de una implementación
	private FilePostingsReader reader;
	
	@Value("#{{'FraudPostings':@FraudPostingsProcessor, 'DuplicatePostings':@DuplicatePostingsProcessor}}") //se puede hacer referencia a un Bean con @
	private Map<String, PostingsProcessor> processors;
	
	public static void main(String[] args) {
		SpringApplication.run(CommandLineApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		String inputFile = null;
		String task = null;
		String outputFile = null;
		
		for (String arg : args) {
			if(StringUtils.startsWith(arg, "--file=")) {
				inputFile = StringUtils.substringAfter(arg, "--file=");
			}
			if(StringUtils.startsWith(arg, "--task=")) {
				task = StringUtils.substringAfter(arg, "--task=");
			}
			if(StringUtils.startsWith(arg, "--out=")) {
				task = StringUtils.substringAfter(arg, "--out=");
			}
		}
		
		if(inputFile == null && task == null) {
			System.err.println("You must specify filepath and task");
			System.exit(1);
		}
		if(!processors.containsKey(task)) {
			System.err.println("Invalid task name");
			System.exit(1);
		}
		
		if(outputFile == null) {
			outputFile = StringUtils.substringBeforeLast(inputFile, ".") + "_out." + StringUtils.substringAfterLast(inputFile, ".");  
		}
		
		// preocess file
		processors.get(task).process(outputFile, reader.readPostings(inputFile));
		
		System.out.println("El proceso finalizó correctamente");
		System.exit(0);
		
	}
	
}
