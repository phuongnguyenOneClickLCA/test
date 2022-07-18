package com.bionova.optimi.frenchTools.re2020

import com.bionova.optimi.core.Constants
import com.bionova.optimi.core.domain.mongo.CalculationResult
import com.bionova.optimi.core.domain.mongo.Dataset
import com.bionova.optimi.core.domain.mongo.Entity
import com.bionova.optimi.core.service.CalculationResultService
import com.bionova.optimi.core.service.DatasetService
import com.bionova.optimi.frenchTools.FrenchConstants
import com.bionova.optimi.frenchTools.FrenchStore
import com.bionova.optimi.frenchTools.helpers.BatimentArea
import com.bionova.optimi.frenchTools.helpers.DatasetGroup
import com.bionova.optimi.frenchTools.helpers.ResultsPerLot
import com.bionova.optimi.frenchTools.helpers.ResultsPerSousLot
import com.bionova.optimi.frenchTools.helpers.ResultsPerZone
import com.bionova.optimi.frenchTools.helpers.SharedProjectVariables
import com.bionova.optimi.frenchTools.helpers.ZoneArea
import com.bionova.optimi.frenchTools.helpers.results.AbstractFrenchResult
import com.bionova.optimi.frenchTools.helpers.results.BeResult
import com.bionova.optimi.frenchTools.helpers.results.EnergyResult
import com.bionova.optimi.frenchTools.helpers.results.IndicateurResults
import com.bionova.optimi.frenchTools.helpers.results.SiteResult
import com.bionova.optimi.frenchTools.helpers.results.WaterResult
import com.bionova.optimi.util.NumberUtil
import com.bionova.optimi.xml.re2020RSEnv.RSEnv
import com.bionova.optimi.xml.re2020RSEnv.RSEnv.SortieProjet.Batiment
import com.bionova.optimi.xml.re2020RSEnv.RSEnv.SortieProjet.Batiment.Zone
import com.bionova.optimi.xml.re2020RSEnv.RSEnv.SortieProjet.Parcelle
import com.bionova.optimi.xml.re2020RSEnv.TCoefModIcconstruction
import com.bionova.optimi.xml.re2020RSEnv.TCoefModIcenergie
import com.bionova.optimi.xml.re2020RSEnv.TIndicateur
import com.bionova.optimi.xml.re2020RSEnv.TIndicateurCo2Dynamique
import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode

import java.math.RoundingMode

@CompileStatic
class SortieProjetUtilRe2020 {
    DatasetService datasetService
    NumberUtil numberUtil
    CalculationResultService calculationResultService

    private static final int RESULT_DECIMAL_PRECISION = 5

    // HUOM! DO NOT RE ARRANGE THE ORDER OF THE CODE INSIDE THIS METHOD
    void setSortieProjetToRSEnv(RSEnv rsEnv, FrenchStore store) {
        RSEnv.SortieProjet sortieProjet = new RSEnv.SortieProjet()

        for (Entity design in store.designs) {
            if (!design) {
                continue
            }
            String designId = design.id.toString()
            boolean selectedToExportParcelle = designId == store.parcelle.designId
            // set batiment if design is selected for exporting parcelle, or the design is selected for exporting parcelle but it also have results for others from mapping.
            boolean setBatiment = !selectedToExportParcelle || (selectedToExportParcelle && !store.parcelle.isStandAlone)
            groupCalculationResults(design, store)

            if (setBatiment) {
                setBatimentToSortieProjet(sortieProjet, store, designId)
            }

            if (selectedToExportParcelle) {
                setParcelleToSortieProjet(sortieProjet, store, designId)
            }

            store.clearResults()
        }

        // if user has not select a design to export parcelle, we export parcelle with 0 as results
        if (!store.parcelle.designId) {
            setParcelleToSortieProjet(sortieProjet, store, null)
        }
        rsEnv.setSortieProjet(sortieProjet)
    }

    private void setBatimentToSortieProjet(RSEnv.SortieProjet sortieProjet, FrenchStore store, String designId) {

        Integer batimentIndex = store.batimentIndexMappings.get(designId)
        Map<String, List<CalculationResult>> resultsPerRule = store.resultsPerRule
        List<CalculationResult> parcelleResults = store.resultsPerDesign.get(store.parcelle.designId).findAll { Constants.GWP_CALC_RULEID == it.calculationRuleId && FrenchConstants.PARCELLE_RESULT_CATID == it.resultCategoryId }
        CalculationResult stockCParcelleResult = store.resultsPerDesign.get(store.parcelle.designId).find { FrenchConstants.STOCKC_KGC_CALC_RULEID == it.calculationRuleId && FrenchConstants.PARCELLE_RESULT_CATID == it.resultCategoryId }

        SharedProjectVariables sharedProjectVariables = new SharedProjectVariables()
        sharedProjectVariables.sref = store.srefPerDesign.sum { it.sref ?: 0D } as Double ?: 1D
        sharedProjectVariables.icParcelle = calculateIcParcelle(parcelleResults, sharedProjectVariables.sref)

        BatimentArea batimentArea = store.srefPerDesign.find { it.designId == designId }
        Double batimentSref = batimentArea?.sref
        Double batimentNocc = batimentArea?.nocc
        Double batimentSrefForIndicateurResults = batimentSref ?: 1D

        Batiment batiment = new Batiment()
        batiment.setIndex(batimentIndex)

        Batiment.Contributeur contributeur = new Batiment.Contributeur()
        Batiment.Contributeur.Composant composant = new Batiment.Contributeur.Composant()
        Batiment.Contributeur.Energie energie = new Batiment.Contributeur.Energie()
        Batiment.Contributeur.Eau eau = new Batiment.Contributeur.Eau()
        Batiment.Contributeur.Chantier chantier = new Batiment.Contributeur.Chantier()
        IndicateurResults irForBatiment = getIndicateurResults(resultsPerRule, batimentSrefForIndicateurResults, IndicateurResults.FOR.ALL)

        for (EnergyResult energyResult in store.energyResults) {
            Batiment.Contributeur.Energie.SousContributeur energieSousContributeur = new Batiment.Contributeur.Energie.SousContributeur(ref: energyResult.energyUseRef?.toString())
            IndicateurResults irForEnergieSc = getIndicateurResults(energyResult.resultsPerRule, batimentSrefForIndicateurResults, IndicateurResults.FOR.ENERGIE)
            energieSousContributeur.setIndicateursAcvCollection(irForEnergieSc.indicateursAcvCollectionForEnergie)
            energieSousContributeur.setIndicateurCo2Dynamique(irForEnergieSc.indicateurCo2DynamiqueForEnergie)
            energie.getSousContributeur().add(energieSousContributeur)
        }

        for (BeResult beResult in store.beResults) {
            Batiment.Contributeur.Energie.SousContributeur beSousContributeur = new Batiment.Contributeur.Energie.SousContributeur(ref: beResult.ref?.toString())
            IndicateurResults irForBeSc = getIndicateurResults(beResult.resultsPerRule, batimentSrefForIndicateurResults, IndicateurResults.FOR.ENERGIE)
            beSousContributeur.setIndicateursAcvCollection(irForBeSc.indicateursAcvCollectionForEnergie)
            beSousContributeur.setIndicateurCo2Dynamique(irForBeSc.indicateurCo2DynamiqueForEnergie)
            energie.getSousContributeur().add(beSousContributeur)
        }

        for (WaterResult waterResult in store.waterResults) {
            Batiment.Contributeur.Eau.SousContributeur waterSousContributeur = new Batiment.Contributeur.Eau.SousContributeur(ref: waterResult.waterTypeRef?.toString())
            IndicateurResults irForEauSc = getIndicateurResults(waterResult.resultsPerRule, batimentSrefForIndicateurResults, IndicateurResults.FOR.EAU)
            waterSousContributeur.setIndicateursAcvCollection(irForEauSc.indicateursAcvCollectionForEau)
            waterSousContributeur.setIndicateurCo2Dynamique(irForEauSc.indicateurCo2DynamiqueForEau)
            eau.getSousContributeur().add(waterSousContributeur)
        }

        for (SiteResult siteResult in store.siteResults) {
            Batiment.Contributeur.Chantier.SousContributeur siteSousContributeur = new Batiment.Contributeur.Chantier.SousContributeur(ref: siteResult.categoryRef?.toString())
            IndicateurResults irForChantierSc = getIndicateurResults(siteResult.resultsPerRule, batimentSrefForIndicateurResults, IndicateurResults.FOR.CHANTIER)
            siteSousContributeur.setIndicateursAcvCollection(irForChantierSc.indicateursAcvCollectionForChantier)
            siteSousContributeur.setIndicateurCo2Dynamique(irForChantierSc.indicateurCo2DynamiqueForChantier)
            chantier.getSousContributeur().add(siteSousContributeur)
        }

        // this need to be executed before we set composant to contributeur
        setZoneLotSousLotToBatiment(batiment, sharedProjectVariables, batimentArea, store, designId)
        setLotSousLotToBatimentComposant(composant, batimentSref, store)

        energie.setIndicateursAcvCollection(irForBatiment.indicateursAcvCollectionForEnergie)
        energie.setIndicateurCo2Dynamique(irForBatiment.indicateurCo2DynamiqueForEnergie)

        eau.setIndicateursAcvCollection(irForBatiment.indicateursAcvCollectionForEau)
        eau.setIndicateurCo2Dynamique(irForBatiment.indicateurCo2DynamiqueForEau)

        chantier.setIndicateursAcvCollection(irForBatiment.indicateursAcvCollectionForChantier)
        chantier.setIndicateurCo2Dynamique(irForBatiment.indicateurCo2DynamiqueForChantier)

        composant.setIndicateursAcvCollection(irForBatiment.indicateursAcvCollectionForComposant)
        composant.setIndicateurCo2Dynamique(irForBatiment.indicateurCo2DynamiqueForComposant)
        composant.setStockC(calculateStockC(resultsPerRule, batimentSref))
        composant.setUdd(calculateUdd(resultsPerRule))

        contributeur.setEau(eau)
        contributeur.setEnergie(energie)
        contributeur.setComposant(composant)
        contributeur.setChantier(chantier)

        batiment.setContributeur(contributeur)
        batiment.setIndicateursAcvCollection(irForBatiment.indicateursAcvCollection)
        batiment.setIndicateurCo2Dynamique(irForBatiment.indicateurCo2Dynamique)
        setIndicateurPerfEnvToBatiment(batiment, resultsPerRule, sharedProjectVariables, batimentSref, batimentNocc, stockCParcelleResult)

        sortieProjet.getBatiment().add(batiment)
    }

