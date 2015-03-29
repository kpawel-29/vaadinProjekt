/*
 * emotikony źródło: http://cdn.vectorstock.com/i/composite/45,39/emoticons-vector-244539.jpg
 */

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
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ColorPicker;
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
import com.vaadin.ui.components.colorpicker.ColorChangeEvent;
import com.vaadin.ui.components.colorpicker.ColorChangeListener;

@Theme("mytheme")
@Widgetset("com.vaadin.vaadinProjekt.MyAppWidgetset")
@Push
public class MyUI extends UI implements MessageListener {

	private static final long serialVersionUID = 1L;
	final VerticalLayout chatContent = new VerticalLayout();
	final TextField messageField = new TextField();
	final Panel chatPanel = new Panel("Chat");
	final Window window = new Window("Podaj nick");
	final FormLayout loginForm = new FormLayout();
	final ColorPicker colorPicker = new ColorPicker();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		final User user = new User(0, "nieznajomy", "#0085D5");
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
		loginForm.addComponent(colorPicker);
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

		final Label lblWelcome = new Label("Witaj <b>" + user.getName()
				+ "</b>", ContentMode.HTML);

		header.addComponent(lblWelcome, "left: 10px; top: 5px;");
		header.addComponent(btnLogin, "right: 10px; top: 2px;");

		final HorizontalLayout footer = new HorizontalLayout();
		footer.addComponent(messageField);
		Button btnSend = new Button("Wyślij");
		footer.addComponent(btnSend);

		footer.setWidth(100.0f, Unit.PERCENTAGE);
		footer.setExpandRatio(messageField, 1.0f);

		messageField.setWidth(100.0f, Unit.PERCENTAGE);

		colorPicker.setSwatchesVisibility(false);
		colorPicker.setHistoryVisibility(false);
		colorPicker.setTextfieldVisibility(false);
		colorPicker.setHSVVisibility(false);
		// colorPicker.

		btnOK.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (loginField.isEmpty()) {
					Notification.show("Error", "Nick nie może być pusty.",
							Type.TRAY_NOTIFICATION);
				} else {
					window.close();
					user.setName(loginField.getValue().toString());
					lblWelcome.setValue("Witaj <b>" + user.getName() + "</b>");
				}
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
					MessageBroadcaster.broadcast(new MessageData(messageField
							.getValue(), user.getName(), new Date(), user.getColor()));

				}
			}
		});
		MessageBroadcaster.registerListener(this);

		/*
		 * http://demo.vaadin.com/sampler/#ui/data-input/other/color-picker
		 */
		colorPicker.addColorChangeListener(new ColorChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void colorChanged(final ColorChangeEvent event) {
				Notification.show(
						"Color changed: " + event.getColor().getCSS(),
						Type.TRAY_NOTIFICATION);
				user.setColor(event.getColor().getCSS());
			}

		});

	}


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
				chatContent.addComponentAsFirst(new Label("(" + messageData.getTime()
						+ ") <b>" + messageData.getAuthor()
						+ "</b>: <font color='" + messageData.getColor() + "'> "
						+ messageData.getText() + "</font>", ContentMode.HTML));
				messageField.setValue("");
				//System.out.println(java.awt.Color.decode(messageData.getColor()));
				/* chatPanel.scroll */
			}
		});

	}

}
