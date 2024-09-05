package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.html.NativeLabel;
import ru.kabzex.server.utils.StringUtils;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;

public class WorkObjectHeader extends AbstractPagePart {
    private NativeLabel label;

    public WorkObjectHeader() {
        setWidth("100%");
        label = new NativeLabel();
        setVisible(false);
        add(label);
    }

    public void setLabel(String value) {
        var text = String.format("Выбранный объект: %s", value);
        label.setText(text);
        label.setVisible(StringUtils.isEmpty(value));
    }

}
