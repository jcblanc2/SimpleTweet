package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public Long id;
    public String body;
    public String createdAt;
    public User user;
    public int  favorite_count;
    public int  retweet_count;
    public Entities entities;
    public ExtendedEntities exEntities;
    public Boolean favorited;
    public Boolean retweeted;


    public String getFavorite_count() {
        return String.valueOf(favorite_count);
    }

    public String getRetweet_count() {
        return String.valueOf(retweet_count);
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.favorite_count = jsonObject.getInt("favorite_count");
        tweet.retweet_count = jsonObject.getInt("retweet_count");
        tweet.retweeted = jsonObject.getBoolean("retweeted");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.entities = Entities.fromJson(jsonObject.getJSONObject("entities"));

        if (jsonObject.has("extended_entities")){
            tweet.exEntities = ExtendedEntities.fromJson(jsonObject.getJSONObject("extended_entities"));
        }else {
            tweet.exEntities = new ExtendedEntities();
            tweet.exEntities.videoUrl = "";
            tweet.exEntities.type = "";
        }
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        return tweet;
    }

    public Tweet(){}

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public static String  getFormattedTimestamp(String createdAt){
        return TimeFormatter.getTimeDifference(createdAt);
    }

    public static String  getFormattedTime(String createdAt){
        return TimeFormatter.getTimeStamp(createdAt);
    }


    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }
}