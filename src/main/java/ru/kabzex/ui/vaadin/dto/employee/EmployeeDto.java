package ru.kabzex.ui.vaadin.dto.employee;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.employee.Employee;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;

import java.util.Optional;

@Getter
@Setter
public class EmployeeDto extends AbstractUpdatableDTO<Employee> {
    private String name;
    private DictionaryValueDTO position;

    public String getPositionValue() {
        return Optional.ofNullable(position).map(DictionaryValueDTO::getValue).orElse("Пусто");
    }
}
