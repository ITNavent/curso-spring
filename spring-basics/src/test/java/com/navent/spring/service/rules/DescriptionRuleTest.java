package com.navent.spring.service.rules;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.navent.spring.Application;
import com.navent.spring.model.Posting;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Application.class})
public class DescriptionRuleTest {
	
	@Autowired
	private DescriptionRule descriptionRule;
	
	@Test
	public void testDescriptionOk() {
		Posting posting = getPostingMock("Descripción de prueba");
		Assert.assertFalse(descriptionRule.applies(posting));
	}

	@Test
	public void testDescriptionFraud() {
		Posting posting = getPostingMock("Descripción de paypal prueba");
		Assert.assertTrue(descriptionRule.applies(posting));
	}
	
	private Posting getPostingMock(String description) {
		Posting posting = Mockito.mock(Posting.class);
		Mockito.when(posting.getDescription()).thenReturn(description);
		return posting;
	}
	
}
