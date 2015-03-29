package com.vaadin.vaadinProjekt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;

import com.example.vaadinProjekt.domain.User;
import com.example.vaadinProjekt.service.MessageBroadcaster;
import com.example.vaadinProjekt.service.MessageData;
import com.example.vaadinProjekt.service.MessageListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.vaadin.vaadinProjekt.MyAppWidgetset")
@Push
public class MyUI extends UI implements MessageListener{

	final VerticalLayout chatContent = new VerticalLayout();
	final TextField messageField = new TextField();
	
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		final User user = new User(0, "nieznajomy");

		final Panel chatPanel = new Panel("Chat");
		chatPanel.setHeight("400px");

		
		chatContent.setMargin(true);

		chatPanel.setContent(chatContent);

		final AbsoluteLayout header = new AbsoluteLayout();
		header.setWidth(100.0f, Unit.PERCENTAGE);
		header.setHeight("50px");
		Button btnLogin = new Button("Zaloguj");
		Label lblWelcome = new Label("Witaj nieznajomy!");

		header.addComponent(lblWelcome, "left: 10px; top: 5px;");
		header.addComponent(btnLogin, "right: 10px; top: 2px;");

		final AbsoluteLayout footer = new AbsoluteLayout();
		footer.setHeight("50px");

		Button btnSend = new Button("Wyślij");
		messageField.setWidth(90.0f, Unit.PERCENTAGE);
		// btnSend.setWidth(10.0f, Unit.PERCENTAGE);

		footer.addComponent(messageField);
		footer.addComponent(btnSend, "right: 0px; top: 0px;");

		layout.addComponent(header);
		layout.addComponent(chatPanel);
		layout.addComponent(footer);
		
		//actual time parser
	/*	Date now = new Date();
		String nowString = now.toString();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nowString);
		} catch (ParseException e) {
		}
		String dateNow = null;
		dateNow = new SimpleDateFormat("H:mm").format(date);*/

		btnSend.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (messageField.isEmpty()) {
					Notification.show("Error",
							"Nie możesz wysłać pustej wiadomości",
							Type.TRAY_NOTIFICATION);
				} else {
					MessageBroadcaster.broadcast(new MessageData(messageField.getValue(), user.getName()));
					/*chatContent.addComponent(new Label("(time) <b>" + user.getName()
							+ "</b> " + messageField.getValue(),
							ContentMode.HTML));
					messageField.setValue("");*/
				}
			}
		});
		MessageBroadcaster.registerListener(this);
		
		

	}


	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	@Override
	public void receiveMessage(final MessageData messageData) {
		access(new Runnable() {
			
			@Override
			public void run() {
				chatContent.addComponent(new Label("(time) <b>" + messageData.getAuthor()
						+ "</b> " + messageData.text,
						ContentMode.HTML));
				messageField.setValue("");
			}
		});
		
	}
		
	
}
