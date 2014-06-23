package com.codepath.apps.basictwitter;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	Context context;
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		
		super(context, 0, tweets);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		
		//find or inflate the template
		View v;
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
			
		}else{
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvMyName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvTime = (TextView) v.findViewById(R.id.tvTime);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvTime.setText(getRelativeTime(tweet.getCreatedAt()));
		return v;
	}
	
	public String getRelativeTime(Date date){
		String suffix = "";
		String str = DateUtils.getRelativeTimeSpanString(

				 // Suppose you are in an activity or other Context subclass

		        date.getTime(),
		        DateTime.now().getMillis(),

		        DateUtils.MINUTE_IN_MILLIS, // The resolution. This will display only 
		                                        // minutes (no "3 seconds ago") 

		        DateUtils.FORMAT_ABBREV_RELATIVE).toString();
		
		if(str.split(" ").length > 1){
		if(str.split(" ")[1].equals("mins") | str.split(" ")[1].equals("min")) suffix = "m";
		else if(str.split(" ")[1].equals("hour") | str.split(" ")[1].equals("hours")) suffix = "h";
		}
		
		
		return str.split(" ")[0] + suffix;
	}
}
