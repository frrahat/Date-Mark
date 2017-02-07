package com.frrahat.datemark;

import com.frrahat.daycount.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TitleInput extends Activity {
	
	EditText editTextTitle;
	Button buttonOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_input);
		
		editTextTitle=(EditText) findViewById(R.id.editTextPlayerName);
		buttonOK=(Button) findViewById(R.id.buttonOk);
		
		buttonOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.putExtra("title", editTextTitle.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed() {//in case of failure
		Intent intent=new Intent();
		intent.putExtra("title", editTextTitle.getText().toString());
		setResult(RESULT_OK, intent);
		
		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title_input, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
}
