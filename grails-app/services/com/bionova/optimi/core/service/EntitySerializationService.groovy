package com.bionova.optimi.core.service


import com.bionova.optimi.calculation.dto.EntityDto
import com.bionova.optimi.core.domain.mongo.Entity
import org.modelmapper.ModelMapper

class EntitySerializationService {
    private final ModelMapper modelMapper = new ModelMapper()

    EntityDto convertToDto(Entity domain) {
        if (domain == null) {
            return null
        }

        domain.calculationResults.each { println(it?.id) }

        return modelMapper.map(domain, EntityDto.class)
    }
}
