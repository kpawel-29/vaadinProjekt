package com.vaadin.vaadinProjekt;

import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
public class MyUI extends UI {

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		final Panel chatPanel = new Panel("Chat");
		chatPanel.setHeight("400px");

		final VerticalLayout chatContent = new VerticalLayout();

		chatContent.setMargin(true);
		chatContent.addComponent(new Label("czat"));

		chatPanel.setContent(chatContent);

		final AbsoluteLayout header = new AbsoluteLayout();
		header.setWidth(100.0f, Unit.PERCENTAGE);
		header.setHeight("50px");
		Button btnLogin = new Button("Zaloguj");
		Label lblWelcome = new Label("Witaj nieznajomy!");

		header.addComponent(lblWelcome, "left: 10px; top: 5px;");
		header.addComponent(btnLogin, "right: 10px; top: 2px;");
		header.addStyleName("outlined");// nie działa

		final HorizontalLayout footer = new HorizontalLayout();
		footer.setWidth(100.0f, Unit.PERCENTAGE);
		footer.setHeight("50px");
		Button btnSend = new Button("Wyślij");
		final TextField messageField = new TextField();
		messageField.setWidth(90.0f, Unit.PERCENTAGE);
		btnSend.setWidth(10.0f, Unit.PERCENTAGE);

		footer.addComponent(messageField);
		footer.addComponent(btnSend);

		layout.addComponent(header);
		layout.addComponent(chatPanel);
		layout.addComponent(footer);

		btnSend.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (messageField.isEmpty()) {
					Notification.show("Error",
							"Nie możesz wysłać pustej wiadomości",
							Type.TRAY_NOTIFICATION);
				} else {
					chatContent.addComponent(new Label(messageField.getValue()));
					messageField.setValue("");
				}
			}
		});

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
