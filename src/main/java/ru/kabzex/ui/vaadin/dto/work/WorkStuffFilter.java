package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;

//Материалы
@Getter
@Setter
public class WorkStuffFilter implements DTOFilter {
    private String workStageDto;
    private String name;
    private DictionaryValueDTO measureType;
    private String amountFrom;
    private String amountTo;
    private String singleCostFrom;
    private String singleCostTo;
    private String deliveryCostFrom;
    private String deliveryCostTo;
}
