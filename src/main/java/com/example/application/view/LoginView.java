package com.example.application.view;

import com.example.application.model.database.User;
import com.example.application.services.TableService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login", layout = MainView.class)
@CssImport("styles/views/form/form-view.css")
public class LoginView extends Div {

    static String username;
    static int userId;
    LoginOverlay loginOverlay;

    @Autowired
    TableService service;

    public LoginView() {
        loginOverlay = new LoginOverlay();
        loginOverlay.setTitle("Table uploader");
        loginOverlay.setDescription("");
        loginOverlay.setOpened(true);
        loginOverlay.addLoginListener(e -> {
            User user = new User(e.getUsername(), e.getPassword());
            for (int i = 0; i < service.retrieveUsers().size(); i++) {
                if (service.retrieveUsers().get(i).getUsername().equals(e.getUsername()) && service.retrieveUsers().get(i).getPassword().equals(e.getPassword())) {
                    username = user.getUsername();
                    VaadinSession.getCurrent().setAttribute("username", user.getUsername());
                    userId = user.getId();
                    loginOverlay.close();
                    UI.getCurrent().navigate("insert");
                    break;
                }
            }
        });
    }
}
