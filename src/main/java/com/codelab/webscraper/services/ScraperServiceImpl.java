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
import java.net.URL;
import java.util.Date;
import java.util.HashSet;

@Slf4j
@Service
public class ScraperServiceImpl implements ScraperService {
    private HashSet<String> allLinks = new HashSet<String>();
    private HashSet<String> allImages = new HashSet<String>();
    private HashSet<String> classOne = new HashSet<String>();
    private HashSet<String> classTwo = new HashSet<String>();
    private HashSet<String> classThree = new HashSet<String>();

    /**
     * Retrieves elements attribues
     *
     * @param attribute String
     * @param doc Document
     * @return Elements
     */
    private Elements retrieveElementAttributes(String attribute, Document doc) {
        return doc.select(attribute);
    }

    /**
     * Loop through the selected elements and extract data
     * @param classKey String
     * @param scraper String
     */
    private void extractInfoFromSpecificClass(String classKey, Scraper scraper, Document doc) {
        Elements element = doc.select("." + classKey);

        for (Element el : element) {

            String text = element.text();

            classOne.add(text);
        }

        scraper.setClass1(classOne);
    }

    /**
     * Will iterate over links, and extract those links
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
            URL baseUrl = new URL(scraper.getUrl());

            String title = doc.title();

            String htmlBody = doc.body().data();


            collectElementData(
                    retrieveElementAttributes("a[href]", doc),
                    scraper,
                    "href");

            collectElementData(
                    retrieveElementAttributes("img[src]", doc),
                    scraper,
                    "src");

            extractInfoFromSpecificClass(scraper.getKeyWordOne(), scraper, doc);
            extractInfoFromSpecificClass(scraper.getKeyWordTwo(), scraper, doc);
            extractInfoFromSpecificClass(scraper.getKeyWordThree(), scraper, doc);


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
