package com.navent.spring.reader;

import java.util.List;

import com.navent.spring.model.Posting;

public interface FilePostingsReader {
	public List<Posting> readPostings(String filePath);
}
