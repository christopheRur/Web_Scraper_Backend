package com.codelab.webscraper.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scraper {
    private String title;
    private String url;
    private String body;
    private String keyWordOne;
    private String keyWordTwo;
    private String keyWordThree;
    private int status;
    private String time;
    private int keysFound;
    private Boolean key1Found;
    private Boolean key2Found;
    private Boolean key3Found;
    private HashSet<String> links;
    private HashSet<String> images;
    private HashSet<String> setOne;
    private HashSet<String> setTwo;
    private HashSet<String> setThree;
    private HashSet<Integer> numericalValues;
    private  Integer bodyLength;





}
