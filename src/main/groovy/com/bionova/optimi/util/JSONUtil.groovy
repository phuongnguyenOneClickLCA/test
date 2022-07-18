package com.bionova.optimi.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import grails.compiler.GrailsCompileStatic
import org.springframework.web.multipart.MultipartFile

@GrailsCompileStatic
class JSONUtil {

    /**
     * This method return true if the given file's content is in correct JSON format using Jackson library
     * @param jsonFile
     * @return
     */
    public static boolean validateJSONFormat(MultipartFile jsonFile) {
        File tempFile = File.createTempFile("tempForValidate", ".json")
        jsonFile.transferTo(tempFile)
        String fileContent = tempFile.text
        try {
            ObjectMapper mapper = new ObjectMapper()
            mapper.configure(DeserializationFeature.FAIL_ON_TRAILING_TOKENS, true)
            mapper.readValue(fileContent, Map)
            return true
        } catch (exception) {
            return false
        }
    }
}
