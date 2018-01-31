package com.navent.spring.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.navent.spring.configuration.Config;
import com.navent.spring.model.Fraud;
import com.navent.spring.model.Operation;
import com.navent.spring.model.Posting;
import com.navent.spring.model.Publisher;
import com.navent.spring.model.StatusEnum;


@RunWith(SpringRunner.class)
@SpringBootTest //https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4
public class FraudServiceTest {

	@MockBean
	private Config config;
	
	@Autowired
	private FraudService fraudService;
	
	@Before
	public void init() {
		Mockito.when(config.getEnabled()).thenReturn(true);
	}

	@Test
	public void testPostingOk() {
		Posting posting = buildPostingTest();
		Fraud fraudInfo = fraudService.getFraudInfo(posting);
		Assert.assertFalse(fraudInfo.getIsFraud());
		Assert.assertTrue(fraudInfo.getMessages().isEmpty());
	}
	
	@Test
	public void testCheapPrice() {
		Posting posting = buildPostingTest();
		posting.getOperation().setPrice(1L);
		
		Fraud fraudInfo = fraudService.getFraudInfo(posting);
		Assert.assertTrue(fraudInfo.getIsFraud());
		Assert.assertEquals(1, fraudInfo.getMessages().size());
		Assert.assertEquals("El aviso es mas barato que 100.", fraudInfo.getMessages().get(0));
	}
	
	@Test
	public void testDescriptionBr() {
		Posting posting = buildPostingTest();
		posting.setSite("IWBR");
		posting.setDescription("Ud puede pagar el aviso a traves de mercadopago las 24hs del día");
		Fraud fraudInfo = fraudService.getFraudInfo(posting);
		Assert.assertTrue(fraudInfo.getIsFraud());
		Assert.assertEquals(1, fraudInfo.getMessages().size());
		Assert.assertEquals("A descrição contém palavras duvidosas.", fraudInfo.getMessages().get(0));
		
	}
	
	private Posting buildPostingTest() {
		Posting p = new Posting();
		p.setPostingId(11111L);
		p.setDescription("Descripcion de ejemplo");
		p.setCreationDate(new Date());
		p.setSite("ZPAR");
		p.setStatus(StatusEnum.ONLINE);
		p.setType("property");
		Publisher publisher = new Publisher();
		publisher.setPublisherId(999L);
		publisher.setPostingCount(10L);
		p.setPublisher(publisher);
		Operation operation = new Operation();
		operation.setOperation("venta");
		operation.setCurrency("usd");
		operation.setPrice(10000L);
		p.setOperation(operation);
		return p;
	}
}
