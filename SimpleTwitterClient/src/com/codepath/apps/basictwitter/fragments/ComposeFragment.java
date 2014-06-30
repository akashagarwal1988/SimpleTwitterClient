package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TimeLineActivity;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.R.id;
import com.codepath.apps.basictwitter.R.layout;
import com.codepath.apps.basictwitter.R.menu;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeFragment extends TweetsListFragment {

	private TwitterClient client;
	EditText etTweet;
	ImageView ivMyImage;
	TextView tvName;
	TextView tvHandle;
	User user;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		
		
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_compose, container, false);
		etTweet = (EditText) v.findViewById(R.id.etTweet);
		ivMyImage = (ImageView) v.findViewById(R.id.ivMyImage);
		tvName = (TextView) v.findViewById(R.id.tvName);
		tvHandle = (TextView) v.findViewById(R.id.tvHandle);
		client = TwitterApplication.getRestClient();
		getData();
				return v;
		
	}
	public void getData() {
		client.getMyInfo(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJson(json);
				tvName.setText(user.getName());
				tvHandle.setText("@" + user.getScreenName());
				ImageLoader imageLoader = ImageLoader.getInstance();
				
				imageLoader.displayImage(user.getProfileImageUrl(), ivMyImage);
			}
			
		});
	}
//	public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tweet, menu);
//        return true;
//    }
//	
	public void onTweet(View v){
		client.postTweet(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
				Toast.makeText(getActivity(), "Post Failed !", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onSuccess(JSONObject json) {				
				Intent intent = new Intent(getActivity(), TimeLineActivity.class);
				clear();
				getActivity().startActivity(intent);
//				client.getHomeTimeLine(new JsonHttpResponseHandler(){
//					@Override
//					public void onFailure(Throwable e, String s) {
//						Log.d("debug", e.toString());
//						Log.d("debug", s.toString());
//					}
//					
//					@Override
//					public void onSuccess(JSONArray json) {
//						addAll(Tweet.fromJSONArray(json));
//					}
//				});
			}
		}, etTweet.getText().toString());
		
		
	}
	
}
