package com.codelab.webscraper.services;

import com.codelab.webscraper.model.Scraper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class ScraperServiceImpl implements ScraperService{

    public JSONObject scrapeWebsite(Scraper scraper){

        try{
            JSONObject jsonObj =new JSONObject();

            Document doc = Jsoup.connect(scraper.getUrl()).get();

            String title = doc.title();
//            String keyword1 =doc.attr(scraper.getKeyWordOne());
//            String keyWord2 =doc.attr(scraper.getKeyWordTwo());
//            String keyWord3 =doc.attr(scraper.getKeyWordThree());
            String htmlBody =doc.body().data();

            jsonObj.put("title",title);
//            jsonObj.put("keyWord1",keyword1);
//            jsonObj.put("keyWord2",keyWord2);
//            jsonObj.put("keyWord3",keyWord3);

            jsonObj.put("body",htmlBody);



            log.info("----Scraper----=====>{}",title.toUpperCase());

            return jsonObj;

        }
        catch(JSONException exception){
            log.info("ERO=------- {}",exception.getMessage());

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("Error ",exception.getMessage());

        return jsonObj;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
