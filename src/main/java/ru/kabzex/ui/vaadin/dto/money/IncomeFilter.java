package ru.kabzex.ui.vaadin.dto.money;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;

import java.time.LocalDate;

@Getter
@Setter
public class IncomeFilter implements DTOFilter {
    private String contract;
    private LocalDate incomeDateFrom;
    private LocalDate incomeDateTo;
    private String amount;
}
