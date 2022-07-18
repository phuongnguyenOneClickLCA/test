package com.bionova.optimi.core.service.scope

class LCAScopeService extends AbstractScopeService {

    static final String LCA_ID = "LCA"
    static final String DEFAULT_LCA_QUERY_ID = "designDefaultParameters"
    static final String LCA_SECTION_ID = "lcaScope"

    @Override
    String getApplicationId() {
        return LCA_ID
    }

    @Override
    String getDefaultQueryId() {
        return DEFAULT_LCA_QUERY_ID
    }

    @Override
    String getSectionId() {
        return LCA_SECTION_ID
    }
}
