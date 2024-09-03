package ru.kabzex.ui.vaadin.pages.owner.dictionary.parts;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryTypeDTO;

public class DictionaryFooter extends AbstractPagePart {

    public DictionaryFooter() {
        setWidthFull();
        var button = new Button("Добавить запись справочника");
        button.addClickListener(this::addRecord);
        add(button);
    }


    private void addRecord(ClickEvent<Button> event) {
        fireEvent(new AddRecordEvent(this));
    }

    public class FooterEvent extends PagePartEvent<DictionaryTypeDTO> {

        protected FooterEvent(AbstractPagePart source) {
            super(source);
        }

    }

    public class AddRecordEvent extends FooterEvent {
        protected AddRecordEvent(DictionaryFooter source) {
            super(source);
        }
    }
}
