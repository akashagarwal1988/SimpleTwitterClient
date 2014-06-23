package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeActivity extends Activity {

	private TwitterClient client;
	EditText etTweet;
	ImageView ivMyImage;
	TextView tvName;
	TextView tvHandle;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		etTweet = (EditText) findViewById(R.id.etTweet);
		ivMyImage = (ImageView) findViewById(R.id.ivMyImage);
		tvName = (TextView) findViewById(R.id.tvName);
		tvHandle = (TextView) findViewById(R.id.tvHandle);
		client = TwitterApplication.getRestClient();
		getData();
		
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
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet, menu);
        return true;
    }
	
	public void onTweet(MenuItem mi){
		client.postTweet(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
				Toast.makeText(getApplicationContext(), "Post Failed !", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onSuccess(JSONObject json) {				
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
				
				
			}
		}, etTweet.getText().toString());
		
		
	}
	
}
