package com.dotcomdotcom.barcodescanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class Book {

    public static final int IMG_SMALL = 0;
    public static final int IMG_MEDIUM = 1;
    public static final int IMG_LARGE = 2;

    private String title;
    private String url;
    private int numPages;
    private String[] authors;
    private String[] publishers;
    private String publishDate;
    private String[] imageUrl;

    public Book() {

    }

    public Book(JSONObject json) throws JSONException {
        fromJson(json);
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.title = json.getString("title");
        this.url = json.getString("url");
        this.numPages = json.getInt("number_of_pages");

        JSONArray authArr = json.getJSONArray("authors");
        this.authors = new String[authArr.length()];
        for (int i=0; i<authArr.length(); i++) {
            authors[i] = authArr.getJSONObject(i).getString("name");
        }

        JSONArray pubArr = json.getJSONArray("publishers");
        this.publishers = new String[pubArr.length()];
        for (int i=0; i<pubArr.length(); i++) {
            publishers[i] = pubArr.getJSONObject(i).getString("name");
        }

        this.publishDate = json.getString("publish_date");

        JSONObject imgArr = json.getJSONObject("cover");
        imageUrl = new String[3];
        imageUrl[IMG_SMALL] = imgArr.getString("small");
        imageUrl[IMG_MEDIUM] = imgArr.getString("medium");
        imageUrl[IMG_LARGE] = imgArr.getString("large");

    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getNumPages() {
        return numPages;
    }

    public String[] getAuthors() {
        return authors;
    }

    public String getAuthorsString() {
        String str = "";
        for (String author : authors) {
            str += author + ", ";
        }
        str += "\b\b";
        return str;
    }

    public String[] getPublishers() {
        return publishers;
    }

    public String getPublishersString() {
        String str = "";
        for (String publisher : publishers) {
            str += publisher + ", ";
        }
        str += "\b\b";
        return str;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String[] getImageUrl() {
        return imageUrl;
    }
}
