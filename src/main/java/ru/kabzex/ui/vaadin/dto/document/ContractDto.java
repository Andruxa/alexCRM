package ru.kabzex.ui.vaadin.dto.document;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.documents.Contract;
import ru.kabzex.server.entity.target.WorkObject;
import ru.kabzex.server.utils.DateTimeUtils;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.client.PersonClientDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ContractDto extends AbstractUpdatableDTO<Contract> {
    private String contractNumber;
    private LocalDate contractDate;
    private PersonClientDto contractor;
    private List<EmployeeDto> linkedEmployees;
    private List<WorkObject> workObjects;

    public String getFullDescription() {
        return String.format("%s от %s", contractNumber, DateTimeUtils.getAsSimpleDateString(contractDate));
    }

}
