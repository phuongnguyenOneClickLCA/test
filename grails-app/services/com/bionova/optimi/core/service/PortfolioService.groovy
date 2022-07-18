/*
 *
 * Copyright (c) 2013 by Bionova Oy
 */

package com.bionova.optimi.core.service

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.Constants.EntityClass
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.domain.mongo.Indicator
import com.bionova.optimi.core.domain.mongo.License
import com.bionova.optimi.core.domain.mongo.Portfolio
import com.bionova.optimi.core.domain.mongo.Query
import com.bionova.optimi.core.domain.mongo.Question
import com.bionova.optimi.core.domain.mongo.User
import com.bionova.optimi.core.util.DomainObjectUtil
import com.mongodb.BasicDBObject
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.bson.types.ObjectId

/**
 * @author Pasi-Markus Mäkelä
 */
class PortfolioService {

    UserService userService
    EntityService entityService
    IndicatorService indicatorService
    QuestionService questionService
    FlashService flashService
    LicenseService licenseService
    OptimiResourceService optimiResourceService
    SpringSecurityService springSecurityService

    @Secured(["ROLE_AUTHENTICATED"])
    Portfolio getBenchmarkPortfolio(String portfolioId) {
        Portfolio portfolio

        if (portfolioId) {
            portfolio = Portfolio.findByPortfolioIdAndPublishAsBenchmark(portfolioId, Boolean.TRUE)
        }
        return portfolio
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Portfolio> getPortfoliosForEntity(Entity entity) {
        return Portfolio.list()?.findAll({ Portfolio portfolio -> portfolio.entityIds?.contains(entity.id.toString()) || portfolio.entityIds?.contains(entity.id) || portfolio.publishAsBenchmark })
    }

    @Secured(["ROLE_AUTHENTICATED"])
    List<Portfolio> getBenchmarkPortfoliosForEntity(Entity entity) {
        List<Portfolio> benchmarkPortfolios = Portfolio.findAllByPublishAsBenchmark(Boolean.TRUE)
        return benchmarkPortfolios?.findAll({ Portfolio portfolio -> (!portfolio.entityClass || entity.entityClass.equals(portfolio.entityClass)) && !portfolio.entityDeleted })

    }

    Portfolio getPortfolioById(String id) {
        if (id) {
            return Portfolio.get(id)
        }
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Portfolio addEntityToPortfolio(String portfolioId, String entityId) {
        Portfolio portfolio = Portfolio.get(portfolioId)

        if (portfolio && entityId) {
            Entity entity = entityService.getEntityById(entityId)

            if (entity) {
                if (!portfolio.entityIds?.contains(entity.id.toString())) {
                    if (!portfolio.entityIds) {
                        portfolio.entityIds = []
                    }
                    portfolio.entityIds.add(entity.id.toString())
                    portfolio.lastUpdaterId = userService.getCurrentUser()?.id?.toString()
                    portfolio = portfolio.merge(flush: true, failOnError: true)
                }
            }
        }
        return portfolio
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Portfolio removeEntityFromPortfolio(String portfolioId, String entityId) {
        Portfolio portfolio = Portfolio.get(portfolioId)

        if (portfolio) {
            portfolio.entityIds?.remove(entityId)
            portfolio.lastUpdaterId = userService.getCurrentUser()?.id?.toString()
            portfolio = portfolio.merge(flush: true)
        }
        return portfolio
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Portfolio addIndicatorToPortfolio(portfolioId, indicatorId) {
        Portfolio portfolio = Portfolio.get(portfolioId)

        if (portfolio) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (indicator) {
                if (!portfolio.indicatorIds?.contains(indicator.indicatorId)) {
                    if (!portfolio.indicatorIds) {
                        portfolio.indicatorIds = []
                    }
                    portfolio.indicatorIds.add(indicator.indicatorId)
                    portfolio.lastUpdaterId = userService.getCurrentUser()?.id?.toString()
                    portfolio = portfolio.merge(flush: true)
                }
            }
        }
        return portfolio
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Portfolio removeIndicatorFromPortfolio(portfolioId, indicatorId) {
        Portfolio portfolio = Portfolio.get(portfolioId)

        if (portfolio) {
            Indicator indicator = indicatorService.getIndicatorByIndicatorId(indicatorId, true)

            if (indicator && portfolio.indicatorIds?.contains(indicator.indicatorId)) {
                portfolio.indicatorIds.remove(indicator.indicatorId)
                portfolio.lastUpdaterId = userService.getCurrentUser()?.id?.toString()
                portfolio = portfolio.merge(flush: true)
            }
        }
        return portfolio
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Portfolio createPortfolio(Portfolio portfolio) {
        if (portfolio.validate()) {
            User user = userService.getCurrentUser(Boolean.TRUE)

            portfolio.userId = user?.id?.toString()
            portfolio.lastUpdaterId = user?.id?.toString()

            if (portfolio.connectedIndicatorId) {
                portfolio.indicatorIds = [portfolio.connectedIndicatorId]
                portfolio.licenseId = null
                portfolio.entityIds = []
            } else if (portfolio.licenseId) {
                portfolio.entityIds = []
            }
            Entity portfolioEntity = Entity.collection.findOne(["_id": new ObjectId(portfolio.entityId)], ["name": 1]) as Entity

            if (portfolioEntity) {
                portfolio.entityName = portfolioEntity.name
            }
            portfolio = portfolio.save(flush: true)
        }
        return portfolio
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Portfolio updatePortfolio(Portfolio portfolio) {
        if (portfolio.validate()) {
            portfolio.lastUpdaterId = userService.getCurrentUser()?.id?.toString()

            if (portfolio.connectedIndicatorId) {
                portfolio.indicatorIds = [portfolio.connectedIndicatorId]
                portfolio.licenseId = null
                portfolio.entityIds = []
            } else if (portfolio.licenseId) {
                portfolio.entityIds = []
            }
            Entity portfolioEntity = Entity.collection.findOne(["_id": new ObjectId(portfolio.entityId)], ["name": 1]) as Entity

            if (portfolioEntity) {
                portfolio.entityName = portfolioEntity.name
            }
            portfolio = portfolio.merge(flush: true)
        } else {
            log.error("Errors: ${portfolio.errors}")
            flashService.setErrorAlert("Errors: ${portfolio.errors}", true)
        }
        return portfolio
    }

    @Secured(["ROLE_AUTHENTICATED"])
    void removePortfolio(portfolioId) {
        Portfolio portfolio = Portfolio.get(portfolioId)
        portfolio?.delete(flush: true)
    }

    @Secured(["ROLE_AUTHENTICATED"])
    Workbook exportPortolio(String portfolioId, String operatingPeriod) {
        Workbook wb

        if (portfolioId && operatingPeriod) {
            Portfolio portfolio = getPortfolioById(portfolioId)
            List<Indicator> indicators = getIndicators(portfolio?.indicatorIds)

            if (indicators) {
                List<Entity> parentEntities = getEntities(portfolio)
                def operatingIds = []
                def operatingId

                parentEntities?.each { Entity entity ->
                    operatingId = entityService.getChildEntities(entity, EntityClass.OPERATING_PERIOD.getType())?.find({
                        !it.name && it.operatingPeriod.equals(operatingPeriod)
                    })?.id

                    if (operatingId) {
                        operatingIds.add(operatingId)
                    }
                }

                if (operatingIds) {
                    List<Entity> entities = Entity.getAll(DomainObjectUtil.stringsToObjectIds(operatingIds))
                    List<Question> questions = new ArrayList<Question>()
                    def queryAndQuestionIds = [:]

                    if (indicators) {
                        Entity portfolioEntity = getEntity(portfolio?.entityId)

                        indicators.each { Indicator indicator ->
                            indicator.getQueries(portfolioEntity)?.each { Query query ->
                                if (!queryAndQuestionIds.get(query.queryId)) {
                                    def textQuestions = query.allQuestions

                                    if (textQuestions) {
                                        queryAndQuestionIds.put(query.queryId, textQuestions.collect({ Question q -> q.questionId }))
                                    }
                                }
                            }
                        }
                    }

                    if (queryAndQuestionIds) {
                        queryAndQuestionIds.each { String queryId, List qIds ->
                            qIds?.each { String questionId ->
                                Question question = questionService.getQuestion(queryId, questionId)

                                if (question) {
                                    questions.add(question)
                                }
                            }
                        }
                        entities = entities.sort({ it.operatingPeriodAndName })
                        questions = questions?.sort({ it.localizedQuestion })
                        wb = new HSSFWorkbook()
                        CellStyle cs = wb.createCellStyle()
                        Font f = wb.createFont()
                        f.setBold(true)
                        cs.setFont(f)
                        Sheet sheet = wb.createSheet()
                        Row headerRow = sheet.createRow(0)
                        Cell emptyheaderCell = headerRow.createCell(0)
                        emptyheaderCell.setCellStyle(cs)
                        emptyheaderCell.setCellType(CellType.STRING)
                        emptyheaderCell.setCellValue("")
                        int i = 1

                        entities?.each { Entity entity ->
                            Cell headerCell = headerRow.createCell(i)
                            headerCell.setCellStyle(cs)
                            headerCell.setCellType(CellType.STRING)
                            headerCell.setCellValue(entity.getParentById()?.name + " " + entity.operatingPeriodAndName)
                            i++
                        }
                        i = 1

                        questions?.each { Question question ->
                            Row questionRow = sheet.createRow(i)
                            Cell questionCell = questionRow.createCell(0)
                            questionCell.setCellStyle(cs)
                            questionCell.setCellType(CellType.STRING)
                            questionCell.setCellValue(question.localizedQuestion)
                            int j = 1

                            entities?.each { Entity entity ->
                                Cell answerCell = questionRow.createCell(j)
                                answerCell.setCellType(CellType.STRING)
                                Set<Dataset> datasets = entity.datasets?.findAll({
                                    it.questionId.equals(question.questionId)
                                })

                                if (datasets) {
                                    def answer = ""

                                    datasets.each { Dataset dataset ->
                                        if (dataset.resourceId) {
                                            answer = "${dataset.resourceId}:${dataset.quantity ? dataset.quantity : 0}"
                                        } else if (dataset.quantity && !dataset.additionalQuestionAnswers) {
                                            answer = "${dataset.quantity}"
                                        }
                                        def keys = dataset.additionalQuestionAnswers?.keySet()?.toList()

                                        if (keys) {
                                            keys = keys.sort()

                                            for (String key in keys) {
                                                answer = answer ? answer + ", ${key}:${dataset.additionalQuestionAnswers.get(key)}" : "${key}:${dataset.additionalQuestionAnswers.get(key)}"

                                            }
                                        }
                                        answer = answer ? answer + "\n" : ""
                                    }
                                    answerCell.setCellValue(answer)
                                } else {
                                    answerCell.setCellValue("")
                                }
                                j++
                            }
                            i++
                        }
                    }
                }
            }
        }
        return wb
    }

    Entity getEntity(String entityId) {
        return entityId ? Entity.get(entityId) : null
    }

    String getLastUpdaterName(Portfolio portfolio) {
        if (portfolio) {
            if (portfolio.lastUpdaterId) {
                return userService.getUserAsDocumentById(portfolio.lastUpdaterId, new BasicDBObject([username:1]))?.username
            } else if (portfolio.lastUpdater) {
                return portfolio.lastUpdater.username
            } else {
                return null
            }
        } else {
            return null
        }
    }

    License getLicense(String licenseId) {
        License license

        if (licenseId) {
            license = licenseService.getLicenseById(licenseId)
        }
        return license
    }

    List<Entity> getEntities(Portfolio portfolio) {
        List<Entity> entities = []

        if (portfolio) {
            if (portfolio.connectedIndicatorId) {
                entities = getEntitiesByConnectedIndicatorId(portfolio)
            } else {
                if (portfolio.licenseId) {
                    License license = licenseService.getLicenseById(portfolio.licenseId)

                    if (license) {
                        entities = license.licensedEntities
                    }
                } else if (portfolio.entityIds) {
                    entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(portfolio.entityIds)]])?.collect({it as Entity})
                }
            }

            if (entities) {
                entities = entities?.findAll({ !it.deleted })

                if (portfolio.entityId) {
                    entities.remove(getEntity(portfolio.entityId))
                }

                if (portfolio.entityClass) {
                    entities = entities.findAll({ Entity e -> e.entityClass.equals(portfolio.entityClass)})

                    if (portfolio.entityClass.equals(Constants.EntityClass.PORTFOLIO.toString())) {
                        List<Entity> portfolios = entities.findAll(({ Entity e -> e.entityClass.equals(Constants.EntityClass.PORTFOLIO.toString()) }))

                        if (portfolios) {
                            entities.removeAll(portfolios)

                            portfolios?.each { Entity e ->
                                List<Entity> portfolioEntities = getInnerEntitiesForPortfolio(e.portfolio)

                                if (portfolioEntities) {
                                    entities.addAll(portfolioEntities)
                                }
                            }
                        }
                    }
                }
            }
        }
        return entities?.unique({ it.id })?.sort({ Entity e -> e.name })
    }

    List<Entity> getEntitiesWithoutPortfolioEntities(Portfolio portfolio) {
        List<Entity> entities = []

        if (portfolio) {
            if (portfolio.connectedIndicatorId) {
                entities = getEntitiesByConnectedIndicatorId(portfolio)
            } else {
                if (portfolio.licenseId) {
                    License license = License.get(portfolio.licenseId)

                    if (license) {
                        entities = license.licensedEntities
                    }
                } else if (portfolio.entityIds) {
                    entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(portfolio.entityIds)]])?.collect({it as Entity})
                }
            }

            if (entities) {
                if (portfolio.entityId) {
                    entities.remove(getEntity(portfolio.entityId))
                }

                if (portfolio.entityClass) {
                    entities = entities.findAll({ Entity e -> portfolio.entityClass.equals(e.entityClass) })
                }
            }
        }
        return entities?.unique({ it.id })?.sort({ Entity e -> e.name })
    }

