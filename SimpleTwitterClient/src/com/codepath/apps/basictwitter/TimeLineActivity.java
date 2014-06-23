package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends Activity {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		client = TwitterApplication.getRestClient();
		populateTimeLine();
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter( this, tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				String maxId = String.valueOf(tweets.get(totalItemsCount - 1).getUid());
				
				client.getMoreTimeLine(new JsonHttpResponseHandler(){
					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());
					}
					
					@Override
					public void onSuccess(JSONArray json) {
						aTweets.addAll(Tweet.fromJSONArray(json));
					}
				}, maxId);
			}

			
		});
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
	
	
	public void populateTimeLine() {
		client.getHomeTimeLine(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
			
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
		});
	}
	public void onCompose(MenuItem mi){
		Intent intent = new Intent(this, ComposeActivity.class);
		startActivityForResult(intent,1);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == 1) {
	     // Extract name value from result extras
		  aTweets.clear();
	     populateTimeLine();
	  }
	}
}
