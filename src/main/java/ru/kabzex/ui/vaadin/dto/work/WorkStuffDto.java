package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.work.WorkStuff;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;

//Материалы
@Getter
@Setter
public class WorkStuffDto extends AbstractUpdatableDTO<WorkStuff> {
    private WorkStageDto workStageDto;
    private String name;
    private DictionaryValueDTO measureType;
    private String amount;
    private String singleCost;
    private String deliveryCost;
}
