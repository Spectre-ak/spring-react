package com.spring.articles;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class article implements Serializable{
	private String title,body,date;
	 Set<String> distinctViewsCookieUUIDsSet=new TreeSet<>();
	private HashMap<String,Integer> hashMapForExtraInfosCount=new HashMap<>();
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public String getDate() {
		return date;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public void setBody(String body) {
		this.body=body;
	}
	public void setDate(String date) {
		this.date=date;
	}
	public int getSizeOfViewsSet() {
		return distinctViewsCookieUUIDsSet.size();
	}
	public boolean addViewID(String id) {
		return distinctViewsCookieUUIDsSet.add(id);
	}
	public boolean checkIfDataIsPresentInHashMap(String data) {
		if(hashMapForExtraInfosCount.get(data)==null)
			return false;
		else 
			return true;
	}
	public int getCountOfData(String data) {
		return hashMapForExtraInfosCount.get(data);
	}
	public void addExtraInfo(String data) {
		hashMapForExtraInfosCount.put(data,1);
		
	}
	
}
