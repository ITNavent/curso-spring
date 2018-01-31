package com.navent.spring.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.navent.spring.model.Posting;
import com.navent.spring.reader.exception.ReadPostingException;

@Service("jsonReader")
public class JsonPostingsReader implements FilePostingsReader {

	private static final Logger log = LoggerFactory.getLogger(JsonPostingsReader.class);
	
	private Gson gson;
	
	@Override
	public List<Posting> readPostings(String filePath) {

		File file = new File(filePath);
		if(!file.exists() || !file.isFile()) {
			log.error(String.format("File %s not exists", filePath));
			throw new ReadPostingException("File not exists");
		}
		
		List<Posting> ret = new ArrayList<>();
		
		try {
			ret = Files.lines(Paths.get(filePath))
						.map(this::getPosting)
						.filter(Objects::nonNull)
						.collect(Collectors.toList());
		} catch (IOException e) {
			log.error("Error reading file", e);
			throw new ReadPostingException("Error reading json postings from file", e);
		}
		
		return ret;
	}
	
	private Posting getPosting(String json) {
		if(StringUtils.isBlank(json)) {
			return null;
		}
		try {
			return gson.fromJson(json, Posting.class);
		}catch (JsonSyntaxException e) {
			log.error(String.format("Error parsing json %s", json), e);
		}
		return null;
	}
	

	@PostConstruct
	public void init() {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		gson = builder.create();
	}

}
