package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.work.WorkActivity;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;

import java.time.LocalDate;

//Выполненные работы
@Getter
@Setter
public class WorkActivityFilter implements DTOFilter {
    private WorkStageDto workStageDto;
    private String name;
    private LocalDate activityDateFrom;
    private LocalDate activityDateTo;
    private DictionaryValueDTO activityType;
    private DictionaryValueDTO measureType;
    private String amountFrom;
    private String amountTo;
    private String singleCostFrom;
    private String singleCostTo;
    private Integer progressFrom;
    private Integer progressTo;


}
