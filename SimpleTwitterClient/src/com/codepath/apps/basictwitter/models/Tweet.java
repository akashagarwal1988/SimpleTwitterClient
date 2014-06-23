package com.codepath.apps.basictwitter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTimeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContextWrapper;
import android.text.format.DateUtils;

public class Tweet implements Serializable{
	
	public Tweet(String body, long uid, Date createdAt, User user) {
		this.body = body;
		this.uid = uid;
		this.createdAt = createdAt;
		this.user = user;
	}
	
	public Tweet(){
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8373494862194152310L;
	private String body;
	private long uid;
	private Date createdAt;
	private User user;

	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();

		try {
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = convertStringToDate(jsonObject.getString("created_at"));
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		
		for (int i = 0; i < jsonArray.length(); i++ ){
			JSONObject tweetJson = null;
			try{
				tweetJson = jsonArray.getJSONObject(i);
			}catch(Exception e){
				e.printStackTrace();
				continue;
				
			}
			Tweet tweet = Tweet.fromJson(tweetJson);
			if(tweet != null){
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	@Override
	public String toString() {
		return getBody() + " - " + getUser().getScreenName();
	}
	
	public static Date convertStringToDate(String date){
		String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
		 
		try {
			Date formattedDate =  new SimpleDateFormat(LARGE_TWITTER_DATE_FORMAT, Locale.ENGLISH)
			       .parse(date);
			return formattedDate;
		} catch (ParseException e) {		
			e.printStackTrace();
			return null;
		}
		 
	}
}
