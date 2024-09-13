package ru.kabzex.ui.vaadin.pages.dictionary;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractDataPage;
import ru.kabzex.ui.vaadin.pages.dictionary.parts.DictionaryBody;

@PageTitle("Справочники")
@Route(value = "dict", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN})
public class DictionaryPage extends AbstractDataPage {

    protected DictionaryPage(AuthenticationContext authenticationContext) {
        super(authenticationContext);
        add(initBody());
    }

    protected DictionaryBody initBody() {
        DictionaryBody body = new DictionaryBody();
        body.addCreateEventListener(this::handle);
        body.addUpdateEventListener(this::handle);
        body.addDeleteEventListener(this::handle);
        body.addFilterChangedEventListener(this::handle);
        body.addAttachListener(this::handle);
        return body;
    }


}
