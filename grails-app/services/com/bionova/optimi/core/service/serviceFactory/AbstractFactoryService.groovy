package com.bionova.optimi.core.service.serviceFactory

abstract class AbstractFactoryService<K, J> {

    private Map<K, J> serviceImplementations

    /**
     * Retrieves the value mapped to a specified key from the serviceImplementations map
     * @param key
     * @return specified implementation or null if no value matching the key is found
     */
    J getServiceImpl(K key) {
        return serviceImplementations.get(key)
    }

    void setServiceImplementations(Map<K, J> serviceImplementations) {
        this.serviceImplementations = serviceImplementations
    }

    Map<K, J> getServiceImplementations() {
        return serviceImplementations
    }

}
