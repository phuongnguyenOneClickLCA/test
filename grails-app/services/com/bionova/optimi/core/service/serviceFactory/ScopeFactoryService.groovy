package com.bionova.optimi.core.service.serviceFactory

import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.service.scope.EPDScopeService
import com.bionova.optimi.core.service.scope.LCAScopeService
import com.bionova.optimi.core.service.scope.ScopeService
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

class ScopeFactoryService extends AbstractFactoryService<String, ScopeService> {

    @Autowired
    LCAScopeService lcaScopeService
    @Autowired
    EPDScopeService epdScopeService

    /**
     * Sets up the serviceImplementations map with instances of Scope Services mapped to EntityClass enums
     */
    @PostConstruct
    private void init() {
        Map<String, ScopeService> scopeServiceImplementations = [(EntityClass.BUILDING.toString()): lcaScopeService,
                                                                    (EntityClass.PRODUCT.toString()): epdScopeService]
        setServiceImplementations(scopeServiceImplementations)
    }

    /**
     * Retrieves a scope service instance if a specific scope service is found,
     * otherwise returns LCAScopeService
     * @param key
     * @return scope service mapped to the key or the instance of LCAScopeService
     */
    @Override
    ScopeService getServiceImpl(String key) {
        if (serviceImplementations.containsKey(key)) {
            return serviceImplementations.get(key)
        } else {
            // for all other project types (not LCA or EPD), LCA scope service is used
            return getServiceImpl(EntityClass.BUILDING.toString())
        }
    }
}
