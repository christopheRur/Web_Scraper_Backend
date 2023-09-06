package com.codelab.webscraper.services;

import com.codelab.webscraper.model.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Slf4j
@Service
public class ScraperServiceImpl implements ScraperService {
    /**
     *  Set values to keys
     * @param scraper
     * @param pageContent
     */
    private void setKeysValues(Scraper scraper, String pageContent) {

        scraper.setKey1Found(pageContent.contains(scraper.getKeyWordOne()));
        scraper.setKey2Found(pageContent.contains(scraper.getKeyWordTwo()));
        scraper.setKey3Found(pageContent.contains(scraper.getKeyWordThree()));
    }

    /**
     * This method scrape the website that is passed.
     *
     * @param scraper Scraper
     * @return
     */
    public Scraper scrapeWebsite(Scraper scraper) {

        try {

            Document doc = Jsoup.connect(scraper.getUrl()).get();

            String title = doc.title();

            String htmlBody = doc.body().data();


            scraper.setTitle(title);
            scraper.setStatus(200);
            scraper.setBody(doc.select("h1").toString());
            scraper.setTime(new Date().toString());

            log.info("----Scraper----=====>{}", title.toUpperCase());

            String pageContent = doc.html();

            if ((pageContent.contains(scraper.getKeyWordOne())
                    && pageContent.contains(scraper.getKeyWordTwo()))
                    && pageContent.contains(scraper.getKeyWordThree())) {

                scraper.setKeysFound(3);

                setKeysValues(scraper, pageContent);
            } else {
                scraper.setKeysFound(-1);
                setKeysValues(scraper, pageContent);
            }


            return scraper;

        } catch (JSONException exception) {
            log.info("Error=------- {}", exception.getMessage());


            return scraper;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
