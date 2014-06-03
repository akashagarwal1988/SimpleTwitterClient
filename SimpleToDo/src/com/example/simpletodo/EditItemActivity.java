package com.example.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends Activity {
int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		Intent myIntent = getIntent();
		if (myIntent.hasExtra("data")){                                
			EditText et = (EditText)findViewById(R.id.editText1);
		et.setText(myIntent.getStringExtra("data"));
		et.setSelection(et.getText().length());
		}
		if(myIntent.hasExtra("position")){
			position = myIntent.getIntExtra("position", 0);
		}
		Bundle bundle = myIntent.getExtras();
		for (String key : bundle.keySet()) {
		    Object value = bundle.get(key);
		    Log.d("####", String.format("%s %s (%s)", key,  
		        value.toString(), value.getClass().getName()));
		}
	}
	
	public void returnToMain(View v){
		EditText etName = (EditText) findViewById(R.id.editText1);
		Intent data = new Intent();
		data.putExtra("name", etName.getText().toString());
		data.putExtra("position", position);
		setResult(RESULT_OK, data);
		finish();
	}
}
