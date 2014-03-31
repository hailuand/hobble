package com.example.hobble2;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class HobbleApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, "EB9W5UE6rzAm5o6vlG0WFsfIviYY6afXfF9oRSyz", "qxwRaWr6DWHmEeBaODMaSjuzMdkUrkAx0rDdYDTh");
		
		/*
		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();
		*/
	}
	
	

}
