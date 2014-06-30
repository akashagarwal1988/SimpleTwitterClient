package com.codepath.apps.basictwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.ComposeFragment;

public class ComposeActivity extends FragmentActivity {

	
	ComposeFragment composeFragment;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_compose);
		composeFragment = new ComposeFragment();
		// Begin the transaction
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		ft.replace(R.id.fragmentCompose, composeFragment);
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
		
	}
	public void onTweet(View v){
		composeFragment.onTweet(v);
	}
}
