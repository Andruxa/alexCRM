package ru.kabzex.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dictionary {
    /**
     * Виды работ
     */
    WORK_TYPES("Виды работ"),
    /**
     * Единицы измерения
     */
    MEASURE_TYPES("Единицы измерения"),
    /**
     * Единицы измерения
     */
    EMPLOYEE_ROLES("Роли сотрудников");
    private final String description;
}
