package com.example.application.view;

import com.example.application.model.database.Person;
import com.example.application.services.TableService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Route(value = "getdata", layout = MainView.class)
@CssImport("styles/views/form/form-view.css")
@PreserveOnRefresh
public class getView extends Div implements AfterNavigationObserver {

    private final Button deleteButton = new Button("Delete");
    @Autowired
    TableService service;
    Grid<Person> grid = new Grid<>(Person.class);
    List<Person> persons;
    ListDataProvider<Person> dataProvider;
    List<Person> selectedPersons;
    TextField firstNameField;

    public getView() {
        VerticalLayout wrapper = createWrapper();
        setId("get-view");
        createTitle(wrapper);
        createGridLayout(wrapper);
        deleteButton(wrapper);
        selectedPersons = new ArrayList<>();

        grid.asMultiSelect().addSelectionListener(e -> {
            if (e.getAllSelectedItems().isEmpty()) {
                grid.deselectAll();
                selectedPersons.clear();
            } else if (e.getAddedSelection().isEmpty()) {
                grid.deselectAll();
                selectedPersons.clear();
            } else {
                selectedPersons.addAll(e.getAllSelectedItems());
            }
        });

        grid.setItemDetailsRenderer(
                new ComponentRenderer<>(person -> {
                    VerticalLayout layout = new VerticalLayout();
                    if (person.getAddress() != null) {
                        layout.add(new Label(" Address: " +
                                person.getAddress().getCity() + " " +
                                person.getAddress().getStreet() + " " +
                                person.getAddress().getNumber()));
                    } else {
                        layout.add(new Label("No address registered."));
                    }
                    layout.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, layout);
                    return layout;
                }));

        deleteButton.addClickListener(e -> {
            deleteSelectedPersons(grid, selectedPersons, service);
        });

        firstNameField.addValueChangeListener(event -> {
            dataProvider.addFilter(
                    person -> StringUtils.containsIgnoreCase(person.getName(),
                            firstNameField.getValue()));

        });

        add(wrapper);

    }

    private static void deleteSelectedPersons(Grid<Person> grid, List<Person> selectedPersons, TableService service) {
        ListDataProvider<Person> dataProvider =
                (ListDataProvider<Person>) grid
                        .getDataProvider();
        for (Person selectedPerson : selectedPersons) {
            if (selectedPersons.contains(selectedPerson))
           service.delete(selectedPerson);
        }
        Notification.show("persons deleted").setPosition(Notification.Position.BOTTOM_CENTER);
        dataProvider.getItems().removeAll(selectedPersons);
        selectedPersons.clear();
        dataProvider.refreshAll();
    }

    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Table Uploader");
        wrapper.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, h1);
        wrapper.add(h1);
        wrapper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    //wrapper method
    private VerticalLayout createWrapper() {
        VerticalLayout wrapper = new VerticalLayout();
        wrapper.setId("wrapper");
        wrapper.setSpacing(false);
        return wrapper;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        persons = service.retrieve();
        dataProvider = new ListDataProvider<>(persons);
        grid.setDataProvider(dataProvider);
        for (int i = 0; i < service.retrieveUsers().size(); i++) {
            if (!service.retrieveUsers().get(i).getUsername().equals(VaadinSession.getCurrent().getAttribute("username"))) {
                UI.getCurrent().navigate("login");
            }
        }
    }

    private void createGridLayout(VerticalLayout wrapper) {
        grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        wrapper.setWidthFull();
        wrapper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        grid.setColumns("id", "name", "lastName", "phoneNumber");

        HeaderRow filterRow = grid.appendHeaderRow();
        firstNameField = new TextField();
        firstNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("name")).setComponent(firstNameField);
        firstNameField.setPlaceholder("Filter");
        wrapper.add(firstNameField, grid);
    }

    private void deleteButton(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(deleteButton);
        wrapper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.END);
        wrapper.add(buttonLayout);
    }
}
