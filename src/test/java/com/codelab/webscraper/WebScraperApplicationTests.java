package com.codelab.webscraper;

import com.codelab.webscraper.controller.WebScraperController;
import com.codelab.webscraper.model.Scraper;
import com.codelab.webscraper.services.ScraperServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class WebScraperApplicationTests {

	@Mock
	Scraper scraper;
	@Mock
	ScraperServiceImpl service;
	@InjectMocks
	WebScraperController controller;

	@Test
	public void testFetchWords(){

		Scraper sc=new Scraper();
		sc.setStatus(200);
		sc.setKeyWordOne("test1");
		sc.setKeyWordTwo("test2");
		sc.setKeyWordThree("test3");

		Mockito.when(service.scrapeWebsite(sc)).thenReturn(new Scraper());
		ResponseEntity<?> response = controller.fetchKeyWords(service.scrapeWebsite(sc));

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void contextLoads() {
	}


}
