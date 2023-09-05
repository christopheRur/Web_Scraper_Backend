package com.codelab.webscraper.services;

import com.codelab.webscraper.model.Scraper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ScraperService {
    public Scraper scrapeWebsite(Scraper scraper);

}
