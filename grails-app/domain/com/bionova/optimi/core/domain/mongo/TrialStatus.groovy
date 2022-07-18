package com.bionova.optimi.core.domain.mongo

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class TrialStatus {
    Date accountCreated
    Date firstProject //condition: if no prior project found in dataset connected to user account
    Date activeTrial //condition: if no prior license found and license type equal trial
    Date projectParameter //condition: if no prior parameter query is found and active trial date exist
    Date firstDesign
    //condition: if no prior childEntity (even deleted) found in linked project and active trial date exist
    Date firstQueryInput
    //condition: if no prior input for query (not project level) found in childEntities and active trial date exist
    Date secondDesign
    // condition: if firstDesign && firstQueryInput && activeTrial exist and prior childEntities count is 1 (not counting deleted designs)
    Date viewResult // condition: if firstQueryInput is filled and the hint to see result is first rendered

    String entityId // project linked with trial activated
    String entityName // project linked with trial activated
    String licenseId // trial license that is activated
    String licenseName // trial license that is activated
    String indicatorName //linked with project and trial license recorded
    Boolean completedTrial = false
    Boolean isPlanetaryUser = false
    String tagStatus //use for activeCampaign
    String tagId //use for activeCampaigrt

    static mapWith = "none"

    /* LOOKUP FOR TAG ID=======
        NAME OF TAG :       ID OF TAG - DESCRIPTION::
    * TRIAL Active – Trial Activated:	194	- User with active trial activated the license
        TRIAL Active – First project:	189	- User with active trial created the first project
        TRIAL Active – First design:	191	- User with active trial created the first design
        TRIAL Active – Parameter query:	190	- User with active trial input the parameter query
        TRIAL Active – First query input:	192	- User with active trial input the first query
        TRIAL Active – Second design:	193	- User with active trial created the second design
        TRIAL Stuck – Trial Activated:	196	- User with active trial stucks at the license
        TRIAL Stuck – First project:	197	- User with active trial stucks at the first project
        TRIAL Stuck – First design: 	199	- User with active trial stucks at the first design
        TRIAL Stuck – Parameter query:	200	- User with active trial stucks at the parameter query
        TRIAL Stuck – First query input:	201	- User with active trial stucks at the first query input
        TRIAL Stuck – Second design:	202	- User with active trial stucks at the second design
        TRIAL Completed:            	203	- User completed the trial
        TRIAL Inactive – First project:	204	- User failed trial at the license
        TRIAL Inactive – First design:	205	- User failed trial at the first design
        TRIAL Inactive – Parameter query:	206	- User failed trial at the parameter query
        TRIAL Inactive – First query input:	207	- User failed trial at the first query input
        TRIAL Inactive – Second designs:	208	- User failed trial at the second design
    * PLANETARY User:
        Planetary user                  303 - Planetary user
    *
    *
    * */

    @Override
    String toString() {
        return "Account created " + accountCreated + ". First project: " + firstProject + ". Active trial: " + activeTrial + ". Project parameter: " + projectParameter + ". First design: " + firstDesign + ". First query input: " + firstQueryInput + ". Second design: " + secondDesign +
                "View result hint: " + viewResult + ". Entity name: " + entityName + ". License name: " + licenseName + ". Indicator name: " + indicatorName + ". Completed trial: " + completedTrial
    }
}
