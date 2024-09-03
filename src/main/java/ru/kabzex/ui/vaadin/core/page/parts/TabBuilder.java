package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import lombok.Getter;

import java.util.LinkedHashMap;

public class TabBuilder {
    @Getter
    LinkedHashMap<Tab, Component> tabsToPages = new LinkedHashMap<>();
    @Getter
    Tabs tabs;

    public TabBuilder addNextPage(String label, Component component) {
        return addNextPage(new Tab(label), component);
    }

    public TabBuilder addNextPage(Tab tab, Component component) {
        component.setVisible(false);
        tabsToPages.put(tab, component);
        return this;
    }

    public Component build() {
        tabs = new Tabs(tabsToPages.keySet().toArray(Tab[]::new));
        tabs.setWidth("100%");
        Div pages = new Div(tabsToPages.values().toArray(Component[]::new));
        pages.setSizeFull();
        tabs.addSelectedChangeListener(event -> {
            Component previousPage = tabsToPages.get(event.getPreviousTab());
            previousPage.setVisible(false);
            Component selectedPage = tabsToPages.get(event.getSelectedTab());
            selectedPage.setVisible(true);
        });
        VerticalLayout vl = new VerticalLayout(tabs, pages);
        vl.setSizeFull();
        return vl;
    }

}
