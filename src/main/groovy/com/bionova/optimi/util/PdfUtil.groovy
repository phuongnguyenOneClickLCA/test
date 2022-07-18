package com.bionova.optimi.util

import com.bionova.optimi.core.domain.mongo.EntityFile
import org.apache.commons.io.FileUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.pdfbox.cos.COSDictionary
import org.apache.pdfbox.cos.COSName
import org.apache.pdfbox.cos.COSString
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDDocumentCatalog
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDSimpleFont
import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.pdmodel.interactive.form.*

/**
 * Created by pmm on 1/11/16.
 */
class PdfUtil {

    private static Log log = LogFactory.getLog(PdfUtil.class)

    public static void populateAndCopy(String originalPdf, String targetPdf) {
        PDDocument pdfDocument = PDDocument.load(originalPdf)
    }

    public static void addImage(PDDocument pdfDocument, Map imageSettings, EntityFile entityFile) {
        if (pdfDocument && imageSettings && entityFile?.isImage) {
            try {
                int pageNo = imageSettings.get("page")
                // "y" : 202, "x" : 2, "width": 2, "height" : 2
                int y = imageSettings.get("x")
                int x = imageSettings.get("y")
                int width = imageSettings.get("width")
                int height = imageSettings.get("height")

                if (pageNo != null) {
                    pageNo = pageNo - 1
                }
                //we will add the image to the first page.
                if (pageNo > -1) {
                    PDPage page = pdfDocument.getPage(pageNo)

                    if (page) {
                        File file = new File(entityFile.name)
                        FileUtils.writeByteArrayToFile(file, entityFile.data)
                        PDImageXObject ximage = PDImageXObject.createFromFileByExtension(file, pdfDocument)
                        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page, true, true)
                        contentStream.drawXObject(ximage, x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
                        contentStream.close()
                        file.delete()
                    }
                }

            } catch (Exception e) {
                System.err.println("Error in adding image: ${e}")
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static List<PDField> getFields(PDDocument pdfDocument) throws IOException {
        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog()
        PDAcroForm acroForm = docCatalog.getAcroForm()
        List<PDField> returnableFields = []
        PDFieldTree fieldTree = acroForm.getFieldTree()
        PDSimpleFont font = PDType1Font.TIMES_ROMAN

        if (fieldTree) {
            for (PDField field : fieldTree) {
                if (field instanceof PDTextField || field instanceof PDCheckBox) {
                    returnableFields.add(field)
                }
            }
        }
        return returnableFields
    }

    @SuppressWarnings("rawtypes")
    public static String processField(PDField field, String sLevel, String sParent) throws IOException {
        String outputString = sLevel + sParent + "." + field.getPartialName() + ",  type=" + field.getClass().getName()
        return outputString
    }

    public static void setCheckbox(PDDocument pdfDocument, String name, Object value) {
        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog()
        PDAcroForm acroForm = docCatalog.getAcroForm()
        PDField field = acroForm.getField(name)

        if (field != null && field instanceof PDCheckBox) {
            if (value) {
                field.check()
            } else {
                field.unCheck()
            }
        } else {
            System.err.println("No field found with name: ${name} or field is not checkbox: ${field?.class?.simpleName}")
        }
    }

    public static void setTextField(PDDocument pdfDocument, String name, Object value) {
        PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog()
        PDAcroForm acroForm = docCatalog.getAcroForm()
        PDField field = acroForm.getField(name)

        if (field != null && field instanceof PDTextField) {
            // Line break fix
            if (value != null) {
                value = value.toString().replaceAll("\\r\\n|\\r|\\n", "\n")
                value = value.replaceAll("[\\u0009​]", " ")
                // We have to set field text to black, because replacing pages might set the color to blue!!?
                COSDictionary dict = field.getCOSObject()
                COSString defaultAppearance = (COSString) dict.getDictionaryObject(COSName.DA)

                if (defaultAppearance != null) {
                    dict.setString(COSName.DA, defaultAppearance.getString() + " 0 0 0 rg ")
                }

                try {
                    field.setValue(value)
                } catch (Exception e) {
                    log.error("Error in setting pdfTextField value: ${e}")
                }
            }
        } else {
            log.error("No field found with name:" + name)
        }
    }

    public static void flattenPDF(PDDocument document) {
        if (document) {
            PDDocumentCatalog catalog = document.getDocumentCatalog()
            PDAcroForm form = catalog.getAcroForm()

            if (form != null) {
                form.flatten()
            }
        }
    }

    public static void replacePage(PDDocument targetDoc, PDDocument sourceDoc, Integer targetPageIndex, Integer sourcePageIndex) {
        try {
            PDPage sourcePage = sourceDoc.getPage(sourcePageIndex.intValue())
            targetDoc.getPages().insertBefore(sourcePage, targetDoc.getPage(targetPageIndex.intValue()))
            targetDoc.getPages().remove(targetPageIndex.intValue() + 1)
        } catch (Exception e) {
            log.error("Error in replacing target page ${targetPageIndex + 1} with source page ${sourcePageIndex + 1}")
        }
    }
}
