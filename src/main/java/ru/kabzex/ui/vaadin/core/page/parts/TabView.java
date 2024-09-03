package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;

public interface TabView {

    Tab getTab();

    Component getComponent();
}
