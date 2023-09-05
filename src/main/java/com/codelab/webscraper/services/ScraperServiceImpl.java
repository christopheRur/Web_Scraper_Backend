package com.codelab.webscraper.services;

import com.codelab.webscraper.model.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class ScraperServiceImpl implements ScraperService{

    public Scraper scrapeWebsite(Scraper scraper){

        try{

            Document doc = Jsoup.connect(scraper.getUrl()).get();

            String title = doc.title();

            String htmlBody =doc.body().data();

            scraper.setTitle(title);
            scraper.setStatus(200);
            scraper.setBody(htmlBody);
            scraper.setTime(new Date().toString());

            log.info("----Scraper----=====>{}",title.toUpperCase());

            return scraper;

        }
        catch(JSONException exception){
            log.info("Error=------- {}",exception.getMessage());


        return scraper;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
