package com.navent.spring.dao;

public interface SequenceDao {
	public Long getNextSequenceId(String key);
}
