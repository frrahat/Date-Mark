package com.frrahat.datemark;

import java.util.ArrayList;
import java.util.Calendar;

import com.frrahat.daycount.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MarkedDatesView extends Activity {
	
	TextView textViewDayCount;
	ListView listView;
	int index;
	BaseAdapter baseAdapter;
	ArrayList<String> dateList=new ArrayList<String>();
	boolean wasMadeUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marked_dates_view);
		
		textViewDayCount=(TextView) findViewById(R.id.textView_dayCount);
		listView=(ListView) findViewById(R.id.listView_dates);
		
		
		Intent intent=getIntent();
		index=intent.getIntExtra("index", -1);
		
		
		if(index>-1 && index<ItemContainer.getItemCount()){
			String title=ItemContainer.getItemAt(index).title;
			setTitle(title);
			
			dateList=ItemContainer.getItemAt(index).Dates;
			textViewDayCount.setText("days : "+Integer.toString(dateList.size()));
		}
		
		
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
				textView.setText(dateList.get(position));

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
				return dateList.size();
			}
		};
		
		listView=(ListView) findViewById(R.id.listView_dates);
		listView.setAdapter(baseAdapter);
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View view, int position,
					long id) {
				onDeleteDate(position);
		    	return true;
			}
		});
		
		wasMadeUpdate=false;
		
	}
	
	@Override
	public void onBackPressed() {
		if(wasMadeUpdate){
			Intent intent=new Intent();
			setResult(RESULT_OK, intent);
		}
		super.onBackPressed();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marked_dates_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_delete) {
			onDeleteFullRecord();
			return true;
		}
		if (id == R.id.action_markDate) {
			onMarkNewDate();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void onDeleteFullRecord(){
		
		
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Deleting!!!")
		.setMessage("Are you sure you want to delete the full record?")
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						if(index>-1 && index<ItemContainer.getItemCount()){
							ItemContainer.deleteItem(index);
							Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
							
							wasMadeUpdate=true;
							onBackPressed();
						}
					}

				}).setNegativeButton("No", null).show();
	}
	
	private void onDeleteDate(final int position){
		
		
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Deleting!!!")
		.setMessage("Are you sure you want to unmark/delete this date?")
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dateList.remove(position);
				    	onDataUpdate();
				    	Toast.makeText(getApplicationContext(), "Date deleted", Toast.LENGTH_SHORT).show();
					}

				}).setNegativeButton("No", null).show();
	}
	
	private void onMarkNewDate() {

		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);

		// Launch Date Picker Dialog
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {

						String dateString = dayOfMonth + "/" + (monthOfYear+1) + "/"
								+ year;

						if (ItemContainer.getItemAt(index).addDate(dateString)) {
							Toast.makeText(getApplicationContext(),
									"Marked New Date", Toast.LENGTH_SHORT)
									.show();
							onDataUpdate();
						} else {
							Toast.makeText(getApplicationContext(),
									"Already Marked", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}, mYear, mMonth, mDay);
		dpd.setTitle("Mark Date");
		dpd.show();

	}
	
	
	private void onDataUpdate(){
		baseAdapter.notifyDataSetChanged();
		textViewDayCount.setText("days : "+Integer.toString(dateList.size()));
		wasMadeUpdate=true;
	}
}