    /*
     * This is needed for not calling inner portfolio.entities to avoid stackOverFlow
     */

    private List<Entity> getInnerEntitiesForPortfolio(Portfolio portfolio) {
        List<Entity> entities = []

        if (portfolio) {
            if (portfolio.connectedIndicatorId) {
                entities = getEntitiesByConnectedIndicatorId(portfolio)
            } else {
                if (portfolio.licenseId) {
                    License license = License.get(portfolio.licenseId)

                    if (license) {
                        entities = license.licensedEntities
                    }
                } else if (portfolio.entityIds) {
                    entities = Entity.collection.find(["_id": [$in: DomainObjectUtil.stringsToObjectIds(portfolio.entityIds)]])?.collect({it as Entity})
                }
            }
        }
        return entities
    }

    private List<Entity> getEntitiesByConnectedIndicatorId(Portfolio portfolio) {
        List<Entity> entities

        if (portfolio) {
            if (portfolio.connectedIndicatorId) {
                if (portfolio.showOnlyLatestStatus) {
                    if (portfolio.showOnlyCarbonHeroes) {
                        if (portfolio.showOnlySpecificallyAllowed) {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses && entity.hasCarbonHeroesAndAllowedAndLatestStatus
                            })
                        } else {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses && entity.hasCarbonHeroesAndLatestStatus
                            })
                        }
                    } else {
                        if (portfolio.showOnlySpecificallyAllowed) {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses && entity.hasAllowedChildrenAndLatestStatus
                            })
                        } else {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses && entity.hasLatestStatus
                            })
                        }
                    }
                } else {
                    if (portfolio.showOnlyCarbonHeroes) {
                        if (portfolio.showOnlySpecificallyAllowed) {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses && entity.hasCarbonHeroesAndAllowed
                            })
                        } else {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses && entity.hasCarbonHeroes
                            })
                        }
                    } else {
                        if (portfolio.showOnlySpecificallyAllowed) {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses && entity.hasAllowedChildren
                            })
                        } else {
                            entities = entityService.getEntitiesByIndicator(portfolio.connectedIndicatorId)?.findAll({
                                Entity entity -> entity.hasValidProductionLicenses
                            })
                        }
                    }
                }
            }
        }
        return entities
    }

    List<Indicator> getIndicators(List indicatorIds) {
        List<Indicator> indicators

        if (indicatorIds) {
            indicators = indicatorService.getIndicatorsByIndicatorIds(indicatorIds, true)
        }
        return indicators
    }

    int getEntityCount(List entityIds) {
        int count = 0

        if (entityIds) {
            count = entityIds.size()
        }
        return count
    }

    int getIndicatorCount(List indicatorIds) {
        int count = 0

        if (indicatorIds) {
            count = indicatorIds.size()
        }
        return count
    }

    boolean getManageable(String entityId) {
        if (getEntity(entityId)?.manageable) {
            return true
        }
        return false
    }

    List<Entity> getAllChildEntities(Portfolio portfolio) {
        List<Entity> childEntities = []
        if (portfolio) {
            List<Entity> entities = getEntities(portfolio)

            if(entities) {
                for (Entity e in entities) {
                    if ("design".equals(portfolio.type)) {
                        if (e.childEntities?.find({"design".equals(it.entityClass)})) {
                            childEntities.addAll(e.getDesigns())
                        }
                    } else {
                        if (e.childEntities?.find({"operatingPeriod".equals(it.entityClass)})) {
                            childEntities.addAll(e.getOperatingPeriods())
                        }
                    }
                }
            }
        }
        return childEntities
    }
}
