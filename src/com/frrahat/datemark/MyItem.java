package com.frrahat.datemark;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateFormat;

/**
 * @author Rahat
 * @since Feb 7, 2017
 */
public class MyItem implements Serializable{
	String title;
	ArrayList<String>Dates;
	
	public MyItem(String title){
		this.title=title;
		Dates=new ArrayList<String>();
	}
	public int getDateCount(){
		return Dates.size();
	}
	public boolean addCurrentDate(){
		Date date=new Date();
		String dateText=DateFormat.format("dd/MMM/yyyy (E) hh:mm:ss a", date).toString();
		
		int sz=Dates.size();
		if(sz>0 && Dates.get(sz-1).substring(0, 11).equals(dateText.substring(0,11))){
			return false;
		}
		Dates.add(dateText);
		return true;
	}
	
	public boolean addDate(String dateString){
		Date date;
		try {
			date = new SimpleDateFormat("dd/M/yyyy", Locale.UK).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		String dateText=DateFormat.format("dd/MMM/yyyy (E)", date).toString();
		
		int sz=Dates.size();
		if(sz>0 && Dates.get(sz-1).equals(dateText)){
			return false;
		}
		Dates.add(dateText);
		return true;
	}
	@Override
	public String toString() {
		String s= title +"\n";
		int sz=Dates.size();
		s+=Integer.toString(sz)+" days\n\n"; 
		for(int i=0;i<sz;i++){
			s+=Dates.get(i)+"\n";
		}
		
		return s;
	}
	
	
}
