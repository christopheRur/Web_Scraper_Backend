package com.codelab.webscraper.controller;

import com.codelab.webscraper.model.Scraper;
import com.codelab.webscraper.services.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.OutputKeys;

@Slf4j
@RestController
public class WebScraperController {

    private ScraperService scraperServ;

    public WebScraperController(ScraperService scraperServ) {
        this.scraperServ = scraperServ;
    }

    @PostMapping("/fetch")
    public ResponseEntity<?> fetchKeyWords(@RequestBody Scraper scrapedBody){

        try{
            if(scrapedBody==null){

            return ResponseEntity.badRequest().body("NoScrapedDataFound!");

        }
        else return new ResponseEntity<>(scraperServ.scrapeWebsite(scrapedBody), HttpStatus.OK);

        }
        catch(Exception e){

            log.error("==>"+e.getLocalizedMessage());

            return ResponseEntity.badRequest().body("Error occurred, unable to FETCH data.");
        }


    }
}
