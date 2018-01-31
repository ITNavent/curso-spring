package com.navent.spring.processor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.navent.spring.model.Posting;

@Service("DuplicatePostingsProcessor")
public class DuplicatePostingsProcessor implements PostingsProcessor {

	@Override
	public void process(String output, List<Posting> postings) {
	}


}
