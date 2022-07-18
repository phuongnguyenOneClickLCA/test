
package com.bionova.optimi.xml.fecRSEnv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RT_Data_PCAD complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RT_Data_PCAD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Index" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Pos_Gen" type="{}E_Position_Gen"/>
 *         &lt;element name="Type_PCAD" type="{}E_Type_PCAD"/>
 *         &lt;element name="Type_Gestion_Chaud" type="{}E_Type_Gestion_Chaud"/>
 *         &lt;element name="Theta_Wm_Ch" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Boucle_Solaire_ProdCAD_Collection" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence minOccurs="0">
 *                   &lt;element name="Boucle_Solaire_ProdCAD" type="{}RT_Data_Boucle_Solaire_ProdCAD" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Ballon_Decentralise_Collection" type="{}ArrayOfRT_Data_Ballon_Decentralise" minOccurs="0"/>
 *         &lt;element name="Ballon_Centralise" type="{}RT_Data_Ballon_Centralise" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RT_Data_PCAD", propOrder = {

})
public class RTDataPCAD {

    @XmlElement(name = "Index")
    protected int index;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Pos_Gen", required = true)
    protected String posGen;
    @XmlElement(name = "Type_PCAD", required = true)
    protected String typePCAD;
    @XmlElement(name = "Type_Gestion_Chaud", required = true)
    protected String typeGestionChaud;
    @XmlElement(name = "Theta_Wm_Ch")
    protected double thetaWmCh;
    @XmlElement(name = "Boucle_Solaire_ProdCAD_Collection")
    protected RTDataPCAD.BoucleSolaireProdCADCollection boucleSolaireProdCADCollection;
    @XmlElement(name = "Ballon_Decentralise_Collection")
    protected ArrayOfRTDataBallonDecentralise ballonDecentraliseCollection;
    @XmlElement(name = "Ballon_Centralise")
    protected RTDataBallonCentralise ballonCentralise;

    /**
     * Gets the value of the index property.
     * 
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     */
    public void setIndex(int value) {
        this.index = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the posGen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosGen() {
        return posGen;
    }

    /**
     * Sets the value of the posGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosGen(String value) {
        this.posGen = value;
    }

    /**
     * Gets the value of the typePCAD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePCAD() {
        return typePCAD;
    }

    /**
     * Sets the value of the typePCAD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePCAD(String value) {
        this.typePCAD = value;
    }

    /**
     * Gets the value of the typeGestionChaud property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeGestionChaud() {
        return typeGestionChaud;
    }

    /**
     * Sets the value of the typeGestionChaud property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeGestionChaud(String value) {
        this.typeGestionChaud = value;
    }

    /**
     * Gets the value of the thetaWmCh property.
     * 
     */
    public double getThetaWmCh() {
        return thetaWmCh;
    }

    /**
     * Sets the value of the thetaWmCh property.
     * 
     */
    public void setThetaWmCh(double value) {
        this.thetaWmCh = value;
    }

    /**
     * Gets the value of the boucleSolaireProdCADCollection property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataPCAD.BoucleSolaireProdCADCollection }
     *     
     */
    public RTDataPCAD.BoucleSolaireProdCADCollection getBoucleSolaireProdCADCollection() {
        return boucleSolaireProdCADCollection;
    }

    /**
     * Sets the value of the boucleSolaireProdCADCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataPCAD.BoucleSolaireProdCADCollection }
     *     
     */
    public void setBoucleSolaireProdCADCollection(RTDataPCAD.BoucleSolaireProdCADCollection value) {
        this.boucleSolaireProdCADCollection = value;
    }

    /**
     * Gets the value of the ballonDecentraliseCollection property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRTDataBallonDecentralise }
     *     
     */
    public ArrayOfRTDataBallonDecentralise getBallonDecentraliseCollection() {
        return ballonDecentraliseCollection;
    }

    /**
     * Sets the value of the ballonDecentraliseCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRTDataBallonDecentralise }
     *     
     */
    public void setBallonDecentraliseCollection(ArrayOfRTDataBallonDecentralise value) {
        this.ballonDecentraliseCollection = value;
    }

    /**
     * Gets the value of the ballonCentralise property.
     * 
     * @return
     *     possible object is
     *     {@link RTDataBallonCentralise }
     *     
     */
    public RTDataBallonCentralise getBallonCentralise() {
        return ballonCentralise;
    }

    /**
     * Sets the value of the ballonCentralise property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTDataBallonCentralise }
     *     
     */
    public void setBallonCentralise(RTDataBallonCentralise value) {
        this.ballonCentralise = value;
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
     *       &lt;sequence minOccurs="0">
     *         &lt;element name="Boucle_Solaire_ProdCAD" type="{}RT_Data_Boucle_Solaire_ProdCAD" maxOccurs="unbounded" minOccurs="0"/>
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
        "boucleSolaireProdCAD"
    })
    public static class BoucleSolaireProdCADCollection {

        @XmlElement(name = "Boucle_Solaire_ProdCAD")
        protected List<RTDataBoucleSolaireProdCAD> boucleSolaireProdCAD;

        /**
         * Gets the value of the boucleSolaireProdCAD property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the boucleSolaireProdCAD property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBoucleSolaireProdCAD().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RTDataBoucleSolaireProdCAD }
         * 
         * 
         */
        public List<RTDataBoucleSolaireProdCAD> getBoucleSolaireProdCAD() {
            if (boucleSolaireProdCAD == null) {
                boucleSolaireProdCAD = new ArrayList<RTDataBoucleSolaireProdCAD>();
            }
            return this.boucleSolaireProdCAD;
        }

    }

}
