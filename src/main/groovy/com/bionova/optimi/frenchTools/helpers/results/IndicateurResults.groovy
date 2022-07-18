package com.bionova.optimi.frenchTools.helpers.results

import com.bionova.optimi.xml.re2020RSEnv.TIndicateur
import com.bionova.optimi.xml.re2020RSEnv.TIndicateurCo2Dynamique
import groovy.transform.CompileStatic

/**
 * An object that holds multiple indicateursAcvCollection and indicateurCo2Dynamique
 */
@CompileStatic
class IndicateurResults {

    TIndicateur indicateursAcvCollection
    TIndicateur indicateursAcvCollectionForComposant
    TIndicateur indicateursAcvCollectionForEau
    TIndicateur indicateursAcvCollectionForChantier
    TIndicateur indicateursAcvCollectionForEnergie

    TIndicateurCo2Dynamique indicateurCo2Dynamique
    TIndicateurCo2Dynamique indicateurCo2DynamiqueForComposant
    TIndicateurCo2Dynamique indicateurCo2DynamiqueForEau
    TIndicateurCo2Dynamique indicateurCo2DynamiqueForChantier
    TIndicateurCo2Dynamique indicateurCo2DynamiqueForEnergie

    IndicateurResults(FOR purpose) {
        switch (purpose) {
            case FOR.ALL:
                this.indicateurCo2Dynamique = new TIndicateurCo2Dynamique()
                this.indicateurCo2DynamiqueForComposant = new TIndicateurCo2Dynamique()
                this.indicateurCo2DynamiqueForEau = new TIndicateurCo2Dynamique()
                this.indicateurCo2DynamiqueForChantier = new TIndicateurCo2Dynamique()
                this.indicateurCo2DynamiqueForEnergie = new TIndicateurCo2Dynamique()

                this.indicateursAcvCollection = new TIndicateur()
                this.indicateursAcvCollectionForComposant = new TIndicateur()
                this.indicateursAcvCollectionForEau = new TIndicateur()
                this.indicateursAcvCollectionForChantier = new TIndicateur()
                this.indicateursAcvCollectionForEnergie = new TIndicateur()
                break
            case FOR.PARCELLE:
                this.indicateurCo2Dynamique = new TIndicateurCo2Dynamique()
                this.indicateurCo2DynamiqueForComposant = new TIndicateurCo2Dynamique()
                this.indicateurCo2DynamiqueForEau = new TIndicateurCo2Dynamique()
                this.indicateurCo2DynamiqueForChantier = new TIndicateurCo2Dynamique()

                this.indicateursAcvCollection = new TIndicateur()
                this.indicateursAcvCollectionForComposant = new TIndicateur()
                this.indicateursAcvCollectionForEau = new TIndicateur()
                    this.indicateursAcvCollectionForChantier = new TIndicateur()
                break
            case FOR.ENERGIE:
                this.indicateurCo2DynamiqueForEnergie = new TIndicateurCo2Dynamique()
                this.indicateursAcvCollectionForEnergie = new TIndicateur()
                break
            case FOR.CHANTIER:
                this.indicateurCo2DynamiqueForChantier = new TIndicateurCo2Dynamique()
                this.indicateursAcvCollectionForChantier = new TIndicateur()
                break
            case FOR.EAU:
                this.indicateurCo2DynamiqueForEau = new TIndicateurCo2Dynamique()
                this.indicateursAcvCollectionForEau = new TIndicateur()
                break
            case FOR.COMPOSANT:
                this.indicateurCo2DynamiqueForComposant = new TIndicateurCo2Dynamique()
                this.indicateursAcvCollectionForComposant = new TIndicateur()
                break
        }
    }

    /**
     * Decide how the object should be created
     */
    static enum FOR {
        ALL, PARCELLE, ENERGIE, CHANTIER, EAU, COMPOSANT
    }
}