    private void setZoneLotSousLotToBatiment(Batiment batiment, SharedProjectVariables sharedProjectVariables, BatimentArea batimentArea, FrenchStore store, String designId) {
        Map<String, Integer> zones = store.batimentZoneIndexMappings.get(designId)

        for (ResultsPerZone resultsPerZone in store.resultsPerZone) {
            String zoneId = resultsPerZone.zoneId
            // parcelle is not included here
            if (zoneId == FrenchConstants.ZONE_PARCELLE) {
                continue
            }
            ZoneArea zoneArea = batimentArea.zoneAreas?.find { it.zoneId == zoneId }
            Double zoneSref = zoneArea?.sref
            Double zoneNocc = zoneArea?.nocc
            Map<String, List<CalculationResult>> zoneResultsPerRule = resultsPerZone.resultsPerRule
            Zone zone = new Zone()
            zone.setIndex(zones.get(zoneId))
            setIndicateurPerfEnvToZone(zone, sharedProjectVariables, zoneSref, zoneNocc, zoneResultsPerRule, store.datasetGroups?.find { it.designId == designId }?.datasetsPerZone?.get(zoneId))

            Zone.Contributeur zoneContributeur = new Zone.Contributeur()
            Zone.Contributeur.Composant zoneComposant = new Zone.Contributeur.Composant()
            Zone.Contributeur.Energie zoneEnergie = new Zone.Contributeur.Energie()
            Zone.Contributeur.Eau zoneEau = new Zone.Contributeur.Eau()
            Zone.Contributeur.Chantier zoneChantier = new Zone.Contributeur.Chantier()

            IndicateurResults irForZone = getIndicateurResults(resultsPerZone.resultsPerRule, zoneSref, IndicateurResults.FOR.ALL)

            for (EnergyResult energyResult in store.energyResults) {
                Map<String, List<CalculationResult>> zoneEnergyResultsPerRule = energyResult.resultsPerZones?.find { it.zoneId == zoneId }?.resultsPerRule
                Zone.Contributeur.Energie.SousContributeur zoneEnergieSc = new Zone.Contributeur.Energie.SousContributeur(ref: energyResult.energyUseRef?.toString())
                IndicateurResults irForEnergieSc = getIndicateurResults(zoneEnergyResultsPerRule, zoneSref, IndicateurResults.FOR.ENERGIE)
                zoneEnergieSc.setIndicateursAcvCollection(irForEnergieSc.indicateursAcvCollectionForEnergie)
                zoneEnergieSc.setIndicateurCo2Dynamique(irForEnergieSc.indicateurCo2DynamiqueForEnergie)
                zoneEnergie.getSousContributeur().add(zoneEnergieSc)
            }

            for (BeResult beResult in store.beResults) {
                Map<String, List<CalculationResult>> zoneBeResultsPerRule = beResult.resultsPerZones?.find { it.zoneId == zoneId }?.resultsPerRule
                Zone.Contributeur.Energie.SousContributeur zoneBeSc = new Zone.Contributeur.Energie.SousContributeur(ref: beResult.ref?.toString())
                IndicateurResults irForBeSc = getIndicateurResults(zoneBeResultsPerRule, zoneSref, IndicateurResults.FOR.ENERGIE)
                zoneBeSc.setIndicateursAcvCollection(irForBeSc.indicateursAcvCollectionForEnergie)
                zoneBeSc.setIndicateurCo2Dynamique(irForBeSc.indicateurCo2DynamiqueForEnergie)
                zoneEnergie.getSousContributeur().add(zoneBeSc)
            }

            for (WaterResult waterResult in store.waterResults) {
                Map<String, List<CalculationResult>> zoneWaterResultsPerRule = waterResult.resultsPerZones?.find { it.zoneId == zoneId }?.resultsPerRule
                Zone.Contributeur.Eau.SousContributeur zoneEauSc = new Zone.Contributeur.Eau.SousContributeur(ref: waterResult.waterTypeRef?.toString())
                IndicateurResults irForEauSc = getIndicateurResults(zoneWaterResultsPerRule, zoneSref, IndicateurResults.FOR.EAU)
                zoneEauSc.setIndicateursAcvCollection(irForEauSc.indicateursAcvCollectionForEau)
                zoneEauSc.setIndicateurCo2Dynamique(irForEauSc.indicateurCo2DynamiqueForEau)
                zoneEau.getSousContributeur().add(zoneEauSc)
            }

            for (SiteResult siteResult in store.siteResults) {
                Map<String, List<CalculationResult>> zoneSiteResultsPerRule = siteResult.resultsPerZones?.find { it.zoneId == zoneId }?.resultsPerRule
                Zone.Contributeur.Chantier.SousContributeur zoneChantierSc = new Zone.Contributeur.Chantier.SousContributeur(ref: siteResult.categoryRef?.toString())
                IndicateurResults irForChantierSc = getIndicateurResults(zoneSiteResultsPerRule, zoneSref, IndicateurResults.FOR.CHANTIER)
                zoneChantierSc.setIndicateursAcvCollection(irForChantierSc.indicateursAcvCollectionForChantier)
                zoneChantierSc.setIndicateurCo2Dynamique(irForChantierSc.indicateurCo2DynamiqueForChantier)
                zoneChantier.getSousContributeur().add(zoneChantierSc)
            }

            for (Integer lotId in FrenchConstants.ALL_LOTS) {
                String lotIdString = lotId.toString()
                // set bunches of 0 if lot doesn't have results
                ResultsPerLot resultsForLot = resultsPerZone.resultsPerLots?.find { it.lotId == lotIdString }
                Map<String, List<CalculationResult>> lotResultsPerRule = resultsForLot?.resultsPerRule
                IndicateurResults irForLot = getIndicateurResults(lotResultsPerRule, zoneSref, IndicateurResults.FOR.COMPOSANT)

                Zone.Contributeur.Composant.Lot lotForZoneComposant = new Zone.Contributeur.Composant.Lot()
                lotForZoneComposant.setRef(lotId)
                lotForZoneComposant.setIndicateurCo2Dynamique(irForLot.indicateurCo2DynamiqueForComposant)
                lotForZoneComposant.setIndicateursAcvCollection(irForLot.indicateursAcvCollectionForComposant)
                lotForZoneComposant.setStockC(calculateStockC(lotResultsPerRule, zoneSref))
                lotForZoneComposant.setIcDed(calculateIcDed(lotResultsPerRule))
                lotForZoneComposant.setIc(calculateIc(lotResultsPerRule))
                lotForZoneComposant.setUdd(calculateUdd(lotResultsPerRule))

                if (resultsForLot?.resultsPerSousLots) {
                    for (ResultsPerSousLot resultsPerSousLot in resultsForLot.resultsPerSousLots) {
                        Map<String, List<CalculationResult>> souslotResultsPerRule = resultsPerSousLot.resultsPerRule
                        String sousLotId = resultsPerSousLot.sousLotId
                        IndicateurResults irForSousLot = getIndicateurResults(souslotResultsPerRule, zoneSref, IndicateurResults.FOR.COMPOSANT)

                        Zone.Contributeur.Composant.Lot.SousLot sousLotForZoneComposant = new Zone.Contributeur.Composant.Lot.SousLot()
                        sousLotForZoneComposant.setRef(sousLotId)
                        sousLotForZoneComposant.setIndicateurCo2Dynamique(irForSousLot.indicateurCo2DynamiqueForComposant)
                        sousLotForZoneComposant.setIndicateursAcvCollection(irForSousLot.indicateursAcvCollectionForComposant)
                        sousLotForZoneComposant.setStockC(calculateStockC(souslotResultsPerRule, zoneSref))
                        sousLotForZoneComposant.setIcDed(calculateIcDed(souslotResultsPerRule))
                        sousLotForZoneComposant.setIc(calculateIc(souslotResultsPerRule))
                        sousLotForZoneComposant.setUdd(calculateUdd(souslotResultsPerRule))
                        lotForZoneComposant.getSousLot().add(sousLotForZoneComposant)
                    }
                }

                zoneComposant.getLot().add(lotForZoneComposant)
            }

            zoneEnergie.setIndicateursAcvCollection(irForZone.indicateursAcvCollectionForEnergie)
            zoneEnergie.setIndicateurCo2Dynamique(irForZone.indicateurCo2DynamiqueForEnergie)

            zoneEau.setIndicateursAcvCollection(irForZone.indicateursAcvCollectionForEau)
            zoneEau.setIndicateurCo2Dynamique(irForZone.indicateurCo2DynamiqueForEau)

            zoneChantier.setIndicateursAcvCollection(irForZone.indicateursAcvCollectionForChantier)
            zoneChantier.setIndicateurCo2Dynamique(irForZone.indicateurCo2DynamiqueForChantier)

            zoneComposant.setIndicateursAcvCollection(irForZone.indicateursAcvCollectionForComposant)
            zoneComposant.setIndicateurCo2Dynamique(irForZone.indicateurCo2DynamiqueForComposant)
            zoneComposant.setStockC(calculateStockC(resultsPerZone.resultsPerRule, zoneSref))
            zoneComposant.setUdd(calculateUdd(resultsPerZone.resultsPerRule))

            zoneContributeur.setComposant(zoneComposant)
            zoneContributeur.setEau(zoneEau)
            zoneContributeur.setEnergie(zoneEnergie)
            zoneContributeur.setChantier(zoneChantier)

            zone.setIndicateursAcvCollection(irForZone.indicateursAcvCollection)
            zone.setIndicateurCo2Dynamique(irForZone.indicateurCo2Dynamique)
            zone.setContributeur(zoneContributeur)

            batiment.getZone().add(zone)
        }
    }

