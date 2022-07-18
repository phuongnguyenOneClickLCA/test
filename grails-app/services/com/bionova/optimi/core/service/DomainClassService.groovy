package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import grails.plugin.cache.Cacheable
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.datastore.mapping.model.PersistentProperty


class DomainClassService {

    def grailsDomainClassMappingContext

    public PersistentEntity getPersistentEntityForDomainClass(Class clazz) {
        if (clazz) {
            return grailsDomainClassMappingContext.getPersistentEntity(clazz.getName())
        }
        return null
    }

    public List<String> getPersistentPropertyNamesForDomainClass(Class clazz, type = null) {
        if (clazz) {
            if (type) {
                return grailsDomainClassMappingContext.getPersistentEntity(clazz.getName())?.getPersistentProperties()?.findAll({ type == it.getType() && !"version".equals(it.name) })?.collect({ it.name })
            } else {
                return grailsDomainClassMappingContext.getPersistentEntity(clazz.getName())?.getPersistentPropertyNames()?.findAll({ !"version".equals(it) })
            }
        }
        return null
    }

    public List<PersistentProperty> getPersistentPropertiesForDomainClass(Class clazz) {
        if (clazz) {
            return grailsDomainClassMappingContext.getPersistentEntity(clazz.getName())?.getPersistentProperties()?.findAll({!"version".equals(it.name)})
        }
        return null
    }

    public List<PersistentProperty> getMandatoryPropertiesForDomainClass(Class clazz) {
        if (clazz) {
            return grailsDomainClassMappingContext.getPersistentEntity(clazz.getName())?.getPersistentProperties().findAll({ !it.nullable && !"version".equals(it.name) })
        }
        return null
    }

    public List<String> getMandatoryPropertyNamesForDomainClass(Class clazz) {
        return getMandatoryPropertiesForDomainClass(clazz)?.collect({ it.name })
    }

    // A temporary method for nmd so we could cache it without regression. HUNG: should cache almost all methods in this class for next release
    @Cacheable(Constants.PERSISTENT_PROPERTIES_DOMAIN)
    List<String> getPersistentPropertyNamesForDomainClassForNmd(Class clazz, type) {
        if (clazz) {
            if (type) {
                return grailsDomainClassMappingContext.getPersistentEntity(clazz.getName())?.getPersistentProperties()?.findAll({ type == it.getType() && !"version".equals(it.name) })?.collect({ it.name })
            } else {
                return grailsDomainClassMappingContext.getPersistentEntity(clazz.getName())?.getPersistentPropertyNames()?.findAll({ !"version".equals(it) })
            }
        }
        return null
    }
}
