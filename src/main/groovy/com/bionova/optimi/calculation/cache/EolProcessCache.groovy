package com.bionova.optimi.calculation.cache

import com.bionova.optimi.core.domain.mongo.EolProcess
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

@CompileStatic
class EolProcessCache {
    private Map<String, EolProcess> cache = new HashMap<>();

    static EolProcessCache init() {
        EolProcessCache eolProcessCache = new EolProcessCache()
        List<EolProcess> eolProcess = EolProcess.list()
        eolProcessCache.cache = eolProcess.collectEntries() { [(it.eolProcessId), it] }

        return eolProcessCache
    }

    EolProcess getEolProcessByEolProcessId(String eolProcessId) {
        if (!eolProcessId) {
            return null;
        }

        return cache.get(eolProcessId)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    List<EolProcess> getEolProcessesByEolProcessIds(List<String> eolProcessIds) {
        if (!eolProcessIds) {
            return null;
        }

        return cache.findResults{
            if(it.key in eolProcessIds){
                return it.value
            }
        }
    }
}
