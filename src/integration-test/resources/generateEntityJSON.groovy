import com.bionova.optimi.core.domain.mongo.Entity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import grails.util.Holders

import java.nio.file.Paths

Map<String, String> names = [
        '5badc82d8e202b0e1f9d9fbd': 'globalLifeCycleCarbon-5badc82d8e202b0e1f9d9fbd',
        '5badd7ff8e202b0e1f9dc5f2': 'LCC_LCA_Automated-5badd7ff8e202b0e1f9dc5f2',
        '617f85f2cc95e94e51e01bc6': 'lcaForRE2020-617f85f2cc95e94e51e01bc6',
        '617f8b1ecc95e94e51e0337c': 'lcaForRE2020-617f8b1ecc95e94e51e0337c',
]

names.each { String dbId, String fileName ->
    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT)
    Entity entity = Holders.getApplicationContext().getBean("entityService").getEntityById(dbId)

    def converted = Holders.getApplicationContext().getBean("entitySerializationService").convertToDto(entity)


    File file = Paths.get("src/integration-test/resources/${fileName}.json").toFile()
    mapper.writeValue(file, converted)
}
