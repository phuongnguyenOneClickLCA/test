package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.EntityFile
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.grails.OptimiMessageSource
import com.bionova.optimi.util.ErrorMessageUtil
import com.bionova.optimi.xml.fecEPD.EPDC
import com.bionova.optimi.xml.fecRSEnv.Projet
import com.bionova.optimi.xml.fecRSEnv.RSET
import com.bionova.optimi.xml.fecRSEnv.RSEnv
import org.apache.commons.lang.StringUtils
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.web.multipart.MultipartFile

import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller

class FecService {

    def loggerUtil
    def flashService
    OptimiMessageSource messageSource
    ErrorMessageUtil errorMessageUtil

    private static final String FEC_RSET_ERROR_MSG = "Le fichier RSET chargé n'est pas compatible avec la version RSEE 1.1.0.0, merci de charger un RSET > 8.1.0.0"

    Projet getProjetFromEntityFile(EntityFile entityFile, MultipartFile multipartFile = null) {
        Projet projet

        if (entityFile || multipartFile) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Projet.class)
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller()
                File file = File.createTempFile(UUID.randomUUID().toString(), ".xml")
                OutputStream outputStream = new FileOutputStream(file)
                outputStream.write(entityFile ? entityFile.data : multipartFile.bytes)
                outputStream.close()

                String fileStr = file.getText("UTF-8")
                String before = StringUtils.substringBefore(fileStr, "<Datas_Comp>")
                String after = StringUtils.substringAfter(fileStr, "<Datas_Comp>")

                String idfiche = StringUtils.substringBetween(fileStr, "idfiche=\"", "\"")
                String idxsd = StringUtils.substringBetween(fileStr, "idxsd=\"", "\"")
                String idxsl = StringUtils.substringBetween(fileStr, "idxsl=\"", "\"")

                boolean isRseeFormat = fileStr.contains("RSET")

                if (isRseeFormat) {
                    before = before + "\n\t<Datas_Comp>"
                } else {
                    before = before + "<RSET idfiche=\"${idfiche}\" idxsd=\"${idxsd}\" idxsl=\"${idxsl}\">\n\t<Datas_Comp>"
                }

                String rsetString = before + after

                before = StringUtils.substringBefore(rsetString, "</Sortie_Projet>")
                after = StringUtils.substringAfter(rsetString, "</Sortie_Projet>")

                if (isRseeFormat) {
                    before = before + "</Sortie_Projet>\n\t"
                } else {
                    before = before + "</Sortie_Projet>\n\t</RSET>"
                }

                rsetString = before + after

                File rsetxml = File.createTempFile(UUID.randomUUID().toString(), ".xml")
                Writer writer = new FileWriter(rsetxml)
                writer.write(rsetString)
                writer.close()

                projet = unmarshaller.unmarshal(rsetxml) as Projet

                rsetxml.delete()
                file.delete()
            } catch (Exception e) {
                loggerUtil.error(log, "Error in creating RSET from file", e)
                flashService.setErrorAlert("Error in creating RSET from file: ${e.message}", true)
            }
        }
        return projet
    }

    EPDC getFecEpdFromFile(EntityFile entityFile) {
        EPDC epdc

        if (entityFile) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(EPDC.class)
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller()
                File file = File.createTempFile(UUID.randomUUID().toString(), ".xml")
                OutputStream outputStream = new FileOutputStream(file)
                outputStream.write(entityFile.data)
                outputStream.close()
                epdc = unmarshaller.unmarshal(file)
                file.delete()
            } catch (Exception e) {
                loggerUtil.error(log, "Error in creating EPDC from file:", e)
                flashService.setErrorAlert("Error in creating EPDC from file: ${e.message}", true)
            }
        }
        return epdc
    }

    EntityFile getFecRsetEntityFile(String parentEntityId) {
        if (parentEntityId) {
            return EntityFile.findByEntityIdAndQueryIdAndQuestionId(parentEntityId, FrenchConstants.FEC_QUERYID_PROJECT_LEVEL, FrenchConstants.FEC_RSET_QUESTIONID)
        }
        return null
    }

    boolean isFecRsetFileOk(MultipartFile file) {
        RSET rset = getRsetFromEntityFile(null, file)

        if (!rset) {
            errorMessageUtil.setErrorMessage(messageSource.getMessage("xml.invalidFile_error", null, LocaleContextHolder.getLocale()), true, true)
            return false
        } else if (rset && !(FrenchConstants.FEC_RSET_VERSION == rset.entreeProjet?.getVersion() && FrenchConstants.FEC_RSET_VERSION == rset.sortieProjet?.getVersion())) {
            errorMessageUtil.setErrorMessage(FEC_RSET_ERROR_MSG, true, true)
            return false
        }

        return true
    }

    /**
     * Check in parent entity if FEC tool is activated
     * @param parent
     * @return
     */
    Boolean isFecToolActivated(Entity parent) {
        if (parent?.indicatorIds) {
            return parent?.indicatorIds?.any { it in FrenchConstants.FEC_TOOLS }
        }
        return false
    }

    RSET getRsetFromEntityFile(EntityFile entityFile, MultipartFile multipartFile = null) {
        return getProjetFromEntityFile(entityFile, multipartFile)?.getRSET()
    }

    RSEnv getRsenvFromFile(EntityFile entityFile, MultipartFile multipartFile = null) {
        return getProjetFromEntityFile(entityFile, multipartFile)?.getRSEnv()
    }
}
