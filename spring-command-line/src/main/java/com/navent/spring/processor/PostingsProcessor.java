package com.navent.spring.processor;

import java.util.List;

import com.navent.spring.model.Posting;

public interface PostingsProcessor {
	void process(String output, List<Posting> postings);
}
