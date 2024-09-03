package ru.kabzex.ui.vaadin.core.dialog;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ConfirmDialog extends Dialog {
    private NativeLabel title;
    private NativeLabel question;
    private Button confirm;
    private Button cancel;

    public ConfirmDialog() {
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
        createHeader();
        createQuestion();
        createFooter();
    }

    public ConfirmDialog(String title, String content, ComponentEventListener confirmListener) {
        this(title, content, confirmListener, null);
    }

    public ConfirmDialog(String title,
                         String content,
                         ComponentEventListener confirmListener,
                         ComponentEventListener cancelListener) {
        this();
        setTitle(title);
        setQuestion(content);
        addConfirmationListener(confirmListener);
        addCancelListener(cancelListener);
    }

    public void addCancelListener(ComponentEventListener cancelListener) {
        if (cancelListener != null)
            cancel.addClickListener(cancelListener);
    }

    public ConfirmDialog(String title, String content) {
        this(title, content, null);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setQuestion(String question) {
        this.question.setText(question);
    }

    public void addConfirmationListener(ComponentEventListener listener) {
        if (listener != null)
            confirm.addClickListener(listener);
    }

    void createHeader() {
        this.title = new NativeLabel();
        HorizontalLayout header = new HorizontalLayout();
        header.add(this.title);
        header.setFlexGrow(1, this.title);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        add(header);
    }

    void createQuestion() {
        question = new NativeLabel();
        VerticalLayout content = new VerticalLayout();
        content.add(question);
        content.setPadding(false);
        add(content);
    }

    void createFooter() {
        cancel = new Button("Отмена");
        cancel.addClickListener(buttonClickEvent -> close());
        confirm = new Button("ОК");
        confirm.addClickListener(buttonClickEvent -> close());
        HorizontalLayout footer = new HorizontalLayout();
        footer.add(confirm, cancel);
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        add(footer);
    }
}
