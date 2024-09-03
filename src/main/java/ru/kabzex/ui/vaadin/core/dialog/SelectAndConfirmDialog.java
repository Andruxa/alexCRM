package ru.kabzex.ui.vaadin.core.dialog;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class SelectAndConfirmDialog extends ConfirmDialog {
    private ComboBox<String> items;

    public SelectAndConfirmDialog() {
        removeAll();
        createHeader();
        createQuestion();
        createSelection();
        createFooter();
    }

    public SelectAndConfirmDialog(String title,
                                  String content,
                                  List<String> variables) {
        this();
        setTitle(title);
        setQuestion(content);
        setVaribales(variables);
    }

    private void setVaribales(List<String> variables) {
        items.setItems(variables);
        items.setValue(variables.get(0));
    }

    private void createSelection() {
        items = new ComboBox<>();
        items.setWidth("100%");
        VerticalLayout content = new VerticalLayout();
        content.add(items);
        content.setPadding(false);
        content.setWidth("100%");
        add(content);
    }

    public String getSelectedItem() {
        return items.getValue();
    }
}