    private void setLotSousLotToBatimentComposant(Batiment.Contributeur.Composant composant, Double batimentSref, FrenchStore store) {
        if (composant == null) {
            return
        }

        Double batimentSrefForIndicateurResults = batimentSref ?: 1D

        for (Integer lotId in FrenchConstants.ALL_LOTS) {
            ResultsPerLot resultsPerLot = store.resultsPerLot.find { it.lotId == lotId.toString() }
            // set bunches of 0 if lot doesn't have results
            Map<String, List<CalculationResult>> lotResultsPerRule = resultsPerLot?.resultsPerRule
            IndicateurResults irForLot = getIndicateurResults(lotResultsPerRule, batimentSrefForIndicateurResults, IndicateurResults.FOR.COMPOSANT)

            Batiment.Contributeur.Composant.Lot lotForComposant = new Batiment.Contributeur.Composant.Lot()
            lotForComposant.setRef(lotId)
            lotForComposant.setIndicateurCo2Dynamique(irForLot.indicateurCo2DynamiqueForComposant)
            lotForComposant.setIndicateursAcvCollection(irForLot.indicateursAcvCollectionForComposant)
            lotForComposant.setStockC(calculateStockC(lotResultsPerRule, batimentSref))
            lotForComposant.setIc(calculateIc(lotResultsPerRule))
            lotForComposant.setIcDed(calculateIcDed(lotResultsPerRule))
            lotForComposant.setUdd(calculateUdd(lotResultsPerRule))

            if (resultsPerLot?.resultsPerSousLots) {
                for (ResultsPerSousLot resultsPerSousLot in resultsPerLot.resultsPerSousLots) {
                    Map<String, List<CalculationResult>> souslotResultsPerRule = resultsPerSousLot.resultsPerRule
                    String sousLotId = resultsPerSousLot.sousLotId
                    IndicateurResults irForSousLot = getIndicateurResults(souslotResultsPerRule, batimentSrefForIndicateurResults, IndicateurResults.FOR.COMPOSANT)

                    Batiment.Contributeur.Composant.Lot.SousLot sousLot = new Batiment.Contributeur.Composant.Lot.SousLot()
                    sousLot.setRef(sousLotId)
                    sousLot.setIndicateurCo2Dynamique(irForSousLot.indicateurCo2DynamiqueForComposant)
                    sousLot.setIndicateursAcvCollection(irForSousLot.indicateursAcvCollectionForComposant)
                    sousLot.setStockC(calculateStockC(souslotResultsPerRule, batimentSref))
                    sousLot.setIcDed(calculateIcDed(souslotResultsPerRule))
                    sousLot.setIc(calculateIc(souslotResultsPerRule))
                    sousLot.setUdd(calculateUdd(souslotResultsPerRule))
                    lotForComposant.getSousLot().add(sousLot)
                }
            }

            composant.getLot().add(lotForComposant)
        }
    }


    private void setParcelleToSortieProjet(RSEnv.SortieProjet sortieProjet, FrenchStore store, String designId) {
        Double projectSref = store.srefPerDesign.sum { it.sref ?: 0D } as Double ?: 1D
        Map<String, List<CalculationResult>> parcelleResultsPerRule = store.resultsPerZone.find { it.zoneId == FrenchConstants.ZONE_PARCELLE }?.resultsPerRule
        Parcelle parcelle = new Parcelle()

        Parcelle.Contributeur contributeur = new Parcelle.Contributeur()
        Parcelle.Contributeur.Composant composant = new Parcelle.Contributeur.Composant()
        Parcelle.Contributeur.Eau eau = new Parcelle.Contributeur.Eau()
        Parcelle.Contributeur.Chantier chantier = new Parcelle.Contributeur.Chantier()

        Double parcelleSref = 1D // division by sref is not needed for parcelle -> indicateurs_acv_collection
        IndicateurResults irForParcelle = getIndicateurResults(parcelleResultsPerRule, parcelleSref, IndicateurResults.FOR.PARCELLE)

        eau.setIndicateursAcvCollection(irForParcelle.indicateursAcvCollectionForEau)
        eau.setIndicateurCo2Dynamique(irForParcelle.indicateurCo2DynamiqueForEau)

        chantier.setIndicateursAcvCollection(irForParcelle.indicateursAcvCollectionForChantier)
        chantier.setIndicateurCo2Dynamique(irForParcelle.indicateurCo2DynamiqueForChantier)

        composant.setIndicateursAcvCollection(irForParcelle.indicateursAcvCollectionForComposant)
        composant.setIndicateurCo2Dynamique(irForParcelle.indicateurCo2DynamiqueForComposant)
        composant.setIc(calculateIc(parcelleResultsPerRule, projectSref))
        composant.setIcDed(calculateIcDed(parcelleResultsPerRule, projectSref))
        composant.setStockC(calculateStockC(parcelleResultsPerRule, projectSref))
        composant.setUdd(calculateUdd(parcelleResultsPerRule))

        contributeur.setEau(eau)
        contributeur.setChantier(chantier)
        contributeur.setComposant(composant)

        parcelle.setContributeur(contributeur)
        parcelle.setIndicateurCo2Dynamique(irForParcelle.indicateurCo2Dynamique)
        parcelle.setIndicateursAcvCollection(irForParcelle.indicateursAcvCollection)
        sortieProjet.setParcelle(parcelle)
    }

    /**
     * Creates an object that holds all the indicateur CO2 dynamiques and Indicateur Acv Collections for batiment / zone / a specific sous contributeur (energie or eau or chantier or composant)
     * @param resultsPerRule
     * @param sref
     * @param purpose
     * @return
     */
    private IndicateurResults getIndicateurResults(Map<String, List<CalculationResult>> resultsPerRule, Double sref, IndicateurResults.FOR purpose) {
        IndicateurResults indicateurResults = new IndicateurResults(purpose)
        setIndicateurCo2Dynamiques(resultsPerRule, indicateurResults, purpose)
        setIndicateursAcvCollections(resultsPerRule, sref, indicateurResults, purpose)

        return indicateurResults
    }

