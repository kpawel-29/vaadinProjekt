//źródło" https://github.com/KubaNeumann/vaadindemo/blob/master/src/main/java/com/example/vaadindemo
package com.example.vaadinProjekt.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageData {
	
	public String text;
	public String author;
	public Date time;
	private MessageManager ms = new MessageManager();
	
	public MessageData(String text, String author, Date time) {
		super();
		this.text = text;
		this.author = author;
		this.time = time;
	}
	public String getText() {
		return ms.convertMessage(text);
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTime() {
		return new SimpleDateFormat("H:mm:ss").format(time);
	}
	public void setTime(Date time) {
		this.time = time;
	}

	
}