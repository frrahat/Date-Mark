package com.frrahat.datemark;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.util.Log;

/**
 * @author Rahat
 * @since Feb 7, 2017
 */
public class ItemContainer {
	static ArrayList<MyItem> items;
	
	public static void initilaizecontainer(Context context){
		items=new ArrayList<MyItem>();
		
		loadFromFile(context);
	}
	
	public static ArrayList<MyItem> getItems(){
		return items;
	}
	
	public static MyItem getItemAt(int index){
		return items.get(index);
	}
	
	public static int getItemCount(){
		return items.size();
	}
	
	public static void addItem(MyItem item){
		items.add(item);
	}
	
	public static void deleteItem(int index){
		items.remove(index);
	}
	
	
	private static File getStorageFile(Context context) {
		File storageDir;
		String state=Environment.getExternalStorageState();
		// has writable external  storage
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			storageDir = new File(Environment.getExternalStorageDirectory(),
					DateMarkMainActivity.storageFolderName);
		} else {
			ContextWrapper contextWrapper = new ContextWrapper(
					context.getApplicationContext());
			storageDir = contextWrapper.getDir(DateMarkMainActivity.storageFolderName,
					Context.MODE_PRIVATE);
		}


		if (!storageDir.exists()) {
			if (storageDir.mkdirs()) {
				Log.i("success", "new folder added");
			} else {
				Log.i("failure", "folder addition failure");
			}
		}

		File dataStorageFile = new File(storageDir, DateMarkMainActivity.STORAGE_FILE_NAME);
		return dataStorageFile;
	}
	
	private static void loadFromFile(Context context) {
		File dataStorageFile = getStorageFile(context);		
		
		FileInputStream inStream;
		ObjectInputStream objectInStream;
		try {
			inStream = new FileInputStream(dataStorageFile);
			objectInStream = new ObjectInputStream(inStream);
			items = (ArrayList<MyItem>) objectInStream.readObject();
			objectInStream.close();

		} catch (IOException | ClassCastException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void saveToFile(Context context) {
		File dataStorageFile = getStorageFile(context);
		
		
		FileOutputStream outStream;
		ObjectOutputStream objectOutStream;
		try {
			outStream = new FileOutputStream(dataStorageFile);
			objectOutStream = new ObjectOutputStream(outStream);
	
			objectOutStream.writeObject(items);
			// objectOutStream.flush();
			objectOutStream.close();
	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