    /**
     * Add {@link TIndicateurCo2Dynamique.ValeurPhaseAcv} to the {@link TIndicateurCo2Dynamique} in the valeurPhaseAcv collection
     * For gwp results we must set a list of {@link TIndicateurCo2Dynamique} which is a list of results for every stages
     * Each {@link TIndicateurCo2Dynamique.ValeurPhaseAcv} is result for each stage.
     * If some stage is missing results, fill in with 0. This is done in {@link #fillResultForMissingCategories} in {@link #addAcvToIndicateurCo2Dynamique}
     * If gwp results are not available, then we fill in 0 for all stages. (check logic)
     * @param resultsPerRule
     * @param sref
     * @param indicateurResults
     * @param purpose
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    private void setIndicateurCo2Dynamiques(Map<String, List<CalculationResult>> resultsPerRule, IndicateurResults indicateurResults, IndicateurResults.FOR purpose) {
        if (!indicateurResults) {
            return
        }

        boolean setAll = purpose == IndicateurResults.FOR.ALL
        boolean setParcelle = purpose == IndicateurResults.FOR.PARCELLE
        boolean setNonContributeur = setAll || setParcelle
        boolean setComposant = setAll || setParcelle || purpose == IndicateurResults.FOR.COMPOSANT
        boolean setEau = setAll || setParcelle || purpose == IndicateurResults.FOR.EAU
        boolean setChantier = setAll || setParcelle || purpose == IndicateurResults.FOR.CHANTIER
        boolean setEnergie = setAll || purpose == IndicateurResults.FOR.ENERGIE

        Map<String, BigDecimal> nonContributeur = setNonContributeur ? new HashMap<String, BigDecimal>() : null
        Map<String, BigDecimal> composant = setComposant ? new HashMap<String, BigDecimal>() : null
        Map<String, BigDecimal> energie = setEnergie ? new HashMap<String, BigDecimal>() : null
        Map<String, BigDecimal> eau = setEau ? new HashMap<String, BigDecimal>() : null
        Map<String, BigDecimal> chantier = setChantier ? new HashMap<String, BigDecimal>() : null

        List<CalculationResult> gwpResults = resultsPerRule?.get(Constants.GWP_CALC_RULEID)
        if (gwpResults) {
            for (CalculationResult gwpResult in gwpResults) {
                if (FrenchConstants.RESULT_CAT_REF_RE2020_A1_BE.containsKey(gwpResult.resultCategoryId)) {
                    String refCat = FrenchConstants.RESULT_CAT_REF_RE2020_A1_BE.get(gwpResult.resultCategoryId)
                    // method can run in multiple cases, that either everything is set or just setEau || setEnergie || setChantier

                    if (setNonContributeur) {
                        addResultToMap(nonContributeur, refCat, gwpResult.result)
                    }

                    if (setEau && FrenchConstants.B7_RE2020_RESULT_CATID == gwpResult.resultCategoryId) {
                        addResultToMap(eau, refCat, gwpResult.result)
                    } else if (setEnergie && gwpResult.resultCategoryId in FrenchConstants.ENERGIE_RESULT_CATEGORIES_RE2020) {
                        addResultToMap(energie, refCat, gwpResult.result)
                    } else if (setChantier && gwpResult.resultCategoryId in FrenchConstants.CHANTIER_RESULT_CATEGORIES_RE2020) {
                        addResultToMap(chantier, refCat, gwpResult.result)
                    } else if (setComposant && gwpResult.resultCategoryId in FrenchConstants.COMPOSANT_RESULT_CATEGORIES_RE2020) {
                        addResultToMap(composant, refCat, gwpResult.result)
                    }
                }
            }
        }

        if (setNonContributeur) {
            addAcvToIndicateurCo2Dynamique(nonContributeur, indicateurResults.indicateurCo2Dynamique)
        }

        if (setEau) {
            addAcvToIndicateurCo2Dynamique(eau, indicateurResults.indicateurCo2DynamiqueForEau)
        }

        if (setEnergie) {
            addAcvToIndicateurCo2Dynamique(energie, indicateurResults.indicateurCo2DynamiqueForEnergie)
        }

        if (setChantier) {
            addAcvToIndicateurCo2Dynamique(chantier, indicateurResults.indicateurCo2DynamiqueForChantier)
        }

        if (setComposant) {
            addAcvToIndicateurCo2Dynamique(composant, indicateurResults.indicateurCo2DynamiqueForComposant)
        }
    }

    /**
     * Add {@link TIndicateur.Indicateur} to the {@link TIndicateur} in the valeurPhaseAcv collection
     * For every calculation rule in the map {@link FrenchConstants#CALC_RULEID_REF_RE2020} we must set a list of {@link TIndicateur.Indicateur}
     * which is a list of results for every stages
     * Each {@link TIndicateur.Indicateur} is result for each stage.
     * If some stage is missing results, fill in with 0. This is done in {@link #fillResultForMissingCategories} in {@link #addIndicateurWithAcvColection}
     * If some calculation rule doesn't have results, then we fill in results for all stages as 0. (check logic)
     * @param resultsPerRule
     * @param sref
     * @param indicateurResults
     * @param purpose
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    private void setIndicateursAcvCollections(Map<String, List<CalculationResult>> resultsPerRule, Double sref, IndicateurResults indicateurResults, IndicateurResults.FOR purpose) {
        if (!indicateurResults) {
            return
        }

        boolean setAll = purpose == IndicateurResults.FOR.ALL
        boolean setParcelle = purpose == IndicateurResults.FOR.PARCELLE
        boolean setNonContributeur = setAll || setParcelle
        boolean setComposant = setAll || setParcelle || purpose == IndicateurResults.FOR.COMPOSANT
        boolean setEau = setAll || setParcelle || purpose == IndicateurResults.FOR.EAU
        boolean setChantier = setAll || setParcelle || purpose == IndicateurResults.FOR.CHANTIER
        boolean setEnergie = setAll || purpose == IndicateurResults.FOR.ENERGIE

        FrenchConstants.CALC_RULEID_REF_RE2020.each { String calculationRuleId, Integer ref ->
            List<CalculationResult> resultsSameRule = resultsPerRule?.get(calculationRuleId)
            TIndicateur.Indicateur indicateur = setNonContributeur ? new TIndicateur.Indicateur(ref: ref) : null
            TIndicateur.Indicateur indicateurForComposant = setComposant ? new TIndicateur.Indicateur(ref: ref) : null
            TIndicateur.Indicateur indicateurForEnergie = setEnergie ? new TIndicateur.Indicateur(ref: ref) : null
            TIndicateur.Indicateur indicateurForEau = setEau ? new TIndicateur.Indicateur(ref: ref) : null
            TIndicateur.Indicateur indicateurForChantier = setChantier ? new TIndicateur.Indicateur(ref: ref) : null

            Map<String, BigDecimal> nonContributeur = setNonContributeur ? new HashMap<String, BigDecimal>() : null
            Map<String, BigDecimal> composant = setComposant ? new HashMap<String, BigDecimal>() : null
            Map<String, BigDecimal> energie = setEnergie ? new HashMap<String, BigDecimal>() : null
            Map<String, BigDecimal> eau = setEau ? new HashMap<String, BigDecimal>() : null
            Map<String, BigDecimal> chantier = setChantier ? new HashMap<String, BigDecimal>() : null

            if (resultsSameRule) {
                for (CalculationResult calculationResult in resultsSameRule) {
                    // combination of calc rule with ref, and results cat stages A1 - BE
                    if (FrenchConstants.RESULT_CAT_REF_RE2020_A1_BE.containsKey(calculationResult.resultCategoryId)) {
                        String refCat = FrenchConstants.RESULT_CAT_REF_RE2020_A1_BE.get(calculationResult.resultCategoryId)
                        // method can run in multiple cases, that either everything is set, or just setEau || setEnergie || setChantier
                        if (setNonContributeur) {
                            addResultToMap(nonContributeur, refCat, calculationResult.result)
                        }

                        if (!sref) {
                            continue
                        }

                        if (setEau && calculationResult.resultCategoryId == FrenchConstants.B7_RE2020_RESULT_CATID) {
                            addResultToMap(eau, refCat, calculationResult.result / sref)
                        } else if (setEnergie && calculationResult.resultCategoryId in FrenchConstants.ENERGIE_RESULT_CATEGORIES_RE2020) {
                            addResultToMap(energie, refCat, calculationResult.result / sref)
                        } else if (setChantier && calculationResult.resultCategoryId in FrenchConstants.CHANTIER_RESULT_CATEGORIES_RE2020) {
                            addResultToMap(chantier, refCat, calculationResult.result / sref)
                        } else if (setComposant && calculationResult.resultCategoryId in FrenchConstants.COMPOSANT_RESULT_CATEGORIES_RE2020) {
                            addResultToMap(composant, refCat, calculationResult.result / sref)
                        }
                    }
                }
            }

            if (setNonContributeur) {
                addIndicateurWithAcvColection(nonContributeur, indicateur, indicateurResults.indicateursAcvCollection)
            }

            if (setComposant) {
                addIndicateurWithAcvColection(composant, indicateurForComposant, indicateurResults.indicateursAcvCollectionForComposant)
            }

            if (setEau) {
                addIndicateurWithAcvColection(eau, indicateurForEau, indicateurResults.indicateursAcvCollectionForEau)
            }

            if (setChantier) {
                addIndicateurWithAcvColection(chantier, indicateurForChantier, indicateurResults.indicateursAcvCollectionForChantier)
            }

            if (setEnergie) {
                addIndicateurWithAcvColection(energie, indicateurForEnergie, indicateurResults.indicateursAcvCollectionForEnergie)
            }
        }
    }

    private static void addResultToMap(Map<String, BigDecimal> resultMap, String refCat, Double result) {
        if (resultMap != null && refCat && result != null) {
            if (resultMap.containsKey(refCat)) {
                resultMap.put(refCat, resultMap.get(refCat) + BigDecimal.valueOf(result))
            } else {
                resultMap.put(refCat, BigDecimal.valueOf(result))
            }
        }
    }

    // if result map is missing results for some categories, we add to it value 0
    private static void fillResultForMissingCategories(Map<String, BigDecimal> resultMap) {
        if (resultMap != null) {
            for (String cat in FrenchConstants.RESULT_CATEGORIES_FOR_RESULTS) {
                if (!resultMap.containsKey(cat)) {
                    resultMap.put(cat, BigDecimal.ZERO)
                }
            }
        }
    }

    private void addIndicateurWithAcvColection(Map<String, BigDecimal> resultMap, TIndicateur.Indicateur indicateur, TIndicateur indicateurParent) {
        fillResultForMissingCategories(resultMap)
        generateAcvForIndicateur(resultMap, indicateur)
        if (indicateur?.valeurPhaseAcv) {
            indicateurParent?.getIndicateur()?.add(indicateur)
        }
    }

    private void generateAcvForIndicateur(Map<String, BigDecimal> resultMap, TIndicateur.Indicateur indicateur) {
        if (indicateur && resultMap != null) {
            resultMap.each { String refCat, BigDecimal sum ->
                indicateur.getValeurPhaseAcv().add(new TIndicateur.Indicateur.ValeurPhaseAcv(ref: refCat, value: roundBd(sum).floatValue()))
            }

        }
    }

    private void addAcvToIndicateurCo2Dynamique(Map<String, BigDecimal> resultMap, TIndicateurCo2Dynamique indicateur) {
        if (indicateur && resultMap != null) {
            fillResultForMissingCategories(resultMap)
            resultMap.each { String refCat, BigDecimal sum ->
                indicateur.getValeurPhaseAcv().add(new TIndicateurCo2Dynamique.ValeurPhaseAcv(ref: refCat, value: roundBd(sum).floatValue()))
            }

        }
    }

    /**
     * Set 0 if resultsPerRule is null
     * @param resultsPerRule
     * @param indicateursAcvCollection1
     * @param indicateursAcvCollection2
     * @param sref1
     * @param sref2
     */
    @CompileStatic(TypeCheckingMode.SKIP)
    private void setMultipleAcvCollection(Map<String, List<CalculationResult>> resultsPerRule,
                                          TIndicateur indicateursAcvCollection1, TIndicateur indicateursAcvCollection2,
                                          Double sref1, Double sref2) {
        FrenchConstants.CALC_RULEID_REF_RE2020.each { String calculationRuleId, Integer ref ->
            TIndicateur.Indicateur indicateur1 = new TIndicateur.Indicateur(ref: ref)
            TIndicateur.Indicateur indicateur2 = new TIndicateur.Indicateur(ref: ref)
            Map<String, BigDecimal> results1 = new HashMap<String, BigDecimal>()
            Map<String, BigDecimal> results2 = new HashMap<String, BigDecimal>()
            List<CalculationResult> resultsSameRule = resultsPerRule?.get(calculationRuleId)

            if (resultsSameRule) {
                for (CalculationResult calculationResult in resultsSameRule) {
                    // combination of calc rule with ref, and results cat stages A1 - BE
                    if (FrenchConstants.RESULT_CAT_REF_RE2020_A1_BE.containsKey(calculationResult.resultCategoryId)) {
                        String refCat = FrenchConstants.RESULT_CAT_REF_RE2020_A1_BE.get(calculationResult.resultCategoryId)
                        if (sref1) {
                            addResultToMap(results1, refCat, calculationResult.result / sref1)
                        }
                        if (sref2) {
                            addResultToMap(results2, refCat, calculationResult.result / sref2)
                        }
                    }
                }
            }

            addIndicateurWithAcvColection(results1, indicateur1, indicateursAcvCollection1)
            addIndicateurWithAcvColection(results2, indicateur2, indicateursAcvCollection2)
        }
    }

