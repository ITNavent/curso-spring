package com.navent.spring.model;

public class PublisherScore {
	
	private Long score;
	private Long from;
	private Long to;
	public Long getScore() {
		return score;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public Long getFrom() {
		return from;
	}
	public void setFrom(Long from) {
		this.from = from;
	}
	public Long getTo() {
		return to;
	}
	public void setTo(Long to) {
		this.to = to;
	}
	
	public boolean applies(Long postingCount) {
		return postingCount >= from && postingCount <= to;
	}
}
