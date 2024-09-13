package ru.kabzex.ui.vaadin.dto.money;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.money.Income;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class IncomeDto extends AbstractUpdatableDTO<Income> {
    private ContractDto contractDto;
    private LocalDateTime incomeDate;
    private String amount;
}
