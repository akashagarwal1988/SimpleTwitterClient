package com.codepath.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8660626352785559508L;
	private String name;
	private long uid; 
	private String screenName;
	private String profileImageUrl;
	private int followersCount;
	private int followingCount;
	private String tagLine;
	
	public static User fromJson(JSONObject jsonObject) {
		User user = new User();
		try{
		user.name = jsonObject.getString("name");
		user.uid = jsonObject.getLong("id");
		user.screenName = jsonObject.getString("screen_name");
		user.profileImageUrl = jsonObject.getString("profile_image_url");
		user.followersCount = jsonObject.getInt("followers_count");
		user.followingCount = jsonObject.getInt("friends_count");
		user.tagLine = jsonObject.getString("description");
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
		return user;
	}
	
	public int getFollowersCount() {
		return followersCount;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public String getTagLine() {
		return tagLine;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

}
