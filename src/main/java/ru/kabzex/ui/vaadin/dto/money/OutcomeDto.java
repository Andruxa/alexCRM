package ru.kabzex.ui.vaadin.dto.money;

import ru.kabzex.server.entity.money.Outcome;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;

import java.time.LocalDateTime;

public class OutcomeDto extends AbstractUpdatableDTO<Outcome> {
    private LocalDateTime incomeDate;
    private String amount;
}
