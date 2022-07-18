
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRT_Data_Sortie_Prod_Elec_C complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRT_Data_Sortie_Prod_Elec_C">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sortie_Prod_Elec_C" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="O_Type_prod_elec" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="O_TAC_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                   &lt;element name="O_TAC_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                   &lt;element name="O_Eef_elec_AC_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                   &lt;element name="O_Eef_elec_exp_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRT_Data_Sortie_Prod_Elec_C", propOrder = {
    "sortieProdElecC"
})
public class ArrayOfRTDataSortieProdElecC {

    @XmlElement(name = "Sortie_Prod_Elec_C", nillable = true)
    protected List<ArrayOfRTDataSortieProdElecC.SortieProdElecC> sortieProdElecC;

    /**
     * Gets the value of the sortieProdElecC property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sortieProdElecC property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSortieProdElecC().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ArrayOfRTDataSortieProdElecC.SortieProdElecC }
     * 
     * 
     */
    public List<ArrayOfRTDataSortieProdElecC.SortieProdElecC> getSortieProdElecC() {
        if (sortieProdElecC == null) {
            sortieProdElecC = new ArrayList<ArrayOfRTDataSortieProdElecC.SortieProdElecC>();
        }
        return this.sortieProdElecC;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="O_Type_prod_elec" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="O_TAC_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *         &lt;element name="O_TAC_elec_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *         &lt;element name="O_Eef_elec_AC_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *         &lt;element name="O_Eef_elec_exp_annuel" type="{http://www.w3.org/2001/XMLSchema}double"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "oTypeProdElec",
        "otacAnnuel",
        "otacElecAnnuel",
        "oEefElecACAnnuel",
        "oEefElecExpAnnuel"
    })
    public static class SortieProdElecC {

        @XmlElement(name = "O_Type_prod_elec", required = true)
        protected String oTypeProdElec;
        @XmlElement(name = "O_TAC_annuel")
        protected double otacAnnuel;
        @XmlElement(name = "O_TAC_elec_annuel")
        protected double otacElecAnnuel;
        @XmlElement(name = "O_Eef_elec_AC_annuel")
        protected double oEefElecACAnnuel;
        @XmlElement(name = "O_Eef_elec_exp_annuel")
        protected double oEefElecExpAnnuel;

        /**
         * Gets the value of the oTypeProdElec property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOTypeProdElec() {
            return oTypeProdElec;
        }

        /**
         * Sets the value of the oTypeProdElec property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOTypeProdElec(String value) {
            this.oTypeProdElec = value;
        }

        /**
         * Gets the value of the otacAnnuel property.
         * 
         */
        public double getOTACAnnuel() {
            return otacAnnuel;
        }

        /**
         * Sets the value of the otacAnnuel property.
         * 
         */
        public void setOTACAnnuel(double value) {
            this.otacAnnuel = value;
        }

        /**
         * Gets the value of the otacElecAnnuel property.
         * 
         */
        public double getOTACElecAnnuel() {
            return otacElecAnnuel;
        }

        /**
         * Sets the value of the otacElecAnnuel property.
         * 
         */
        public void setOTACElecAnnuel(double value) {
            this.otacElecAnnuel = value;
        }

        /**
         * Gets the value of the oEefElecACAnnuel property.
         * 
         */
        public double getOEefElecACAnnuel() {
            return oEefElecACAnnuel;
        }

        /**
         * Sets the value of the oEefElecACAnnuel property.
         * 
         */
        public void setOEefElecACAnnuel(double value) {
            this.oEefElecACAnnuel = value;
        }

        /**
         * Gets the value of the oEefElecExpAnnuel property.
         * 
         */
        public double getOEefElecExpAnnuel() {
            return oEefElecExpAnnuel;
        }

        /**
         * Sets the value of the oEefElecExpAnnuel property.
         * 
         */
        public void setOEefElecExpAnnuel(double value) {
            this.oEefElecExpAnnuel = value;
        }

    }

}
