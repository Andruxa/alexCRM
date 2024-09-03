package ru.kabzex.server.utils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamUtils {
    public static Predicate combine(Predicate... filters) {
        if (filters == null) return x -> true;
        return Arrays.stream(filters)
                .reduce(Predicate::and)
                .orElse(x -> true);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static <T> Stream<T> nullSafeStream(Collection<T> collection) {
        return Optional.ofNullable(collection).orElse(Collections.emptySet()).stream();
    }
}
