package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.AccountImages
import com.bionova.optimi.core.util.DomainObjectUtil
import org.apache.commons.codec.binary.Base64

class AccountImagesService {

    def getAccountImage(String id) {
        AccountImages accountImage

        if (id) {
            accountImage = AccountImages.get(DomainObjectUtil.stringToObjectId(id))
        }
        return accountImage
    }

    def getAccountImagesByAccountIdAndType(String accountId, String type) {
        List<AccountImages> accountImagesList = []
        if (accountId && type) {
            accountImagesList = AccountImages.collection.find([accountId: accountId, type: type])?.collect({it as AccountImages})
        }
        return accountImagesList
    }

    def getAllAccountImagesByType(String type) {
        List<AccountImages> accountImagesList = []
        if (type) {
            accountImagesList = AccountImages.collection.find([type: type])?.collect({it as AccountImages})
        }
        return accountImagesList
    }

    def getDefaultProductImage(String defaultProductImageId) {
        AccountImages accountImage
        if (defaultProductImageId) {
            accountImage = AccountImages.collection.find([_id: DomainObjectUtil.stringToObjectId(defaultProductImageId), type: Constants.AccountImageType.PRODUCTIMAGE.toString()]) as AccountImages
        }
        return accountImage
    }

    def getDefaultManufacturingDiagram(String defaultManufacturingDiagramId) {
        AccountImages accountImage
        if (defaultManufacturingDiagramId) {
            accountImage = AccountImages.collection.find([_id: DomainObjectUtil.stringToObjectId(defaultManufacturingDiagramId), type: Constants.AccountImageType.MANUFACTURINGDIAGRAM.toString()]) as AccountImages
        }
        return accountImage
    }

    def getDefaultBrandingImage(String defaultBrandingImage) {
        AccountImages accountImage
        if (defaultBrandingImage) {
            accountImage = AccountImages.collection.find([_id: DomainObjectUtil.stringToObjectId(defaultBrandingImage), type: Constants.AccountImageType.BRANDINGIMAGE.toString()]) as AccountImages
        }
        return accountImage
    }

    def getAccountImageAsB64Data(String id) {
        AccountImages accountImage
        String b64
        if (id) {
            accountImage = AccountImages.get(DomainObjectUtil.stringToObjectId(id))
            if(accountImage) {
                Base64 encoder = new Base64()
                b64 = encoder.encodeBase64String(accountImage?.image)
            }
        }
        return b64
    }
}
