package com.example.vaadinProjekt.service;

import java.util.HashMap;
import java.util.Map.Entry;

public class MessageManager {
	
	private final String emoticon1 = "<img src='http://imageshack.com/a/img540/5792/R7madn.png' height='19px'>";
	private final String emoticon2 = "<img src='http://imageshack.com/a/img538/2826/ZEfUJh.png' height='19px'>";
	private final String emoticon3 = "<img src='http://imageshack.com/a/img908/8103/SrlORt.png' height='19px'>";
	private final String emoticon4 = "<img src='http://imageshack.com/a/img911/3802/VlUsBa.png' height='19px'>";
	private final String emoticon5 = "<img src='http://imageshack.com/a/img673/9108/bNOiib.png' height='19px'>";
	private final String emoticon6 = "<img src='http://imageshack.com/a/img537/3572/t7Nfcx.png' height='19px'>";
	private final String emoticon7 = "<img src='http://imageshack.com/a/img910/627/5QMUI3.png' height='19px'>";
	private final String emoticon8 = "<img src='http://imageshack.com/a/img540/8758/MO8Arl.png' height='19px'>";
	private final String emoticon9 = "<img src='http://imageshack.com/a/img540/9319/WdWy3L.png' height='19px'>";

	private HashMap<String, String> map = new HashMap<>();

	public MessageManager() {
		map.put(":D", emoticon1);
		map.put(":)", emoticon2);
		map.put(":(", emoticon3);
		map.put(";)", emoticon4);
		map.put(":P", emoticon5);
		map.put(";P", emoticon6);
		map.put(":B", emoticon7);
		map.put(":|", emoticon8);
		map.put(";(", emoticon9);
		
	}

	public String convertMessage(String s) {

		if (s.length() > 1) {
			/*
			 * http://stackoverflow.com/questions/4234985/how-to-for-each-the-hashmap
			 */
			for (Entry<String, String> entry : map.entrySet()){
				String key = entry.getKey();
				String value = entry.getValue();
				
				s = s.replace(key, value);
				System.out.println(s);
			}
			
		}

		return s;
	}

	public void zamien(int i, String c) {

	}
}
