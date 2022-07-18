
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_Sortie_Base complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_Sortie_Base">
 *   &lt;complexContent>
 *     &lt;extension base="{}RT_Data_Objet_Base">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_Sortie_Base")
@XmlSeeAlso({
    RTDataSortieProjet.class,
    RTDataSortieSensibiliteGroupe.class,
    RTDataSortieSensibiliteZone.class,
    RTDataSortieBaieB.class,
    RTDataSortieGenerateur.class,
    RTDataSortieGroupeB.class,
    RTDataSortieGroupeE.class,
    RTDataSortieBatimentE.class,
    RTDataSortieBatimentD.class,
    RTDataSortieAscenseurC.class,
    RTDataSortieBatimentB.class,
    RTDataSortieProductionStockage.class,
    RTDataSortieSensibiliteBatiment.class,
    RTDataSortiePCAD.class,
    RTDataSortieVentilationMecanique.class,
    RTDataSortieGeneration.class,
    RTDataSortieZoneE.class,
    RTDataSortieZoneB.class,
    RTDataSortieParkingC.class
})
public abstract class RTDataSortieBase
    extends RTDataObjetBase
{


}