    private static BigDecimal calculateStockC(Map<String, List<CalculationResult>> resultsPerRule, Double sref = null) {
        calculateResultForRuleAndCategories(resultsPerRule, FrenchConstants.STOCKC_KGC_CALC_RULEID, FrenchConstants.COMPOSANT_RESULT_CATEGORIES_RE2020, sref)
    }

    private static BigDecimal calculateUdd(Map<String, List<CalculationResult>> resultsPerRule) {
        calculateResultForRuleAndCategory(resultsPerRule, FrenchConstants.RATIO_GWP_CALC_RULEID, FrenchConstants.UDD_RESULT_CATID)
    }

    private static BigDecimal calculateIc(Map<String, List<CalculationResult>> resultsPerRule, Double sref = null) {
        calculateResultForRuleAndCategory(resultsPerRule, Constants.GWP_CALC_RULEID, FrenchConstants.COMPOSANTS_RESULT_CATID, sref)
    }

    private static BigDecimal calculateIcDed(Map<String, List<CalculationResult>> resultsPerRule, Double sref = null) {
        calculateResultForRuleAndCategory(resultsPerRule, Constants.GWP_CALC_RULEID, FrenchConstants.DED_TOTAL_RESULT_CATID, sref)
    }

    private static BigDecimal calculateIcParcelle(List<CalculationResult> parcelleResults, Double sref = null) {
        calculateResultForRuleAndCategory(null, Constants.GWP_CALC_RULEID, FrenchConstants.PARCELLE_RESULT_CATID, sref, parcelleResults)
    }

    private static BigDecimal calculateResultForRuleAndCategory(Map<String, List<CalculationResult>> resultsPerRule, String calculationRuleId, String resultCategoryId, Double sref = null, List<CalculationResult> parcelleResults = null) {

        List<CalculationResult> results
        if (parcelleResults) {
            results = parcelleResults
        } else {
            results = resultsPerRule?.get(calculationRuleId)?.findAll { resultCategoryId == it.resultCategoryId }
        }
        BigDecimal result = sumCalculationResultsAndDivideBySrefIfNeeded(results, sref)

        return result
    }

    private static BigDecimal calculateResultForRuleAndCategories(Map<String, List<CalculationResult>> resultsPerRule, String calculationRuleId, Set<String> resultCategoryIdSet, Double sref = null) {

        List<CalculationResult> results = resultsPerRule?.get(calculationRuleId)?.findAll { it.resultCategoryId in resultCategoryIdSet }
        BigDecimal result = sumCalculationResultsAndDivideBySrefIfNeeded(results, sref)

        return result
    }

    private static BigDecimal sumCalculationResultsAndDivideBySrefIfNeeded(List<CalculationResult> results, Double sref = null) {

        Double result = results?.sum { it.result } as Double ?: 0D

        if (sref != null) {
            result = convertKgCIntoKgCDivM2(result, sref)
        }

        return roundBd(BigDecimal.valueOf(result))
    }

