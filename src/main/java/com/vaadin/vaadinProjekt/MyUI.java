package com.vaadin.vaadinProjekt;

import java.util.Date;

import javax.servlet.annotation.WebServlet;

import com.example.vaadinProjekt.domain.User;
import com.example.vaadinProjekt.service.MessageBroadcaster;
import com.example.vaadinProjekt.service.MessageData;
import com.example.vaadinProjekt.service.MessageListener;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.vaadin.vaadinProjekt.MyAppWidgetset")
@Push
public class MyUI extends UI implements MessageListener{

	private static final long serialVersionUID = 1L;
	final VerticalLayout chatContent = new VerticalLayout();
	final TextField messageField = new TextField();
	final Panel chatPanel = new Panel("Chat");
	final Window window = new Window("Podaj nick");
	final FormLayout loginForm = new FormLayout();
	
	
	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		final User user = new User(0, "nieznajomy");
		chatPanel.setHeight("400px");

		window.setWidth(150.0f, Unit.PIXELS);
		window.center();
		window.setClosable(true);
		window.setDraggable(false);
		window.setResizable(false);
		
		final TextField loginField = new TextField("", "");
		loginField.setWidth(90.0f, Unit.PERCENTAGE);
		final Button btnOK = new Button("OK");
		loginForm.addComponent(loginField);
		loginForm.addComponent(btnOK);
		
	    window.setContent(loginForm);
	    
		chatContent.setMargin(true);

		chatPanel.setContent(chatContent);

		final AbsoluteLayout header = new AbsoluteLayout();
		header.setWidth(100.0f, Unit.PERCENTAGE);
		header.setHeight("50px");
		Button btnLogin = new Button("Zaloguj");
		btnLogin.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				addWindow(window);
				
			}
		});
		
		Label lblWelcome = new Label("Witaj <b>"+user.getName()+"!</b>",ContentMode.HTML);

		header.addComponent(lblWelcome, "left: 10px; top: 5px;");
		header.addComponent(btnLogin, "right: 10px; top: 2px;");

		final HorizontalLayout footer = new HorizontalLayout();
		//footer.setHeight("50px");
		footer.addComponent(messageField);
		Button btnSend = new Button("Wyślij");
		//btnSend.setWidth(10.0f, Unit.PERCENTAGE);
		footer.addComponent(btnSend);
		
		footer.setWidth(100.0f, Unit.PERCENTAGE);
		footer.setExpandRatio(messageField, 1.0f);

		
		messageField.setWidth(100.0f, Unit.PERCENTAGE);
		// btnSend.setWidth(10.0f, Unit.PERCENTAGE);

		btnOK.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				window.close();
				user.setName(loginField.getValue().toString());
				
			}
		});

		layout.addComponent(header);
		layout.addComponent(chatPanel);
		layout.addComponent(footer);
		
		
		btnSend.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (messageField.isEmpty()) {
					Notification.show("Error",
							"Nie możesz wysłać pustej wiadomości",
							Type.TRAY_NOTIFICATION);
				} else {
					MessageBroadcaster.broadcast(new MessageData(messageField.getValue(), user.getName(), new Date()));

				}
			}
		});
		MessageBroadcaster.registerListener(this);
		
		

	}
	/*
	 * https://vaadin.com/forum/#!/thread/97069/1359208
	 */
/*	private void scrollIntoView() {
        final VerticalLayout layout = (VerticalLayout)chatContent.getContent();
        if (getApplication() != null && layout.getComponentCount() > 0)
            getApplication().getMainWindow().scrollIntoView(layout.getComponent(layout.getComponentCount() - 1));
    }*/


	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {

		private static final long serialVersionUID = 1L;
	}

	@Override
	public void receiveMessage(final MessageData messageData) {
		access(new Runnable() {
			
			@Override
			public void run() {
				chatContent.addComponent(new Label("("+messageData.getTime()+") <b>" + messageData.getAuthor()
						+ "</b>: " + messageData.text,
						ContentMode.HTML));
				messageField.setValue("");
/*				chatPanel.scroll*/
			}
		});
		
	}
		
	
}
