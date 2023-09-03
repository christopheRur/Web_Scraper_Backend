package com.codelab.webscraper.services;

import com.codelab.webscraper.model.Scraper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ScraperService {
    public JSONObject scrapeWebsite(Scraper scraper);

}
