package com.frrahat.datemark;

import com.frrahat.daycount.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DateMarkMainActivity extends Activity {
	
	public static final String storageFolderName = ".com.frrahat.datemark";
	public static final String STORAGE_FILE_NAME = "data.ser";
	BaseAdapter baseAdapter;
	ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_mark_main);
		
		ItemContainer.initilaizecontainer(this);
		
		
		final LayoutInflater layoutInflater = getLayoutInflater();

		// adapter for listview
		baseAdapter = new BaseAdapter() {

			@SuppressLint("InflateParams")
			@Override
			public View getView(final int position, View view, final ViewGroup parent) {
				if (view == null) {
					view = layoutInflater.inflate(
							R.layout.layout_myitem, null);
				}
				TextView textView = (TextView) view.findViewById(R.id.textView);
				
				MyItem item=ItemContainer.getItemAt(position);

				textView.setText(item.title + " (days : "+Integer.toString(item.getDateCount())+")");
				
				/*Button buttonMark=(Button) view.findViewById(R.id.button_mark);
				buttonMark.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
					}
				});*/

				return view;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return position;
			}

			@Override
			public int getCount() {
				return ItemContainer.getItemCount();
			}
		};
		
		listView=(ListView) findViewById(R.id.listView);
		listView.setAdapter(baseAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				
				/*long viewId = view.getId();
				 
			    if (viewId == R.id.button_mark) {
					if(ItemContainer.getItemAt(position).addCurrentDate()){
						Toast.makeText(getApplicationContext(), "Marked Current Date", Toast.LENGTH_SHORT).show();
						baseAdapter.notifyDataSetChanged();
					}
					else{
						Toast.makeText(getApplicationContext(), "Already Marked", Toast.LENGTH_SHORT).show();
					}
			        //Toast.makeText(getApplicationContext(), "Button mark clicked", Toast.LENGTH_SHORT).show();
			    } else {*/
			    	Intent intent=new Intent(DateMarkMainActivity.this, MarkedDatesView.class);
					intent.putExtra("index", position);
					startActivityForResult(intent, 145);
			        //Toast.makeText(getApplicationContext(), "ListView clicked" + id, Toast.LENGTH_SHORT).show();
			    //}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.date_mark_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			addNewItem();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==121){
			if(resultCode==RESULT_OK){
				String title=data.getStringExtra("title");
				if(title==null || title.length()==0){
					title="dummy";
				}
				ItemContainer.addItem(new MyItem(title));
				baseAdapter.notifyDataSetChanged();
			}
		}
		else if(requestCode==145){
			if(resultCode==RESULT_OK){
				baseAdapter.notifyDataSetChanged();
				ItemContainer.saveToFile(this);
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void addNewItem(){
		Intent intent=new Intent(DateMarkMainActivity.this, TitleInput.class);
		startActivityForResult(intent, 121);
	}
}
