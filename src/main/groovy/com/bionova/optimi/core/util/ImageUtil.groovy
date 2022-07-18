package com.bionova.optimi.core.util

import com.bionova.optimi.core.service.FlashService
import net.coobird.thumbnailator.Thumbnails
import net.coobird.thumbnailator.geometry.Positions
import net.coobird.thumbnailator.resizers.Resizers
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage

/**
 * @author Pasi-Markus Mäkelä
 */
class ImageUtil {
    private static Log log = LogFactory.getLog(ImageUtil.class)

    LoggerUtil loggerUtil
    FlashService flashService

    public static void cropImage(File sourceFile, File targetFile, int width, int height) {
        if (sourceFile && targetFile) {
            BufferedImage bufferedImage = ImageIO.read(sourceFile)
            int originalWidth = bufferedImage.width
            int originalHeight = bufferedImage.height
            int newWidth
            int newHeight

            if (originalHeight > originalWidth) {
                newWidth = originalWidth
                newHeight = originalWidth
            } else {
                newWidth = originalHeight
                newHeight = originalHeight
            }
            Thumbnails.of(sourceFile).sourceRegion(Positions.CENTER_LEFT, newWidth, newHeight)
                    .resizer(Resizers.BICUBIC).size(width, height).toFile(targetFile)
        }
    }

    /**
     * Calculate the dimension of the image from the given dimension (set in config) so that the image aspect ratio is preserved.
     * @param image
     * @param boundaryWidth width that image can have
     * @param boundaryHeight height that image can have
     * @return {@link Dimension} object containing scaled height and width
     */
    Dimension calculateScaledDimensionToMaintainAspectRatio(File image, Integer boundaryWidth, Integer boundaryHeight) {
        Dimension scaledDim = null
        try {
            if (image != null && boundaryWidth != null && boundaryHeight != null) {
                BufferedImage bufferedImage = ImageIO.read(image)

                if (bufferedImage) {
                    int originalWidth = bufferedImage.width
                    int originalHeight = bufferedImage.height
                    int newWidth = originalWidth
                    int newHeight = originalHeight

                    /*
                        Scale the width if it's bigger than the boundary.
                        Also scale the height to maintain aspect ratio
                     */
                    if (originalWidth > boundaryWidth) {
                        newWidth = boundaryWidth
                        newHeight = (Integer) ((newWidth * originalHeight) / originalWidth)
                    }

                    /*
                        Scale the height if it's bigger than the boundary (even the new height)
                        Also scale the width to maintain aspect ratio
                     */
                    if (newHeight > boundaryHeight) {
                        newHeight = boundaryHeight
                        newWidth = (Integer) ((newHeight * originalWidth) / originalHeight)
                    }

                    scaledDim = new Dimension(newWidth, newHeight)
                }
            }
        } catch (e) {
            loggerUtil.error(log, "Error in calculateScaledDimensions for image: ${image?.getPath()}", e)
            flashService.setErrorAlert("Error in calculateScaledDimensions for image: ${image?.getPath()}: ${e.getMessage()}", true)
        }
        return scaledDim
    }
}
