package com.navent.spring.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.navent.spring.dao.SequenceDao;
import com.navent.spring.model.SequenceId;

@Repository // Component de Spring para DAO (Clases que acceden a la BBDD). 
public class SequenceDaoImpl implements SequenceDao {

	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Override
	public Long getNextSequenceId(String key) {
		// get sequence id
		Query query = new Query(Criteria.where("_id").is(key));

		// increase sequence id by 1
		Update update = new Update();
		update.inc("seq", 1);

		// return new increased id
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);
		SequenceId seqId = mongoTemplate.findAndModify(query, update, options, SequenceId.class);
		return seqId.getSeq();
	}

}
