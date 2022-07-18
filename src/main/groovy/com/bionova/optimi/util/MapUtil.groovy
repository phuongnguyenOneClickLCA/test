package com.bionova.optimi.util

/**
 * Created by pmm on 4/25/17.
 */
class MapUtil {

    public static Map mergeResultMaps(Map a, Map b) {
        Map y = a.inject([:]) { result, e -> b.keySet().contains(e.key) ? result << [(e.key): e.value + b[e.key]] : result << e }
        Map yy = b.findAll { e -> !a.keySet().contains(e.key) }
        return y + yy
    }
}
