package com.project.transactions.util;

import java.util.Collection;
import java.util.Map;

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
}
