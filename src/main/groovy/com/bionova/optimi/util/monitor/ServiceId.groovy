package com.bionova.optimi.util.monitor

import org.apache.commons.lang3.StringUtils

enum ServiceId {
    CALCULATION_SERVICE("CalculationService"),
    OPTIMI("360Optimi"),
    UNDEFINED("UNDEFINED");

    private final String name
    private final String nameLower

    private ServiceId(String name) {
        this.name = name
        this.nameLower = name.toLowerCase()
    }

    String getName() {
        return this.name
    }

    static ServiceId fromName(String name) {
        if (!StringUtils.isEmpty(name)) {
            String lowerName = name.toLowerCase()
            ServiceId[] var2 = values()
            int var3 = var2.length

            for (int var4 = 0; var4 < var3; ++var4) {
                ServiceId serviceId = var2[var4]
                if (serviceId.nameLower.equals(lowerName)) {
                    return serviceId
                }
            }
        }

        throw new IllegalArgumentException(String.format("Invalid service name '%s'", name))
    }
}