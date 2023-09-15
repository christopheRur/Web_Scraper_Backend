package com.codelab.webscraper.controller;

import com.codelab.webscraper.model.Scraper;
import com.codelab.webscraper.services.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WebScraperController {

    private ScraperService scraperServ;

    public WebScraperController(ScraperService scraperServ) {
        this.scraperServ = scraperServ;
    }

    @PostMapping("/fetch")
    public ResponseEntity<?> fetchKeyWords(@RequestBody Scraper scrapedBody) {

        try {
            if (scrapedBody == null) {
                scrapedBody.setStatus(400);
                return ResponseEntity.badRequest().body("NoScrapedDataFound!");

            } else {

                scrapedBody.setStatus(200);
                return new ResponseEntity<>(scraperServ.scrapeWebsite(scrapedBody), HttpStatus.OK);}

        } catch (Exception e) {

            log.error("==>" + e.getLocalizedMessage());

            scrapedBody.setStatus(400);
            return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
        }


    }
}
