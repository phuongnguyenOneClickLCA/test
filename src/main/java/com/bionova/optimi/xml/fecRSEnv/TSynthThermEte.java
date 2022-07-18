
package com.bionova.optimi.xml.fecRSEnv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * DC:synthese thermique ete
 * 
 * <p>Java class for t_synth_therm_ete complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_synth_therm_ete">
 *   &lt;complexContent>
 *     &lt;extension base="{}t_locaux">
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_synth_therm_ete")
@XmlSeeAlso({
    com.bionova.optimi.xml.fecRSEnv.Batiment.Enveloppe.SynthCaractThermEte.SurfTotale.class
})
public class TSynthThermEte
    extends TLocaux
{


}
