package com.bionova.optimi.core.service

import com.bionova.optimi.construction.Constants
import com.bionova.optimi.core.Constants as CoreConstants
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.IndicatorQuery
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.User
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.bson.types.ObjectId
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class IndicatorServiceSpec extends Specification implements ServiceUnitTest<IndicatorService>, AutowiredTest {

    QueryService queryService
    UserService userService
    EntityService entityService

    private static final String LCA_PARAMETERS_QUERY_ID = "LCAParametersQuery"
    private static final String LCC_PROJECT_PARAMETERS_QUERY_ID = "LCCProjectParametersQuery"
    private static final String NON_EXISTING_QUERY_ID = "nonExistingQuery"
    private static final String NON_PROJECT_LEVEL_QUERY_ID = "nonProjectLevelQuery"
    private static final String EXPLICIT_NON_PROJECT_LEVEL_QUERY_ID = "explicitNonProjectLevelQuery"
    private static final String NON_INDICATOR_QUERY_ID = "nonIndicatorQuery"

    @Shared IndicatorQuery lcaParametersIndicatorQuery = new IndicatorQuery(queryId: LCA_PARAMETERS_QUERY_ID, projectLevel: true)
    @Shared IndicatorQuery lccProjectParametersIndicatorQuery = new IndicatorQuery(queryId: LCC_PROJECT_PARAMETERS_QUERY_ID, projectLevel: true)
    @Shared IndicatorQuery nonExistingProjectLevelIndicatorQuery = new IndicatorQuery(queryId: NON_EXISTING_QUERY_ID, projectLevel: true)
    @Shared IndicatorQuery nonProjectLevelIndicatorQuery = new IndicatorQuery(queryId: NON_PROJECT_LEVEL_QUERY_ID)
    @Shared IndicatorQuery explicitNonProjectLevelIndicatorQuery = new IndicatorQuery(queryId: EXPLICIT_NON_PROJECT_LEVEL_QUERY_ID, projectLevel: false)

    @Shared Query lcaParametersQuery = new Query(queryId: lcaParametersIndicatorQuery.queryId)
    @Shared Query lccProjectParametersQuery = new Query(queryId: lccProjectParametersIndicatorQuery.queryId)
    @Shared Query nonProjectLevelQuery = new Query(queryId: nonProjectLevelIndicatorQuery.queryId)
    @Shared Query explicitNonProjectLevelQuery = new Query(queryId: explicitNonProjectLevelIndicatorQuery.queryId)
    @Shared Query nonIndicatorQuery = new Query(queryId: NON_INDICATOR_QUERY_ID)

    @Shared Indicator designIndicator = new Indicator(indicatorId: "designIndicator",
            indicatorUse: Constants.IndicatorUse.DESIGN.toString())

    @Shared Indicator operatingIndicator = new Indicator(indicatorId: "operatingIndicator",
            indicatorUse: Constants.IndicatorUse.OPERATING.toString())

    @Shared Indicator compareIndicator = new Indicator(indicatorId: CoreConstants.COMPARE_INDICATORID,
            indicatorUse: Constants.IndicatorUse.DESIGN.toString(),
            visibilityStatus: "materialSpecifier",
            indicatorQueries: [lccProjectParametersIndicatorQuery, lcaParametersIndicatorQuery])


    def setup() {
        queryService = Mock(QueryService)
        service.queryService = queryService
        userService = Mock(UserService)
        service.userService = userService
        entityService = Mock(EntityService)
        service.entityService = entityService
    }

    def cleanup() {
    }


    void "getIndicatorsByEntity"() {

        String entityId = "entity_id"
        Entity entity = Mock()

        GroovySpy(Entity, global: true)
        GroovySpy(Indicator, global: true)

        when:
        List<Indicator> indicators = service.getIndicatorsByEntity(entityId)

        then:
        1 * Entity.get(entityId) >> entity
        1 * entity.indicatorIds >> [designIndicator.indicatorId, operatingIndicator.indicatorId]
        1 * Indicator.withCriteria(_ as Closure) >> [designIndicator, operatingIndicator]

        indicators == [designIndicator, operatingIndicator]
    }

    void "getIndicatorsByEntityObject"() {
        GroovySpy(Indicator, global: true)

        Entity entity = Mock()

        when:
        List<Indicator> indicators = service.getIndicatorsByEntityObject(entity)

        then:
        1 * entity.indicatorIds >> [_]
        1 * Indicator.withCriteria(_ as Closure) >> [designIndicator, operatingIndicator]

        indicators == [designIndicator, operatingIndicator]
    }

    void "getSelectedIndicatorsForCurrentUser"() {
        given:
        GroovySpy(Indicator, global: true)

        Entity entity = Mock()

        List<Indicator> indicators = [designIndicator, compareIndicator, operatingIndicator]

        when:
        List<Indicator> selectedIndicators = service.getSelectedIndicatorsForCurrentUser(entity)

        then:
        1 * entity.indicatorIds >> [_]
        1 * Indicator.withCriteria(_ as Closure) >> indicators
        1 * userService.getCurrentUser() >> new User()
        1 * entityService.isCompareAvailableForCurrentUser(entity, _) >> true
        0 * Indicator.findByIndicatorIdAndActive(CoreConstants.COMPARE_INDICATORID, _, _)

        selectedIndicators == [designIndicator, operatingIndicator, compareIndicator]
    }

    void "getSelectedIndicatorsForCurrentUser when compare is unavailable for user"() {
        given:
        GroovySpy(Indicator, global: true)

        Entity entity = Mock()

        List<Indicator> indicators = [designIndicator, compareIndicator, operatingIndicator]

        when:
        List<Indicator> selectedIndicators = service.getSelectedIndicatorsForCurrentUser(entity)

        then:
        1 * entity.indicatorIds >> [_]
        1 * Indicator.withCriteria(_ as Closure) >> indicators
        1 * userService.getCurrentUser() >> new User()
        1 * entityService.isCompareAvailableForCurrentUser(entity, _) >> false
        0 * Indicator.findByIndicatorIdAndActive(CoreConstants.COMPARE_INDICATORID, _, _)

        selectedIndicators == [designIndicator, operatingIndicator]
    }

    void "getSelectedIndicatorsForCurrentUser without design indicators"() {
        given:
        GroovySpy(Indicator, global: true)

        Entity entity = Mock()

        Indicator operatingIndicator2 = new Indicator()
        operatingIndicator2.indicatorId = "operatingIndicator2"
        operatingIndicator2.indicatorUse = Constants.IndicatorUse.OPERATING.toString()

        List<Indicator> indicators = [operatingIndicator, compareIndicator, operatingIndicator2]

        when:
        List<Indicator> selectedIndicators = service.getSelectedIndicatorsForCurrentUser(entity)

        then:
        1 * entity.indicatorIds >> [_]
        1 * Indicator.withCriteria(_ as Closure) >> indicators
        1 * userService.getCurrentUser() >> new User()
        0 * entityService.isCompareAvailableForCurrentUser(entity, _)
        0 * Indicator.findByIndicatorIdAndActive(CoreConstants.COMPARE_INDICATORID, _, _)

        selectedIndicators == [operatingIndicator, operatingIndicator2]
    }

    void "getSelectedIndicatorsForCurrentUser without compareIndicator for user"() {
        given:
        GroovySpy(Indicator, global: true)

        Entity entity = Mock()

        List<Indicator> indicators = [designIndicator, operatingIndicator]

        when:
        List<Indicator> selectedIndicators = service.getSelectedIndicatorsForCurrentUser(entity)

        then:
        1 * entity.indicatorIds >> [_]
        1 * Indicator.withCriteria(_ as Closure) >> indicators
        1 * userService.getCurrentUser() >> new User()
        1 * entityService.isCompareAvailableForCurrentUser(entity, _) >> true
        1 * Indicator.findByIndicatorIdAndActive(CoreConstants.COMPARE_INDICATORID, _, _) >> compareIndicator

        selectedIndicators == [designIndicator, operatingIndicator, compareIndicator]
    }

    void "getSelectedIndicatorsForCurrentUser skipCompareIndicator"() {
        given:
        GroovySpy(Indicator, global: true)

        Entity entity = Mock()

        List<Indicator> indicators = [designIndicator, compareIndicator, operatingIndicator]

        Boolean skipCompareIndicator = true

        when:
        List<Indicator> selectedIndicators = service.getSelectedIndicatorsForCurrentUser(entity, skipCompareIndicator)

        then:
        1 * entity.indicatorIds >> [_]
        1 * Indicator.withCriteria(_ as Closure) >> indicators
        1 * userService.getCurrentUser() >> new User()
        0 * entityService.isCompareAvailableForCurrentUser(entity, _)
        0 * Indicator.findByIndicatorIdAndActive(CoreConstants.COMPARE_INDICATORID, _, _)

        selectedIndicators == [designIndicator, operatingIndicator]
    }

    void "filterListIndicatorsByPossibleIndicatorUse"() {
        given:
        Indicator designIndicator2 = Mock()
        Indicator indicatorWithReportIndicatorUse = Mock()

        designIndicator2.indicatorId >> "designIndicator2"
        designIndicator2.indicatorUse >> Constants.IndicatorUse.DESIGN.toString()
        indicatorWithReportIndicatorUse.indicatorId >> "indicatorWithReportIndicatorUse"
        indicatorWithReportIndicatorUse.indicatorUse >> "report"

        List<Indicator> indicators = [designIndicator, operatingIndicator, indicatorWithReportIndicatorUse, compareIndicator, designIndicator2]

        when:
        List<Indicator> filteredIndicators = service.filterListIndicatorsByPossibleIndicatorUse(indicators)

        then:
        filteredIndicators == [designIndicator, operatingIndicator, designIndicator2]
    }

    void "getProjectLevelQueryIdsForIndicators"() {
        given:

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()
        Indicator indicator3 = Mock()
        Indicator indicator4 = Mock()

        List<Indicator> indicators = [indicator1, indicator2, indicator3, indicator4]

        when:
        List<String> projectLevelQueryIds = service.getProjectLevelQueryIdsForIndicators(indicators)

        then:
        1 * indicator1.indicatorQueries >> [lccProjectParametersIndicatorQuery, nonProjectLevelIndicatorQuery, nonExistingProjectLevelIndicatorQuery]
        1 * indicator2.indicatorQueries >> [lcaParametersIndicatorQuery, lccProjectParametersIndicatorQuery, nonProjectLevelIndicatorQuery]
        1 * indicator3.indicatorQueries >> [nonProjectLevelIndicatorQuery]
        1 * queryService.getQueriesByQueryIds(_) >> [lccProjectParametersQuery, lcaParametersQuery]
        projectLevelQueryIds == [LCA_PARAMETERS_QUERY_ID, LCC_PROJECT_PARAMETERS_QUERY_ID]
    }

    void "getProjectLevelQueryIdsForIndicators without check on existing"() {
        given:

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()
        Indicator indicator3 = Mock()
        Indicator indicator4 = Mock()

        List<Indicator> indicators = [indicator1, indicator2, indicator3, indicator4]

        when:
        List<String> projectLevelQueryIds = service.getProjectLevelQueryIdsForIndicators(indicators, false)

        then:
        1 * indicator1.indicatorQueries >> [lccProjectParametersIndicatorQuery, nonProjectLevelIndicatorQuery, nonExistingProjectLevelIndicatorQuery]
        1 * indicator2.indicatorQueries >> [lcaParametersIndicatorQuery, lccProjectParametersIndicatorQuery, nonProjectLevelIndicatorQuery]
        1 * indicator3.indicatorQueries >> [nonProjectLevelIndicatorQuery]
        0 * queryService.getQueriesByQueryIds(_)
        projectLevelQueryIds == [LCA_PARAMETERS_QUERY_ID, LCC_PROJECT_PARAMETERS_QUERY_ID, NON_EXISTING_QUERY_ID]
    }

    void "groupIndicatorsByProjectLevelQueryIds"() {
        given:

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()
        Indicator indicator3 = Mock()
        Indicator indicator4 = Mock()

        List<Indicator> indicators = [indicator1, indicator2, indicator3, indicator4]

        when:
        Map<String, List<Indicator>> indicatorsByProjectLevelQueryIds = service.groupIndicatorsByProjectLevelQueryIds(indicators)

        then:
        1 * indicator1.indicatorQueries >> [lccProjectParametersIndicatorQuery, nonProjectLevelIndicatorQuery, nonExistingProjectLevelIndicatorQuery]
        1 * indicator2.indicatorQueries >> [lcaParametersIndicatorQuery, lccProjectParametersIndicatorQuery, nonProjectLevelIndicatorQuery]
        1 * indicator3.indicatorQueries >> [nonProjectLevelIndicatorQuery]
        1 * queryService.getQueriesByQueryIds(_) >> [lccProjectParametersQuery, lcaParametersQuery]
        indicatorsByProjectLevelQueryIds == [(LCA_PARAMETERS_QUERY_ID): [indicator2], (LCC_PROJECT_PARAMETERS_QUERY_ID): [indicator1, indicator2]]
    }

    void "groupIndicatorsByProjectLevelQueryIds for Compare indicator"() {
        given:

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()

        List<Indicator> indicators = [indicator1, indicator2, compareIndicator]

        when:
        Map<String, List<Indicator>> indicatorsByProjectLevelQueryIds = service.groupIndicatorsByProjectLevelQueryIds(indicators, false)

        then:
        1 * indicator1.indicatorId >> "indicator1"
        1 * indicator1.indicatorQueries >> [lcaParametersIndicatorQuery, lccProjectParametersIndicatorQuery]
        1 * indicator2.indicatorId >> "indicator2"
        1 * indicator2.indicatorQueries >> [lcaParametersIndicatorQuery]
        0 * queryService.getQueriesByQueryIds(_)
        indicatorsByProjectLevelQueryIds == [(LCA_PARAMETERS_QUERY_ID): [indicator1, indicator2], (LCC_PROJECT_PARAMETERS_QUERY_ID): [indicator1, compareIndicator]]
    }

    @Unroll
    void "filterQueriesByProjectLevelIndicatorQueries for different query list"() {
        given:
        Indicator indicator = new Indicator()
        indicator.indicatorQueries = [lcaParametersIndicatorQuery, lccProjectParametersIndicatorQuery, nonProjectLevelIndicatorQuery]

        expect:
        service.filterQueriesByProjectLevelIndicatorQueries(indicator, queries, isProjectLevel) == filteredQueries

        where:
        queries | isProjectLevel | filteredQueries
        [lcaParametersQuery, lccProjectParametersQuery, nonProjectLevelQuery, nonIndicatorQuery] | true  | [lcaParametersQuery, lccProjectParametersQuery]
        [lcaParametersQuery, lccProjectParametersQuery, nonProjectLevelQuery, nonIndicatorQuery] | false | [nonProjectLevelQuery]
        [nonIndicatorQuery] | true  | []
        [nonIndicatorQuery] | false | []
        []   | true | []
        null | true | null
    }

    @Unroll
    void "filterQueriesByProjectLevelIndicatorQueries for different indicatorQueries"() {
        given:
        Indicator indicator = new Indicator()
        indicator.indicatorQueries = indicatorQueries

        List<Query> queries = [lcaParametersQuery, lccProjectParametersQuery, nonProjectLevelQuery, explicitNonProjectLevelQuery, nonIndicatorQuery]

        expect:
        service.filterQueriesByProjectLevelIndicatorQueries(indicator, queries, isProjectLevel) == filteredQueries

        where:
        indicatorQueries | isProjectLevel | filteredQueries
        [lcaParametersIndicatorQuery, nonProjectLevelIndicatorQuery] | true  | [lcaParametersQuery]
        [lcaParametersIndicatorQuery, nonProjectLevelIndicatorQuery] | false | [nonProjectLevelQuery]
        [nonProjectLevelIndicatorQuery, explicitNonProjectLevelIndicatorQuery] | true  | []
        [nonProjectLevelIndicatorQuery, explicitNonProjectLevelIndicatorQuery] | false | [nonProjectLevelQuery, explicitNonProjectLevelQuery]
        [lcaParametersIndicatorQuery] | true  | [lcaParametersQuery]
        [lcaParametersIndicatorQuery] | false | []
        []   | true | []
        null | true | []
    }

    @Unroll
    void "filterQueriesByProjectLevelIndicatorQueries for empty or null indicator"() {
        given:

        List<Query> queries = [lcaParametersQuery, lccProjectParametersQuery, nonProjectLevelQuery, nonIndicatorQuery]

        expect:
        service.filterQueriesByProjectLevelIndicatorQueries(indicator, queries, isProjectLevel) == filteredQueries

        where:
        indicator | isProjectLevel | filteredQueries
        new Indicator() | true  | []
        new Indicator() | false | []
        null | true  | []
        null | false | []
    }

    void "filterListIndicatorsForCurrentUser" () {
        given:
        Entity entity = Mock()
        User user = Mock()
        ObjectId userId = new ObjectId()

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()
        Indicator indicator3 = Mock()

        List<Indicator> indicators = [indicator1, indicator2]

        when:
        List<Indicator> filteredIndicators = service.filterListIndicatorsForCurrentUser(indicators, entity)

        then:
        1 * userService.getCurrentUser() >> user
        1 * user.id >> userId
        1 * indicator1.indicatorId >> "indicator1"
        1 * indicator2.indicatorId >> "indicator2"
        0 * indicator3.indicatorId >> "indicator3"
        1 * entity.userIndicators >> [(userId.toString()): ["indicator1", "indicator3"],
                                      (new ObjectId().toString()): ["indicator1", "indicator2"]]

        filteredIndicators == [indicator1]
    }

    void "filterListIndicatorsForCurrentUser without userIndicatorIds for user in the entity" () {
        given:
        Entity entity = Mock()
        User user = Mock()
        ObjectId userId = new ObjectId()

        Indicator indicator1 = Mock()
        Indicator indicator2 = Mock()

        List<Indicator> indicators = [indicator1, indicator2]

        when:
        List<Indicator> filteredIndicators = service.filterListIndicatorsForCurrentUser(indicators, entity)

        then:
        1 * entity.userIndicators >> [(new ObjectId().toString()): ["indicator2"]]
        1 * userService.getCurrentUser() >> user
        1 * user.id >> userId
        0 * indicator1.indicatorId >> "indicator1"
        0 * indicator2.indicatorId >> "indicator2"

        filteredIndicators == [indicator1, indicator2]
    }

    void "filterListIndicatorsForCurrentUser with null parameters" () {
        expect:
        service.filterListIndicatorsForCurrentUser(indicators, entity) == filteredIndicators

        where:
        indicators | entity | filteredIndicators
        null | new Entity() | []
        [new Indicator()] | null | []
    }

    void "isDesignIndicatorAvailableToCompare" () {
        expect:
        service.isDesignIndicatorAvailableToCompare(indicator) == isDesignIndicatorAvailableToCompare

        where:
        indicator          | isDesignIndicatorAvailableToCompare
        designIndicator    | true
        operatingIndicator | false
        compareIndicator   | false
    }
}
