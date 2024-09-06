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
        KnownExceptions ex = KnownExceptions.getByValue(e.getClass());
        return switch (ex) {
            case UNIQUE_CONSTRAINT -> new NoSuchEnumException("Нарушение уникальности");
            case KNOWN_EXCEPTIONS -> e;
            default -> new CommonException("Ошибка. Сообщите администратору с указанием времени события");
        };
    }

    @Getter
    @AllArgsConstructor
    enum KnownExceptions {
        KNOWN_EXCEPTIONS("ru.kabzex"),
        UNIQUE_CONSTRAINT(DataIntegrityViolationException.class.getSimpleName()),
        UNKNOWN("-1");
        private String className;

        public static KnownExceptions getByValue(Class cl) {
            if(cl.getPackageName().contains(KNOWN_EXCEPTIONS.getClassName())) return KNOWN_EXCEPTIONS;
            for (KnownExceptions v : values()) {
                if (v.getClassName().equalsIgnoreCase(cl.getSimpleName())) {
                    return v;
                }
            }
            return UNKNOWN;
        }

    }
}
