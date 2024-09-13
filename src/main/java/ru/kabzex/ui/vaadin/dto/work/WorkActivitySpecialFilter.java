package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.work.WorkActivitySpecial;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

import java.time.LocalDate;

//Спецмонтаж
@Getter
@Setter
public class WorkActivitySpecialFilter implements DTOFilter {
    private String workStageDto;
    private String name;
    private LocalDate specialActivityDateFrom;
    private LocalDate specialActivityDateTo;
    private String amountFrom;
    private String amountTo;
    private String singleCostFrom;
    private String singleCostTo;
}
