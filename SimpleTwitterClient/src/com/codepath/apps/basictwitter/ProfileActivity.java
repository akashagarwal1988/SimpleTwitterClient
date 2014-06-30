package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimeLineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	String screenName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Intent intent = getIntent();
		User u = (User) intent.getSerializableExtra("user");
		if (u!=null)
		screenName = u.getScreenName();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimeLineFragment fragmentDemo = UserTimeLineFragment.newInstance(screenName);
		ft.replace(R.id.fragmentTimeLine, fragmentDemo);
		ft.commit();
		loadProfileInfo();
	}
	
	private void loadProfileInfo(){
		
		if(screenName.equals("")){
TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
	@Override
	public void onSuccess(JSONObject json){
		User u = User.fromJson(json);
		getActionBar().setTitle("@" + u.getScreenName());
		populateProfileHeader(u);
	}
		});
		}
		else {
			
			TwitterApplication.getRestClient().getUserInfo(new JsonHttpResponseHandler(){
				@Override
				public void onSuccess(JSONObject json){
					User u = User.fromJson(json);
					getActionBar().setTitle("@" + u.getScreenName());
					populateProfileHeader(u);
				}
					}, screenName);
		}
			
	}
	private void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(u.getName());
		tvTagLine.setText(u.getTagLine());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFollowingCount() + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
	}
}
