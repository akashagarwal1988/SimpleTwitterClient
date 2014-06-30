package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.ProfileActivity;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsListFragment extends Fragment {

	
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	TwitterClient client;
	User u;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter( getActivity(), tweets);
		client = TwitterApplication.getRestClient();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//INflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// Assign our references
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		client = TwitterApplication.getRestClient();
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
					addAll(Tweet.fromJSONArray(json));
				}
			}, maxId);
		}

		
	});
	lvTweets.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Tweet tweet = aTweets.getItem(position);
			u = tweet.getUser();
			Toast.makeText(getActivity(), "User " + u, Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(getActivity(),ProfileActivity.class);			
			intent.putExtra("user", u);
			startActivity(intent);
		}
		
		
	});
	
		// Return the layout view
		return v;
	}
	
	public TweetArrayAdapter getAdapter() {
		return aTweets;
	}
	
	public void addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}
	
	public void clear(){
		aTweets.clear();
	}
	
	
}
