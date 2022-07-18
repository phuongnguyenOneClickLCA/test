package com.bionova.optimi.core.service

import com.bionova.optimi.core.domain.mongo.Resource
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

class DataCardService {

    String getEpdFirstPageB64(String epdFilePathPrefix, Resource resource) {
        String base64
        try {
            File file = new File("${epdFilePathPrefix}${resource.downloadLink}")
            if (file && file.exists()) {
                PDDocument document = PDDocument.load(file)
                PDFRenderer renderer = new PDFRenderer(document);
                BufferedImage image = renderer.renderImage(0)
                ByteArrayOutputStream os = new ByteArrayOutputStream()
                Base64 encoder = new Base64()
                ImageIO.write(image, "JPEG", encoder.getEncoder().wrap(os))
                base64 = os.toString("UTF-8")
                document.close()
            }
        } catch (Exception exception) {
            log.error("Error in processing the pdf file, resource details : ${resource.getNameEN()} , ${resource.getResourceId()}", exception)
        }
        return base64
    }

    int getRoundingNumber(Double value) {
        return value < 0.1 ? 4 : 2
    }
}
