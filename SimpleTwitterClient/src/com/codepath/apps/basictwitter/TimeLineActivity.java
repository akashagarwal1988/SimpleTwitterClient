package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.ComposeFragment;
import com.codepath.apps.basictwitter.fragments.HomeTimeLineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimeLineFragment;
import com.codepath.apps.basictwitter.fragments.TweetsListFragment;
import com.codepath.apps.basictwitter.listener.FragmentTabListener;

public class TimeLineActivity extends FragmentActivity {

	private TweetsListFragment fragmentTweetList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		fragmentTweetList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.layout.fragment_tweets_list);
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home	")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimeLineFragment>(R.id.flContainer, this, "first",
								HomeTimeLineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimeLineFragment>(R.id.flContainer, this, "second",
			    		MentionsTimeLineFragment.class));

		actionBar.addTab(tab2);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.tweets, menu);
        return true;
    }
	
	

	public void onCompose(MenuItem mi){
		Intent intent = new Intent(this, ComposeActivity.class);
		startActivity(intent);	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == 1) {
	     // Extract name value from result extras
		  	fragmentTweetList.clear();
	  }
	}
	
	public void onProfileView(MenuItem mi){
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}
	
}
