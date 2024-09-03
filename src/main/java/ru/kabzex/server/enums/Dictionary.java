package ru.kabzex.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.kabzex.server.exception.NoSuchEnumException;

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

    public static Dictionary getByValue(String val) {
        for (Dictionary v : values()) {
            if (v.getDescription().equalsIgnoreCase(val)) {
                return v;
            }
        }
        throw new NoSuchEnumException(val);
    }
}
