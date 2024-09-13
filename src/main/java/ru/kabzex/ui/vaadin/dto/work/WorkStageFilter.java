package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.work.WorkStage;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;

//Этапы
@Getter
@Setter
public class WorkStageFilter implements DTOFilter {

    private String workObject;
    private String serialNumber;
    private String name;
}
