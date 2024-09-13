package ru.kabzex.ui.vaadin.dto.client;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

import java.util.Set;

@Getter
@Setter
public class PersonClientDto extends AbstractUpdatableDTO {
    private String name;
    private Set<String> phoneNumbers;
    private Set<EmployeeDto> linkedEmployees;

    public String getShortInfo() {
        return String.format("%s(тел. %s)", name, String.join(",", phoneNumbers));
    }
}
