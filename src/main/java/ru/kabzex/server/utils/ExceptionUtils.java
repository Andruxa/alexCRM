package ru.kabzex.server.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.dao.DataIntegrityViolationException;
import ru.kabzex.server.exception.CommonException;
import ru.kabzex.server.exception.NoSuchEnumException;

@UtilityClass
public class ExceptionUtils {
    public static Throwable handleException(Throwable e) {
        KnownExceptions ex = KnownExceptions.getByValue(e.getClass().getSimpleName());
        return switch (ex) {
            case UNIQUE_CONSTRAINT -> new NoSuchEnumException("Нарушение уникальности");
            default -> new CommonException("Ошибка. Сообщите администратору с указанием времени события");
        };
    }

    @Getter
    @AllArgsConstructor
    enum KnownExceptions {
        UNIQUE_CONSTRAINT(DataIntegrityViolationException.class.getSimpleName()),
        UNKNOWN("-1");
        private String className;

        public static KnownExceptions getByValue(String val) {
            for (KnownExceptions v : values()) {
                if (v.getClassName().equalsIgnoreCase(val)) {
                    return v;
                }
            }
            return UNKNOWN;
        }

    }
}
