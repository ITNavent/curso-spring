package com.navent.spring.reader;

import java.util.List;

import org.springframework.stereotype.Service;

import com.navent.spring.model.Posting;

@Service("csvReader")
public class CSVPostingsReader implements FilePostingsReader {

	@Override
	public List<Posting> readPostings(String filePath) {
		return null;
	}

}
