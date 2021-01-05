package com.jacobvborden.controllers;

import java.io.IOException;
import java.util.Arrays;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetLookupController {

    @Value("${twitter.bearerToken}")
    private String bearerToken;

    @CrossOrigin
    @RequestMapping("/tweetlookup/7day")
    public String tweetLookup(@RequestParam String keywordSearch, @RequestParam(required = false) String nextToken) throws IOException {

        OkHttpClient client = new OkHttpClient();
        String[] completeResponse; 

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.twitter.com/2/tweets/search/recent").newBuilder();

        urlBuilder.addQueryParameter("query", keywordSearch);
        urlBuilder.addQueryParameter("expansions", "author_id");
        urlBuilder.addQueryParameter("tweet.fields", "created_at");
        urlBuilder.addQueryParameter("user.fields", "username,url");
        urlBuilder.addQueryParameter("max_results", "100");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().header("Authorization", "Bearer " + bearerToken).url(url).build();

        Response response = client.newCall(request).execute();
        
        String responseString = response.body().string();

        completeResponse = responseString.split("\n");

        return  Arrays.toString(completeResponse);
    }
    
}