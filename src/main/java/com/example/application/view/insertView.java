package com.example.application.view;

import com.example.application.services.FileService;
import com.example.application.services.TableService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

@Route(value = "insert", layout = MainView.class)
@CssImport("styles/views/form/form-view.css")
@PreserveOnRefresh
//This is the insert view class
public class insertView extends Div implements AfterNavigationObserver {
    private final Button uploadButton = new Button("Upload");
    @Autowired
    FileService fileService;
    InputStream inputStream;

    MemoryBuffer memoryBuffer;
    Upload upload;

    @Autowired
    TableService service;


    //sets up the layout items
    public insertView() {
        setId("insert-view");
        VerticalLayout wrapper = createWrapper();
        createTitle(wrapper);
        fileLoad(wrapper);
        fileUploadButton(wrapper);
        uploadButton.addClickListener(e ->
        {

            fileService.readWorkbook(inputStream, memoryBuffer, LoginView.userId);

        });
        add(wrapper);
    }

    //form title
    private void createTitle(VerticalLayout wrapper) {
        H1 h1 = new H1("Table Uploader");
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

    public void fileLoad(VerticalLayout wrapper) {
        memoryBuffer = new MemoryBuffer();
        upload = new Upload(memoryBuffer);

        upload.addFinishedListener(e -> {
            inputStream = memoryBuffer.getInputStream();
        });

        wrapper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        wrapper.add(upload);
    }

    //sets up the upload button
    private void fileUploadButton(VerticalLayout wrapper) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(uploadButton);
        wrapper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        wrapper.add(buttonLayout);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        for (int i = 0; i < service.retrieveUsers().size(); i++) {
            if (!service.retrieveUsers().get(i).getUsername().equals(VaadinSession.getCurrent().getAttribute("username"))) {
                UI.getCurrent().navigate("login");
            }
        }
    }
}