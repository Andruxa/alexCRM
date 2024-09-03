package ru.kabzex.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.about.AboutView;
import ru.kabzex.ui.vaadin.pages.contracts.ClientContractsPage;
import ru.kabzex.ui.vaadin.pages.dictionary.DictionaryPage;
import ru.kabzex.ui.vaadin.pages.employee.EmployeePage;
import ru.kabzex.ui.vaadin.pages.incomes.IncomesPage;
import ru.kabzex.ui.vaadin.pages.specialActivities.SpecialActivitiesPage;
import ru.kabzex.ui.vaadin.pages.stuff.StuffPage;
import ru.kabzex.ui.vaadin.pages.workstages.WorkStagesPage;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {
    private final AuthenticationContext authenticationContext;

    private H2 viewTitle;

    public MainLayout(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE,
                LumoUtility.Flex.GROW);
        var logout = new Button("Logout " + authenticationContext.getPrincipalName().orElse(""),
                event -> authenticationContext.logout());

        var header = new Header(toggle, viewTitle, logout);
        header.addClassNames(LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX,
                LumoUtility.Padding.End.MEDIUM, LumoUtility.Width.FULL);

        addToNavbar(false, header);
    }

    private void addDrawerContent() {
        Span appName = new Span("Формула ремонта");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("О нас", AboutView.class, LineAwesomeIcon.FILE.create()));
        nav.addItem(new SideNavItem("Итог", ClientContractsPage.class, LineAwesomeIcon.FILE_EXCEL_SOLID.create()));
        if (authenticationContext.hasAnyRole(Roles.ADMIN, Roles.EMPLOYEE)) {
            nav.addItem(new SideNavItem("Платежи", IncomesPage.class, LineAwesomeIcon.FILE_EXCEL_SOLID.create()));
            nav.addItem(new SideNavItem("Работы", WorkStagesPage.class, LineAwesomeIcon.FILE_EXCEL_SOLID.create()));
            nav.addItem(new SideNavItem("Материалы", StuffPage.class, LineAwesomeIcon.FILE_EXCEL_SOLID.create()));
            nav.addItem(new SideNavItem("Спецмонтаж", SpecialActivitiesPage.class, LineAwesomeIcon.FILE_EXCEL_SOLID.create()));
        }
        if (authenticationContext.hasAnyRole(Roles.ADMIN)) {
            nav.addItem(new SideNavItem("Сотрудники", EmployeePage.class, LineAwesomeIcon.FILE_EXCEL_SOLID.create()));
            nav.addItem(new SideNavItem("Справочники", DictionaryPage.class, LineAwesomeIcon.FILE_EXCEL_SOLID.create()));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
