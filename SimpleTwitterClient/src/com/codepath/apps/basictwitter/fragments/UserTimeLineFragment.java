package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimeLineFragment extends TweetsListFragment {
	
	String screenName = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		screenName = getArguments().getString("screenName", "");
		populateTimeLine();
	}
	
	public void populateTimeLine() {
		
		client.getUserTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
			
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
			}
		}, screenName);
		
	}
	public static UserTimeLineFragment newInstance(String screenName) {
		UserTimeLineFragment fragmentDemo = new UserTimeLineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
}
