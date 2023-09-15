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
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Slf4j
@Service
public class ScraperServiceImpl implements ScraperService {
    private HashSet<String> allLinks = new HashSet<String>();
    private HashSet<String> allImages = new HashSet<String>();
    private HashSet<String> windowOne = new HashSet<String>();
    private HashSet<String> windowTwo = new HashSet<String>();
    private HashSet<String> windowThree = new HashSet<String>();

    /**
     * Retrieves elements attribues
     *
     * @param attribute String
     * @param doc       Document
     * @return Elements
     */
    private Elements retrieveElementAttributes(String attribute, Document doc) {
        return doc.select(attribute);
    }

    /**
     * Loop through the selected elements and extract data
     *
     * @param classKey String
     */
    private void extractInfoFromSpecificClass(String classKey, Document doc, Scraper scraper, HashSet<String> details) {

        Elements elementWithClass = doc.getElementsByClass(classKey);

        for (Element data : elementWithClass) {

            String text = " ->Related to keyword: " + classKey + " " + Arrays.toString(data.text().split("----"));

            details.add(text);
        }
        if (scraper.getKeyWordOne().equals(classKey)) {
            scraper.setClassOne(details);
        }
        if (scraper.getKeyWordTwo().equals(classKey)) {
            scraper.setClassTwo(details);
        }
        if (scraper.getKeyWordThree().equals(classKey)) {
            scraper.setClassThree(details);
        }

        extractInfoFromSpecificId(classKey, doc, scraper, details);

    }

    private void extractInfoFromSpecificId(String classKey, Document doc, Scraper scraper, HashSet<String> details) {

        Element elementWithId = doc.getElementById(classKey);

        if (elementWithId != null) {

            String text = elementWithId.text();

            details.add(text);
        } else {
            log.info("Couldn't find ID: {}", classKey);
        }


        if (scraper.getKeyWordOne().equals(classKey)) {
            scraper.setClassOne(details);
        }
        if (scraper.getKeyWordTwo().equals(classKey)) {
            scraper.setClassTwo(details);
        }
        if (scraper.getKeyWordThree().equals(classKey)) {
            scraper.setClassThree(details);
        }


    }

    /**
     * Will iterate over html and extract links
     *
     * @param
     */
    private void collectElementLinks(Elements linksData, Scraper scraper, String attrKey) {


        for (Element data : linksData) {


            String singleDatum = linksData.attr(attrKey);


            if (!singleDatum.isEmpty() && attrKey.contains("href")) {
                allLinks.add(singleDatum);
            }

        }

        scraper.setLinks(allLinks);

    }


    /**
     * Will iterate and extract image links
     *
     * @param
     */
    private void collectElementImage(Elements retrievedData, Scraper scraper, String attrKey) {


        for (Element data : retrievedData) {


            String singleDatum = retrievedData.attr(attrKey);

            if (!singleDatum.isEmpty() && attrKey.contains("src")) {
                allImages.add(singleDatum);
            }

        }
        scraper.setImages(allImages);

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


            collectElementLinks(
                    retrieveElementAttributes("a[href]", doc),
                    scraper,
                    "href");

            collectElementImage(
                    retrieveElementAttributes("img[src]", doc),
                    scraper,
                    "src");


            extractInfoFromSpecificClass(scraper.getKeyWordOne(), doc, scraper, windowOne);
            extractInfoFromSpecificClass(scraper.getKeyWordTwo(), doc, scraper, windowTwo);
            extractInfoFromSpecificClass(scraper.getKeyWordThree(), doc, scraper, windowThree);


            scraper.setTitle(title);
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
