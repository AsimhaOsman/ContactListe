package com.example.adaptersample;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class AppGlobal extends Application{
	//an android:name=".AppGlobal" in der Manifest denken

	List<CDContacts> lsContactList;
	int iSelectedPosition = -1; // merkt sich die position des listenitems
	boolean bNotifyIt = false;
	
	List<Activity> myActivities = new ArrayList<Activity>(); 
	
	public void finishAll() {

		System.exit(0);
	}
}
