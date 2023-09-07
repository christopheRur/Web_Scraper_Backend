package com.codelab.webscraper.services;

import com.codelab.webscraper.model.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

@Slf4j
@Service
public class ScraperServiceImpl implements ScraperService {
    private HashSet<String> allLinks = new HashSet<String>();
    private HashSet<String> allImages = new HashSet<String>();

    /**
     * Retrieves elements attribues
     *
     * @param attribute
     * @param doc
     * @return
     */
    private Elements retrieveElementAttributes(String attribute, Document doc) {
        return doc.select(attribute);
    }

    /**
     * Will iterate over links
     *
     * @param
     */
    private void collectElementData(Elements retrievedData, Scraper scraper, String attrKey) {

        for (Element data : retrievedData) {


            String singleDatum = retrievedData.attr(attrKey);


            if (!singleDatum.isEmpty() && attrKey.contains("href")) {
                allLinks.add(singleDatum);
            }

            if (!singleDatum.isEmpty() && attrKey.contains("src")) {
                allImages.add(singleDatum);
            }

        }
        scraper.setImages(allImages);
        scraper.setLinks(allLinks);
    }

    /**
     * Set values to keys
     *
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

            Elements links = doc.select("a[href]");

            collectElementData(
                    retrieveElementAttributes("a[href]", doc),
                    scraper,
                    "href");

            collectElementData(
                    retrieveElementAttributes("img[src]", doc),
                    scraper,
                    "src");


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


//    private ArrayList<String> retrieveAttributes(String attributes){
//
//
//    }


}
