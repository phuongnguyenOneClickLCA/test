package com.bionova.optimi.core.taglib

import com.bionova.optimi.core.domain.mongo.Construction
import com.bionova.optimi.core.domain.mongo.ConstructionGroup
import com.bionova.optimi.core.domain.mongo.ProductDataList

class ProductDataListTagLib {

    static defaultEncodeAs = "raw"
    static namespace = "productDataListRender"

    def missmatchDataWarning = { attrs ->
        ProductDataList productDataList = attrs.productDataList
        String returnable = ""

        if (productDataList && productDataList.missMatchedData) {
            returnable = "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"${message(code: 'query.resource.not_in_filter')}\" id=\"${productDataList.id}notInFilter\"><i class=\"icon-alert\"></i></a>"
        }
        out << returnable
    }

    def inactiveDataWarning = { attrs ->
        ProductDataList productDataList = attrs.productDataList
        String toRender = ""

        if (productDataList && productDataList.inactiveData) {
            toRender = "<a href=\"javascript:\" rel=\"popover\" data-trigger=\"hover\" data-html=\"true\" data-content=\"Inactive resources found\" id=\"${productDataList.id}notInFilter\">${asset.image(src: "img/icon-warning.png", style: "max-width:14px; padding-bottom: 2px;")}</a>"
        }
        out << toRender
    }
}
