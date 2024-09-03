package ru.kabzex.server.repository;

import ru.kabzex.server.entity.dictionary.DictionaryValue;
import ru.kabzex.server.enums.Dictionary;

import java.util.List;

public interface DictionaryValueRepository extends EntityRepository<DictionaryValue> {
    List<DictionaryValue> getAllByDictionaryAndDeletionDateIsNull(Dictionary dictionaryName);

    DictionaryValue getByDictionaryAndValueAndDeletionDateIsNull(Dictionary dictionaryName, String value);
}
