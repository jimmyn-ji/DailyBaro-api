package com.project.hanfu.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionUtils {
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty() || coll.size() == 0);
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Map map) {
        return (map == null || map.isEmpty() || map.size() == 0);
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static <T> List findAll(Collection collection, Predicate<T> predicate) {
        return collection == null ? null : (List) collection.stream().filter(predicate).collect(Collectors.toList());
    }
}
