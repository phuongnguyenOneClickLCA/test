package com.bionova.optimi.core.service.scope

class EPDScopeService extends AbstractScopeService {

    static final String EPD_ID = "LCA_EPD"
    static final String DEFAULT_EPD_QUERY_ID = "scopeEPD"
    static final String EPD_SECTION_ID = "epdScope"

    @Override
    String getApplicationId() {
        return EPD_ID
    }

    @Override
    String getDefaultQueryId() {
        return DEFAULT_EPD_QUERY_ID
    }

    @Override
    String getSectionId() {
        return EPD_SECTION_ID
    }
}