package com.example.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class TodoActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvItems;
	private final int REQUEST_CODE = 20;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);			
		lvItems = (ListView) findViewById(R.id.lvItems);
		items = new ArrayList<String>();
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
		
	}
	
	private void setupListViewListener() {
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public 
			void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
				intent.putExtra("position",position);
				intent.putExtra("id", id);
				intent.putExtra("data", items.get(position));
				startActivityForResult(intent, REQUEST_CODE);
			}
			
		});
		
		
		
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}
	
	public void addToDoItem(View v){
		EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
		saveItems();
	}
	
	private void readItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "toDo.txt");
		try{
			items = new ArrayList<String>(FileUtils.readLines(todoFile));			
		}
		catch(IOException e){
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItems() {
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "toDo.txt");
		try{
			FileUtils.writeLines(todoFile, items);
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String name = data.getExtras().getString("name");
	     Integer position = data.getExtras().getInt("position");
	     items.set(position, name);
	     lvItems = (ListView) findViewById(R.id.lvItems);
	     itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
	     lvItems.setAdapter(itemsAdapter);
	     saveItems();
	     // Toast the name to display temporarily on screen
	     Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
	  }
	} 

}
