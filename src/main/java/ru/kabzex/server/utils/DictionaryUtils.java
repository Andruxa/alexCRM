package ru.kabzex.server.utils;

import lombok.experimental.UtilityClass;
import ru.kabzex.server.entity.dictionary.DictionaryValue;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class DictionaryUtils {

    public static String parseNullableDictionaryValue(DictionaryValue dictionaryValue) {
        return Optional.ofNullable(dictionaryValue).map(DictionaryValue::getValue).orElse("");
    }

    public static String parseNullableDictionaryValueSet(Set<DictionaryValue> dictionaryValueSet) {
        return Stream.ofNullable(dictionaryValueSet)
                .flatMap(Collection::stream)
                .map(DictionaryUtils::parseNullableDictionaryValue)
                .filter(Predicate.not(String::isEmpty))
                .collect(Collectors.joining(","));
    }
}
