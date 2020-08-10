package com.example.application.view;

import com.example.application.services.TableService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@CssImport(value = "styles/views/main/main-view.css", themeFor = "vaadin-app-layout")
@PWA(name = "TableUploader", shortName = "TableUploader")
@PreserveOnRefresh
@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@Route(value = "")
public class MainView extends AppLayout implements AfterNavigationObserver {

    private final Tabs menu;
    @Autowired
    TableService service;
    static TextField usertext;

    public MainView() {
        menu = createMenuTabs();
        usertext = new TextField();
        usertext.setReadOnly(true);
        Button logout = new Button("Logout");
        logout.addClickListener(e -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().navigate("login");
        });
        menu.add(logout);
        menu.add(usertext);
        addToNavbar(menu);
    }


    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "100%");
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Insert", insertView.class));
        tabs.add(createTab("Get", getView.class));
        tabs.add(createTab("Login", LoginView.class));
        tabs.get(2).setVisible(false);
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title, Class<? extends Component> viewClass) {
        return createTab(populateLink(new RouterLink(null, viewClass), title));
    }

    private static Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(content);
        return tab;
    }

    private static <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        selectTab();
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(getContent().getClass());
        Optional<Component> tabToSelect = menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> menu.setSelectedTab((Tab) tab));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        for (int i = 0; i < service.retrieveUsers().size(); i++) {
            if (!service.retrieveUsers().get(i).getUsername().equals(VaadinSession.getCurrent().getAttribute("username"))) {
                UI.getCurrent().navigate("login");
            }
        }
        usertext.setValue("Welcome, " + LoginView.username);
    }
}