    private static BigDecimal roundBd(BigDecimal bd) {
        return bd?.setScale(RESULT_DECIMAL_PRECISION, RoundingMode.CEILING)
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private void setIndicateurPerfEnvToZone(Zone zone, SharedProjectVariables sharedProjectVariables, Double zoneSref, Double zoneNocc, Map<String, List<CalculationResult>> zoneResultsPerRule, Set<Dataset> zoneDatasets) {
        zone.setIndicateurPerfEnv(new Zone.IndicateurPerfEnv(createIndicateurPerfEnvMap(zoneResultsPerRule, sharedProjectVariables, zoneSref, zoneNocc, true, zoneDatasets)))
    }

    @CompileStatic(TypeCheckingMode.SKIP)
    private void setIndicateurPerfEnvToBatiment(Batiment batiment, Map<String, List<CalculationResult>> resultsPerRule, SharedProjectVariables sharedProjectVariables, Double batimentSref, Double batimentNocc, CalculationResult stockCParcelleResult) {
        batiment.setIndicateurPerfEnv(new Batiment.IndicateurPerfEnv(createIndicateurPerfEnvMap(resultsPerRule, sharedProjectVariables, batimentSref, batimentNocc, false, null, stockCParcelleResult)))
    }

    /**
     * Remember to add calculationRuleId to map {@link FrenchConstants#INDICATEUR_PERF_ENV_CALC_RULE_IDS} if there's a new switch condition for calculationRuleId
     * @param resultsPerRule
     * @param forZone
     */
    private Map createIndicateurPerfEnvMap(Map<String, List<CalculationResult>> resultsPerRule, SharedProjectVariables sharedProjectVariables, Double sref, Double nocc, Boolean forZone = false, Set<Dataset> zoneDatasets = null, CalculationResult stockCParcelleResult = null) {
        // set 0 value in case we don't have results for it
        Map indicateurPerfEnvMap = FrenchConstants.IndicateurPerfEnv.createEmptyPerfEnvMap(forZone)

        if (forZone && zoneDatasets) {
            Dataset srefDatasetForZone = zoneDatasets?.find {
                it.queryId == FrenchConstants.PROJECT_DESCRIPTION_FEC_QUERYID &&
                        it.sectionId == FrenchConstants.DESCRIPTION_PER_ZONE_RE2020_SECTIONID &&
                        it.questionId == FrenchConstants.SREF_QUESTIONID
            }
            initCoefModIcconstructionToIndicateurPerfEnvMap(indicateurPerfEnvMap, srefDatasetForZone)
            initCoefModIcenergieToIndicateurPerfEnvMap(indicateurPerfEnvMap, srefDatasetForZone)
        }

        if (!resultsPerRule) {
            return indicateurPerfEnvMap
        }

        indicateurPerfEnvMap = setIcParcelleToIndicateurPerfEnvMap(indicateurPerfEnvMap, sharedProjectVariables.icParcelle, sref, nocc)

        for (String calcRuleId in FrenchConstants.INDICATEUR_PERF_ENV_CALC_RULE_IDS) {
            List<CalculationResult> resultsSameRule = resultsPerRule.get(calcRuleId)
            if (!resultsSameRule) {
                continue
            }
            for (CalculationResult calculationResult in resultsSameRule) {
                switch (calculationResult.calculationRuleId) {
                    case FrenchConstants.GWP_M2_SREF_CALC_RULEID:
                        switch (calculationResult.resultCategoryId) {
                            case FrenchConstants.CONSTRUCTION_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_CONSTRUCTION.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                            case FrenchConstants.ENERGIE_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_ENERGIE.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                            case FrenchConstants.EAU_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_EAU.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                            case FrenchConstants.BATIMENT_RESULT_CATID:
                                BigDecimal bdValue = numberUtil.resultToBigDecimal(calculationResult.result)
                                String attr = forZone ? FrenchConstants.IndicateurPerfEnv.IC_ZONE.attribute : FrenchConstants.IndicateurPerfEnv.IC_BATIMENT.attribute
                                indicateurPerfEnvMap.put(attr, bdValue)
                                putSumValueToMap(FrenchConstants.IndicateurPerfEnv.IC_PROJET.attribute, indicateurPerfEnvMap, bdValue)
                                break
                            case FrenchConstants.DED_LOT3_13_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_DED.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                            case FrenchConstants.COMPOSANTS_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_COMPOSANT.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                            case FrenchConstants.CHANTIER_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_CHANTIER.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                        }
                        break
                    case FrenchConstants.GWP_NOCC_CALC_RULEID:
                        switch (calculationResult.resultCategoryId) {
                            case FrenchConstants.CONSTRUCTION_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_CONSTRUCTION_OCC.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                            case FrenchConstants.ENERGIE_RESULT_CATID:
                                indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_ENERGIE_OCC.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                            case FrenchConstants.BATIMENT_RESULT_CATID:
                                BigDecimal bdValue = numberUtil.resultToBigDecimal(calculationResult.result)
                                String attr = forZone ? FrenchConstants.IndicateurPerfEnv.IC_ZONE_OCC.attribute : FrenchConstants.IndicateurPerfEnv.IC_BATIMENT_OCC.attribute
                                indicateurPerfEnvMap.put(attr, bdValue)
                                putSumValueToMap(FrenchConstants.IndicateurPerfEnv.IC_PROJET_OCC.attribute, indicateurPerfEnvMap, bdValue)
                                break
                        }
                        break
                    case FrenchConstants.STOCKC_KGC_CALC_RULEID:
                        switch (calculationResult.resultCategoryId) {
                            case FrenchConstants.BATIMENT_RESULT_CATID:
                                String attr = forZone ? FrenchConstants.IndicateurPerfEnv.STOCK_C_ZONE.attribute : FrenchConstants.IndicateurPerfEnv.STOCK_C_BATIMENT.attribute
                                Double result = convertKgCIntoKgCDivM2(calculationResult.result, sref)
                                indicateurPerfEnvMap.put(attr, numberUtil.resultToBigDecimal(result))
                                break
                            case FrenchConstants.PARCELLE_RESULT_CATID:
                                if (!forZone) {
                                    Double result = convertKgCIntoKgCDivM2(stockCParcelleResult.result, sharedProjectVariables.sref)
                                    BigDecimal bdValue = numberUtil.resultToBigDecimal(result)
                                    indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.STOCK_C_PARCELLE.attribute, bdValue)
                                }
                                break
                        }
                        break
                    case FrenchConstants.RATIO_GWP_CALC_RULEID:
                        switch (calculationResult.resultCategoryId) {
                            case FrenchConstants.UDD_RESULT_CATID:
                                String attr = FrenchConstants.IndicateurPerfEnv.UDD.attribute
                                indicateurPerfEnvMap.put(attr, numberUtil.resultToBigDecimal(calculationResult.result))
                                break
                        }
                        break
                    case FrenchConstants.GWP_TOTAL_CALC_RULEID:
                        if (FrenchConstants.IC_CONSTRUCTION_MAX_RESULT_CATID == calculationResult.resultCategoryId) {
                            indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_CONSTRUCTION_MAX.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                        } else if (FrenchConstants.IC_ENERGIE_MAX_RESULT_CATID == calculationResult.resultCategoryId) {
                            indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_ENERGIE_MAX.attribute, numberUtil.resultToBigDecimal(calculationResult.result))
                        } else if (forZone && calculationResult.resultCategoryId in FrenchConstants.COEFMOD_ICCONSTRUCTION_RESULT_CATS) {
                            putResultToCoefModIcconstructionFromMap(indicateurPerfEnvMap, calculationResult)
                        }
                        break
                }
            }
        }
        return indicateurPerfEnvMap
    }

    /**
     * Add ic_parcelle to indicateurPerfEnvMap;
     * add it as part of ic_projet;
     * calculate (icParcelle * sref / nocc) and add it as part of ic_projet_occ
     * @param indicateurPerfEnvMap
     * @param icParcelle
     * @param sref
     * @param nocc
     * @return
     */
    private static Map<String, BigDecimal> setIcParcelleToIndicateurPerfEnvMap(Map<String, BigDecimal> indicateurPerfEnvMap, BigDecimal icParcelle, Double sref, Double nocc) {

        indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.IC_PARCELLE.attribute, icParcelle)
        putSumValueToMap(FrenchConstants.IndicateurPerfEnv.IC_PROJET.attribute, indicateurPerfEnvMap, icParcelle)

        if (sref) {
            nocc = nocc ?: 1D
            BigDecimal icProjetOccPart = (icParcelle * sref) / nocc
            putSumValueToMap(FrenchConstants.IndicateurPerfEnv.IC_PROJET_OCC.attribute, indicateurPerfEnvMap, roundBd(icProjetOccPart))
        }

        return indicateurPerfEnvMap
    }

    /**
     * Converts stock kgC into kgC/m2 by dividing by sref
     * @param value
     * @param sref
     * @return
     */
    private static Double convertKgCIntoKgCDivM2(Double value, Double sref) {

        Double result = value

        if (value && sref) {
            result = value / sref as Double
        }

        return result
    }

    /**
     * Sum up the value of key in map as bigDecimal
     * @param key - value of this key must be BigDecimal
     * @param map must be initiated before use of this method
     * @param value
     */
    private static void putSumValueToMap(String key, Map map, BigDecimal value) {
        if (map.containsKey(key)) {
            BigDecimal existing = map.get(key) as BigDecimal
            map.put(key, existing + value)
        } else {
            map.put(key, value)
        }
    }

    /**
     * Instatiate the coefModIcconstruction with some data from the datasets. There are few fields that will be added in {@link #putResultToCoefModIcconstructionFromMap}
     * @param indicateurPerfEnvMap
     * @param srefDatasetForZone
     */
    private void initCoefModIcconstructionToIndicateurPerfEnvMap(Map indicateurPerfEnvMap, Dataset srefDatasetForZone) {
        if (indicateurPerfEnvMap == null) {
            return
        }

        indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.COEF_MOD_ICCONSTRUCTION.attribute, new TCoefModIcconstruction(
                migeo: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MIGEO_QUESTIONID) ?: 0d,
                micombles: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MICOMBLES_QUESTIONID) ?: 0d,
                misurf: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MISURF_QUESTIONID) ?: 0d,
                icConstructionMaxmoyen: datasetService.getAdditionalAnswerFromDatasetAsInteger(srefDatasetForZone, FrenchConstants.ICCONSTRUCTION_MAXMOYEN_QUESTIONID) ?: 0
        ))
    }

    /**
     * this method must be run for a calculation result that already match one of the resultCategoryId and calculationRuleId
     * The coefModIcconstruction is already init in method {@link #initCoefModIcconstructionToIndicateurPerfEnvMap}
     * @param indicateurPerfEnvMap
     * @param calculationResult
     */
    private static void putResultToCoefModIcconstructionFromMap(Map indicateurPerfEnvMap, CalculationResult calculationResult) {
        TCoefModIcconstruction coefModIcconstruction = indicateurPerfEnvMap.get(FrenchConstants.IndicateurPerfEnv.COEF_MOD_ICCONSTRUCTION.attribute) as TCoefModIcconstruction

        if (!coefModIcconstruction) {
            // this should not happen. Implement fallback just in case
            coefModIcconstruction = new TCoefModIcconstruction()
        }

        // if change or add new case, need to update FrenchConstants.COEFMOD_ICCONSTRUCTION_RESULT_CATS
        switch (calculationResult.resultCategoryId) {
            case FrenchConstants.MIINFRA_RESULT_CATID:
                coefModIcconstruction.setMiinfra(calculationResult.result)
                break
            case FrenchConstants.MIVRD_RESULT_CATID:
                coefModIcconstruction.setMivrd(calculationResult.result)
                break
            case FrenchConstants.MIDED_RESULT_CATID:
                coefModIcconstruction.setMided(calculationResult.result)
                break
        }

        indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.COEF_MOD_ICCONSTRUCTION.attribute, coefModIcconstruction)
    }

    /**
     * Init the CoefModIcenergie with data from the datasets of this zone.
     * @param indicateurPerfEnvMap
     * @param srefDatasetForZone
     */
    private void initCoefModIcenergieToIndicateurPerfEnvMap(Map indicateurPerfEnvMap, Dataset srefDatasetForZone) {
        if (indicateurPerfEnvMap == null) {
            return
        }

        indicateurPerfEnvMap.put(FrenchConstants.IndicateurPerfEnv.COEF_MOD_ICENERGIE.attribute, new TCoefModIcenergie(
                mcgeo: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MCGEO_QUESTIONID) ?: 0d,
                mccombles: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MCCOMBLES_QUESTIONID) ?: 0d,
                mcsurfMoy: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MCSURF_MOY_QUESTIONID) ?: 0d,
                mcsurfTot: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MCSURF_TOT_QUESTIONID) ?: 0d,
                mccat: datasetService.getAdditionalAnswerFromDatasetAsDouble(srefDatasetForZone, FrenchConstants.MCCAT_QUESTIONID) ?: 0d,
                icEnergieMaxmoyen: datasetService.getAdditionalAnswerFromDatasetAsInteger(srefDatasetForZone, FrenchConstants.ICENERGIE_MAXMOYEN_QUESTIONID) ?: 0
        ))
    }

    /**
     * Set the result to objects {@link BeResult} {@link EnergyResult} {@link SiteResult} {@link WaterResult}
     * Also set them by zone
     * @param datasets
     * @param resultsPerDataset
     * @param storeResultObject
     * @param validZoneIds
     * @param batimentArea
     */
    private void setEnergyWaterSiteBeResultToFrenchResultObject(Set<Dataset> datasets, Map<String, List<CalculationResult>> resultsPerDataset,
                                                                AbstractFrenchResult storeResultObject, Set<String> validZoneIds = null, BatimentArea batimentArea = null) {
        boolean setBe = storeResultObject instanceof BeResult
        boolean hasValidZonesAndSharedRatios = validZoneIds && batimentArea?.allZonesHaveSharedRatio

        for (Dataset dataset in datasets) {
            List<CalculationResult> resultsForDataset = resultsPerDataset.get(dataset.manualId)
            if (!resultsForDataset) {
                continue
            }

            String zoneId = datasetService.getZoneOfDataset(dataset)

            for (CalculationResult result in resultsForDataset) {

                Integer existingZoneIndex = zoneId ? storeResultObject.resultsPerZones?.findIndexOf { it.zoneId == zoneId } : -1
                calculationResultService.groupCalculationResultByKey(result, result.calculationRuleId, storeResultObject.resultsPerRule)

                if (zoneId) {
                    setZoneResultToFrenchResultObject(existingZoneIndex, result, storeResultObject, zoneId)
                } else if (setBe && hasValidZonesAndSharedRatios && result.resultCategoryId == FrenchConstants.BE_RESULT_CATID) {
                    // Be result categories have many virtuals hence we don't care about the results of the virtuals, we just care about Be
                    // Be results do not have results at zone level, hence we need to create them for each zone from the total, by multiplying the total by the shared ratio
                    for (String validZoneId in validZoneIds) {
                        if (!validZoneId) {
                            continue
                        }

                        Double sharedRatio = batimentArea.zoneAreas?.find { it.zoneId == validZoneId }?.zonePerBatimentRatio
                        if (!sharedRatio) {
                            continue
                        }

                        CalculationResult copy = copyResult(result)
                        if (result.result) {
                            copy.result = result.result * sharedRatio
                        }
                        Integer existingValidZoneIndex = zoneId ? storeResultObject.resultsPerZones?.findIndexOf { it.zoneId == validZoneId } : -1
                        setZoneResultToFrenchResultObject(existingValidZoneIndex, copy, storeResultObject, validZoneId)
                    }
                }
            }
        }
    }

    private void setZoneResultToFrenchResultObject(Integer existingZoneIndex, CalculationResult result, AbstractFrenchResult storeResultObject, String zoneId) {
        if (existingZoneIndex >= 0) {
            calculationResultService.groupCalculationResultByKey(result, result.calculationRuleId, storeResultObject.resultsPerZones[existingZoneIndex].resultsPerRule)
        } else {
            ResultsPerZone resultsPerZone = new ResultsPerZone(zoneId)
            resultsPerZone.resultsPerRule.put(result.calculationRuleId, [result])
            storeResultObject.resultsPerZones.add(resultsPerZone)
        }
    }

    /**
     * Group all calculation results of design to per zone, per lot and per sous lot
     * also run grouping energy be water site results
     * All grouping for exporting results easier later
     * @param design
     * @param store
     */
    private void groupCalculationResults(Entity design, FrenchStore store) {
        String designId = design.id.toString()
        List<CalculationResult> calculationResults = store.resultsPerDesign?.get(designId)
        Set<String> validZones = store.batimentZoneIndexMappings.get(designId)?.keySet() ?: [] as Set
        if (store.parcelle.designId == designId) {
            validZones += FrenchConstants.ZONE_PARCELLE
        }
        BatimentArea batimentArea = store.srefPerDesign.find { it.designId == designId }
        DatasetGroup datasetGroup = store.datasetGroups.find { it.designId == designId }
        boolean allZonesHaveSharedRatio = validZones && batimentArea.allZonesHaveSharedRatio
        // this map only has results for energy water and site datasets
        Map<String, List<CalculationResult>> resultsPerDataset = new HashMap<>()
        Set<String> getResultForDatasetIds = store.datasetGroups.find { it.designId == designId }?.energyWaterSiteBeDatasetIds

        boolean runGroupingEnergyWaterSiteBeResults = !getResultForDatasetIds?.isEmpty()

        for (String calculationRuleId in store.calculationRuleIds) {
            List<CalculationResult> resultsForRule = calculationResults?.findAll {
                it.calculationRuleId?.equals(calculationRuleId)
            }

            if (!resultsForRule) {
                continue
            }
            store.resultsPerRule.put(calculationRuleId, resultsForRule)
            //Every resultForRule is a result for specific result cat and calc rule, we split it into multiple results for lots and souslots
            // (as the resultForRule is the sum of results of multiple datasets, each dataset belong to a lot / sous lot)
            for (CalculationResult resultForRule in resultsForRule) {
                // <lotId, results same lot>
                Map<String, Double> resultsPerLot = [:]
                // <lotId, <sousLotId, results same sousLot>>
                Map<String, Map<String, Double>> resultsPerLotPerSousLot = [:]
                // <zoneId, results same zone>
                Map<String, Double> resultsPerZone = [:]
                // <zoneId, <lotId, results same lot>>
                Map<String, Map<String, Double>> resultsPerZonePerLot = [:]
                // <zoneId, <lotId, <sousLotId, results same sousLot>>>
                Map<String, Map<String, Map<String, Double>>> resultsPerZonePerLotPerSousLot = [:]

                Set<Dataset> datasets = calculationResultService.getDatasetsFromCalculationResult(resultForRule, design)

                if (datasets) {
                    Integer maxLotNumbers = 14

                    for (Dataset dataset in datasets) {
                        Double datasetResult = calculationResultService.getResultOfDataset(resultForRule, dataset)
                        if (!datasetResult) {
                            continue
                        }

                        if (runGroupingEnergyWaterSiteBeResults && dataset.manualId in getResultForDatasetIds) {
                            CalculationResult copy = copyResult(resultForRule)
                            copy.result = datasetResult
                            calculationResultService.groupCalculationResultByKey(copy, dataset.manualId, resultsPerDataset)
                        }

                        String originalZoneId = datasetService.getZoneOfDataset(dataset)
                        String lotId = datasetService.getLotOfDataset(dataset)
                        Integer lotIdAsInt = lotId?.isInteger() ? lotId.toInteger() : null

                        // only results from zones that are mapped get exported
                        if (originalZoneId && (originalZoneId == FrenchConstants.FEC_UNASSIGNED_ZONE || originalZoneId in validZones)) {
                            // if the result is for all zones, split it to multiple results for every valid zone
                            Map<String, Double> resultAndZone = handleResultForAllZones(originalZoneId, validZones, datasetResult, batimentArea, allZonesHaveSharedRatio)
                            if (!resultAndZone) {
                                continue
                            }

                            resultAndZone.each { String zoneId, Double resultForDataset ->
                                // sum up the results of same zone
                                calculationResultService.sumResultSameGroup(resultForDataset, zoneId, resultsPerZone)

                                if (lotIdAsInt > 0 && lotIdAsInt <= maxLotNumbers) {
                                    // sum up the results of same lot
                                    calculationResultService.sumResultSameGroup(resultForDataset, lotId, resultsPerLot)

                                    Map<String, Double> lotResultsForZone = resultsPerZonePerLot.get(zoneId) ?: [:]

                                    // sum up result of same lot same zone
                                    calculationResultService.sumResultSameGroup(resultForDataset, lotId, lotResultsForZone)
                                    resultsPerZonePerLot.put(zoneId, lotResultsForZone)

                                    String sousLotId = datasetService.getSousLotOfDataset(dataset)

                                    if (sousLotId) {
                                        // group results per lot per souslot at design level
                                        groupResultsPerLotPerSousLot(resultsPerLotPerSousLot, lotId, sousLotId, resultForDataset)

                                        // group results per lot per souslot at zone level
                                        // <lotId, <sousLotId, results same sousLot>>
                                        Map<String, Map<String, Double>> resultsPerLotPerSousLotForZone = resultsPerZonePerLotPerSousLot.get(zoneId) ?: [:]
                                        groupResultsPerLotPerSousLot(resultsPerLotPerSousLotForZone, lotId, sousLotId, resultForDataset)
                                        resultsPerZonePerLotPerSousLot.put(zoneId, resultsPerLotPerSousLotForZone)
                                    }
                                }
                            }
                        }
                    }
                }

                // have to keep these in separated loops because there are results that belong to a zone, but not lot, and to a lot but not sous lot, and so on...
                // DO NOT CHANGE THE ORDER OF THE NEXT TWO LINES, must run setResultsPerLot before setResultsPerLotPerSousLot
                setResultsPerLot(resultsPerLot, store.resultsPerLot, resultForRule, calculationRuleId)
                setResultsPerLotPerSousLot(resultsPerLotPerSousLot, store.resultsPerLot, resultForRule, calculationRuleId)

                resultsPerZone.each { String zoneId, Double result ->
                    CalculationResult copy = copyResult(resultForRule)
                    copy.result = result

                    Integer existingZoneIndex = store.resultsPerZone.findIndexOf { it.zoneId == zoneId }
                    if (existingZoneIndex >= 0) {
                        calculationResultService.groupCalculationResultByKey(copy, calculationRuleId, store.resultsPerZone[existingZoneIndex].resultsPerRule)
                    } else {
                        ResultsPerZone resultsByZone = new ResultsPerZone(zoneId)
                        calculationResultService.groupCalculationResultByKey(copy, calculationRuleId, resultsByZone.resultsPerRule)
                        store.resultsPerZone.add(resultsByZone)
                    }
                }

                resultsPerZonePerLot.each { String zoneId, Map<String, Double> resultPerLot ->
                    Integer existingZoneIndex = store.resultsPerZone.findIndexOf { it.zoneId == zoneId }
                    ResultsPerZone resultsByZone = store.resultsPerZone[existingZoneIndex]
                    if (resultsByZone) {
                        setResultsPerLot(resultPerLot, resultsByZone.resultsPerLots, resultForRule, calculationRuleId)
                        store.resultsPerZone.set(existingZoneIndex, resultsByZone)
                    }
                }

                resultsPerZonePerLotPerSousLot.each { String zoneId, Map<String, Map<String, Double>> resultPerLotPerSousLot ->
                    Integer existingZoneIndex = store.resultsPerZone.findIndexOf { it.zoneId == zoneId }
                    ResultsPerZone resultsByZone = store.resultsPerZone[existingZoneIndex]
                    setResultsPerLotPerSousLot(resultPerLotPerSousLot, resultsByZone.resultsPerLots, resultForRule, calculationRuleId)
                }
            }
        }

        if (runGroupingEnergyWaterSiteBeResults) {
            groupEnergyWaterSiteBeResults(store, datasetGroup, resultsPerDataset, designId)
        }
    }

    /**
     * Group the result to the map resultsPerLotPerSousLot by lotId and sousLotId
     * @param resultsPerLotPerSousLot <lotId, <sousLotId, result>>
     * @param lotId
     * @param sousLotId
     * @param resultForDataset
     */
    private void groupResultsPerLotPerSousLot(Map<String, Map<String, Double>> resultsPerLotPerSousLot, String lotId,
                                              String sousLotId, Double resultForDataset) {
        if (resultsPerLotPerSousLot == null) {
            return
        }

        Map<String, Double> resultsPerSousLot = resultsPerLotPerSousLot.get(lotId) ?: [:]
        // sum up the results of same sous lot
        calculationResultService.sumResultSameGroup(resultForDataset, sousLotId, resultsPerSousLot)
        resultsPerLotPerSousLot.put(lotId, resultsPerSousLot)
    }

    /**
     * Convert the result per lot into {@link ResultsPerLot} objects by creating {@link CalculationResult} and
     * add it to the list of results per rule in {@link ResultsPerLot#resultsPerRule}
     * These objects are eventually stored in the {@link FrenchStore}
     * @param resultPerLot
     * @param groupedResultsPerLots
     * @param originalResult
     * @param calculationRuleId
     */
    private void setResultsPerLot(Map<String, Double> resultPerLot, List<ResultsPerLot> groupedResultsPerLots,
                                  CalculationResult originalResult, String calculationRuleId) {
        if (!originalResult || resultPerLot == null || groupedResultsPerLots == null) {
            return
        }

        resultPerLot.each { String lotId, Double result ->
            CalculationResult copy = copyResult(originalResult)
            copy.result = result

            Integer existingLotIndex = groupedResultsPerLots.findIndexOf { it.lotId == lotId }
            if (existingLotIndex >= 0) {
                calculationResultService.groupCalculationResultByKey(copy, calculationRuleId, groupedResultsPerLots[existingLotIndex].resultsPerRule)
            } else {
                ResultsPerLot groupedResultsByLot = new ResultsPerLot(lotId)
                calculationResultService.groupCalculationResultByKey(copy, calculationRuleId, groupedResultsByLot.resultsPerRule)
                groupedResultsPerLots.add(groupedResultsByLot)
            }
        }
    }

    /**
     * {@link ResultsPerLot#resultsPerSousLots} stores a list of results per souslot that belongs to the lot.
     * Convert the result per souslot into {@link ResultsPerSousLot} objects by creating {@link CalculationResult} and
     * add it to the list of results per rule in {@link ResultsPerSousLot#resultsPerRule}
     * These objects are eventually stored in the {@link FrenchStore}
     * @param resultPerLotPerSousLot
     * @param groupedResultsPerLots
     * @param originalResult
     * @param calculationRuleId
     */
    private void setResultsPerLotPerSousLot(Map<String, Map<String, Double>> resultPerLotPerSousLot,
                                            List<ResultsPerLot> groupedResultsPerLots, CalculationResult originalResult,
                                            String calculationRuleId) {
        if (!originalResult || resultPerLotPerSousLot == null || groupedResultsPerLots == null) {
            return
        }

        resultPerLotPerSousLot.each { String lotId, Map<String, Double> resultPerSousLot ->
            ResultsPerLot groupedResultsByLot
            Integer existingLotIndex = groupedResultsPerLots.findIndexOf { it.lotId == lotId }

            boolean addNew = false
            if (existingLotIndex < 0) {
                addNew = true
                groupedResultsByLot = new ResultsPerLot(lotId)
            } else {
                groupedResultsByLot = groupedResultsPerLots[existingLotIndex]
            }

            resultPerSousLot.each { String sousLotId, Double result ->
                CalculationResult copy = copyResult(originalResult)
                copy.result = result

                Integer existingSousLotIndex = groupedResultsByLot.resultsPerSousLots?.findIndexOf { it.sousLotId == sousLotId }
                if (existingSousLotIndex >= 0) {
                    calculationResultService.groupCalculationResultByKey(copy, calculationRuleId, groupedResultsByLot.resultsPerSousLots[existingSousLotIndex].resultsPerRule)
                } else {
                    ResultsPerSousLot groupedResultsBySousLot = new ResultsPerSousLot(sousLotId)
                    calculationResultService.groupCalculationResultByKey(copy, calculationRuleId, groupedResultsBySousLot.resultsPerRule)
                    groupedResultsByLot.resultsPerSousLots.add(groupedResultsBySousLot)
                }
            }

            if (addNew) {
                groupedResultsPerLots.add(groupedResultsByLot)
            } else {
                groupedResultsPerLots.set(existingLotIndex, groupedResultsByLot)
            }
        }
    }

    /**
     * Split the result for all zones among all the zones (multiplying by the zone area ratio / batiment area)
     * all zones must have the shared ratio to be able to do it.
     */
    private static Map<String, Double> handleResultForAllZones(String zoneId, Set<String> validZones, Double resultForDataset, BatimentArea batimentArea, Boolean allZonesHaveSharedRatio) {

        if (zoneId == FrenchConstants.FEC_UNASSIGNED_ZONE) {
            if (!allZonesHaveSharedRatio) {
                return null
            }

            Map<String, Double> sharedResults = new HashMap<>()

            for (String validZone in validZones) {
                Double sharedRatio = batimentArea.zoneAreas?.find { it.zoneId == validZone }?.zonePerBatimentRatio
                if (sharedRatio) {
                    sharedResults.put(validZone, resultForDataset * sharedRatio)
                }
            }
            return sharedResults
        } else {
            return [(zoneId): resultForDataset]
        }
    }

    /**
     * Group the energy water site and Be results to our store for easier use later
     * @param store
     * @param resultsPerDataset
     * @param datasetsPerEnergyUse
     * @param siteDatasetsPerCategory
     * @param waterDatasetsPerType
     */
    private void groupEnergyWaterSiteBeResults(FrenchStore store, DatasetGroup datasetGroup,
                                               Map<String, List<CalculationResult>> resultsPerDataset, String designId) {
        for (Integer energyUseRef in FrenchConstants.SousContributeurRef.ENERGIE.allRefs()) {
            EnergyResult energyResult = new EnergyResult(energyUseRef)
            Set<Dataset> datasets = datasetGroup?.energyDatasetsPerUse?.get(energyUseRef)
            if (datasets) {
                setEnergyWaterSiteBeResultToFrenchResultObject(datasets, resultsPerDataset, energyResult)
            }
            store.energyResults.add(energyResult)
        }

        // Be result categories have datasets from multiple places
        Set<String> validZoneIds = store.batimentZoneIndexMappings?.get(designId)?.keySet()
        BatimentArea batimentArea = store.srefPerDesign.find { it.designId == designId }
        BeResult beResult = new BeResult(FrenchConstants.SousContributeurRef.ENERGIE.BE.intValue())
        Set<Dataset> beDatasets = datasetGroup?.beDatasetsPerRef?.get(FrenchConstants.SousContributeurRef.ENERGIE.BE.intValue())
        if (beDatasets) {
            setEnergyWaterSiteBeResultToFrenchResultObject(beDatasets, resultsPerDataset, beResult, validZoneIds, batimentArea)
        }
        store.beResults.add(beResult)

        for (Integer categoryRef in FrenchConstants.SousContributeurRef.CHANTIER.allRefs()) {
            SiteResult siteResult = new SiteResult(categoryRef)
            Set<Dataset> siteDatasets = datasetGroup?.siteDatasetsPerCategory?.get(categoryRef)
            if (siteDatasets) {
                setEnergyWaterSiteBeResultToFrenchResultObject(siteDatasets, resultsPerDataset, siteResult)
            }
            store.siteResults.add(siteResult)
        }

        for (Integer waterTypeRef in FrenchConstants.SousContributeurRef.EAU.allRefs()) {
            WaterResult waterResult = new WaterResult(waterTypeRef)
            Set<Dataset> waterDatasets = datasetGroup?.waterDatasetsPerType?.get(waterTypeRef)
            if (waterDatasets) {
                setEnergyWaterSiteBeResultToFrenchResultObject(waterDatasets, resultsPerDataset, waterResult)
            }
            store.waterResults.add(waterResult)
        }
    }

    /**
     * Clone calculation result with only necessary fields for RE2020
     * @param result
     * @return
     */
    private static CalculationResult copyResult(CalculationResult result) {
        if (!result) {
            return null
        }
        CalculationResult copy = new CalculationResult()
        copy.resultCategoryId = result.resultCategoryId
        copy.calculationRuleId = result.calculationRuleId

        return copy
    }
}
